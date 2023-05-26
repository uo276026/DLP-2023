package codegenerator;

import ast.Definition;
import ast.Expression.Expression;
import ast.Expression.FunctionInvocation;
import ast.FunctionDefinition;
import ast.Program;
import ast.Statement.*;
import ast.Type.FunctionType;
import ast.Type.Type;
import ast.Type.VoidType;
import ast.VariableDefinition;

public class ExecuteCGVisitor extends AbstractCGVisitor<Object, Void>{

    public AddressCGVisitor addressCGVisitor;
    public ValueCGVisitor valueCGVisitor;

    public ExecuteCGVisitor(CodeGenerator codeGenerator) {
        super(codeGenerator);
        addressCGVisitor= new AddressCGVisitor(codeGenerator);
        valueCGVisitor=new ValueCGVisitor(codeGenerator, addressCGVisitor);
        addressCGVisitor.setValueCGVisitor(valueCGVisitor);
    }

    /**
     * execute[[Program program -> definition*]]() =
     *      ' * Global Variables
     *          definition*.foreach(definition -> execute[[definition]]())
     */
    @Override
    public Void visit(Program program, Object p) {
        for(Definition d: program.defs)
            if(d instanceof VariableDefinition)
                d.accept(this, p);

        codeGenerator.mainInvocationComment();
        codeGenerator.callMain();
        codeGenerator.halt();

        for(Definition d: program.defs)
            if(d instanceof FunctionDefinition)
                d.accept(this, p);

        return null;
    }

    /**
     * execute[[VariableDefinition varDef -> type ID]]() =
     *      ' * type.toString() ID (offset varDef.offset)
     */
    @Override
    public Void visit(VariableDefinition varDef, Object p) {
        codeGenerator.varDefinition(varDef);
        return null;
    }

    /**
     * execute[[FunctionDefinition f -> type ID varDef* statement*]]() =
     *      ' * Parameters
     *          execute[[type]]()
     *      ' * Local Variables
     *          varDef*.foreach(varDef -> execute[[varDef]]())
     *          if(varDef*.size>0)
     *              enter varDef*.get(varDef.size-1).offset;
     */
    @Override
    public Void visit(FunctionDefinition f, Object p) {
        codeGenerator.printLine(f.getLine());
        codeGenerator.printString("\n "+f.getName()+":");

        codeGenerator.printTitle("Parameters");
        f.getFunctionType().accept(this,p);

        codeGenerator.printTitle("Local variables");
        int localBytes=0;
        for (Statement s : f.statements) {
            if (s instanceof VariableDefinition) {
                localBytes += ((VariableDefinition) s).getType().numberOfBytes();
                s.accept(this, p);
            }
        }
        codeGenerator.enter(localBytes);

        FunctionType funcType= (FunctionType) f.getType();
        Type returnType= funcType.getReturnType();
        f.setLocalBytes(localBytes);
        f.setParamBytes(funcType.getParamBytes());
        f.setReturnBytes(returnType.numberOfBytes());

        for(Statement s: f.statements) {
            if (!(s instanceof VariableDefinition)) {
                codeGenerator.printLine(s.getLine());
                s.accept(this, f); //le pasamos la funcion para que en el statement que sea return tenga los bytes
            }
        }

        if (returnType instanceof VoidType)
            codeGenerator.ret(0, f.getLocalBytes(), f.getParamBytes());
        return null;
    }

    /**
     * execute[[FunctionType functionType -> type varDef*]]() =
     *      varDef*.forEach(varDef -> execute[[varDef]]())
     */
    @Override
    public Void visit(FunctionType functionType, Object p) {
        int paramBytes=0;
        for(VariableDefinition s: functionType.parameters) {
            paramBytes += s.getType().numberOfBytes();
            s.accept(this, p);
        }
        functionType.setParamBytes(paramBytes);
        return null;
    }

    /**
     * execute[[Assignment a -> exp1 exp2]]() =
     *      ' * Assignment
     *      address[[ex1]]())
     *      value[[ex2]]())
     *      store exp1.type.suffix()
     */
    @Override
    public Void visit(Assignment a, Object p) {
        codeGenerator.printTitle("Assignment");
        a.expression1.accept(addressCGVisitor, p);
        a.expression2.accept(valueCGVisitor, p);
        codeGenerator.convert(a.expression2.getType(), a.expression1.getType());
        codeGenerator.store(a.expression1.getType());
        return null;
    }


    /**
     * execute[[Print print -> expression]]() =
     *      ' * Write
     *      value[[expression]]())
     *      out exp1.type.suffix()
     */
    @Override
    public Void visit(Print print, Object p) {
        codeGenerator.printTitle("Write");
        print.expression.accept(valueCGVisitor, p);
        codeGenerator.out(print.expression.getType());
        return null;
    }

    /**
     * execute[[Input input -> expression]]() =
     *      ' * Read
     *      address[[expression]]())
     *      in exp1.type.suffix()
     *      store exp1.type.suffix()
     */
    @Override
    public Void visit(Input input, Object p) {
        codeGenerator.printTitle("Read");
        input.expression.accept(addressCGVisitor, p);
        codeGenerator.in(input.expression.getType());
        codeGenerator.store(input.expression.getType());
        return null;
    }

    /**
     *  execute[[Iterative: statement → exp statement*]] =
     *      int labelNumber = cg.getLabels(2);
     *      label labelNumber :
     *      value[[exp]]
     *      jz label labelNumber+1
     *      statement*.foreach(stmt -> execute[[stmt]])
     *      jmp label labelNumber
     *      label labelNumber +1
     */
    public Void visit(Iterative i, Object p){
        codeGenerator.printTitle("While");
        codeGenerator.printLine(i.getLine());
        int condition = codeGenerator.getLabel();
        int end = codeGenerator.getLabel();
        codeGenerator.printLabel(condition);
        i.expression.accept(valueCGVisitor, p);
        codeGenerator.jz(end);

        codeGenerator.printTitle("While body");
        for(Statement s: i.whileBody) {
            codeGenerator.printLine(s.getLine());
            s.accept(this, p);
        }

        codeGenerator.jmp(condition);
        codeGenerator.printLabel(end);
        return null;
    }

    /**
     * execute[[IfStatement: statement1 → exp statement2+ statement3*]] =
     *      int labelNumber = cg.getLabels(2);
     *      value[[exp]]
     *      jz label labelNumber
     *      statement2+.foreach(stmt -> execute[[stmt]])
     *      jmp label labelNumber+1
     *      label labelNumber :
     *      statement3*.foreach(stmt -> execute[[stmt]])
     *      label labelNumber+1 :
     */
    public Void visit(Conditional c, Object p){
        codeGenerator.printTitle("If");
        codeGenerator.printLine(c.getLine());

        int labelNumber=codeGenerator.getLabel();
        int labelNumber2=codeGenerator.getLabel();
        c.expression.accept(valueCGVisitor,p);
        codeGenerator.jz(labelNumber);

        codeGenerator.printTitle("if body");
        for(Statement s:c.ifBody) {
            codeGenerator.printLine(s.getLine());
            s.accept(this, p);
        }
        codeGenerator.jmp(labelNumber2);
        codeGenerator.printLabel(labelNumber);

        codeGenerator.printTitle("else body");
        for(Statement s:c.elseBody) {
            codeGenerator.printLine(s.getLine());
            s.accept(this, p);
        }
        codeGenerator.printLabel(labelNumber2);
        return null;
    }

    //Revisar esto
    public Void visit(FunctionInvocation f, Object p){
        ((Expression)f).accept(valueCGVisitor, p);
        FunctionType function = (FunctionType) f.defName.def.getType();
        if(!(function.returnType instanceof VoidType)){
            codeGenerator.pop(function.returnType);
        }
        return null;
    }

    public Void visit(Return r, Object p){
        codeGenerator.printTitle("Return");
        r.expression.accept(valueCGVisitor,p);

        FunctionDefinition f = (FunctionDefinition) p;

        FunctionType funcType= (FunctionType) f.getType();
        Type returnType= funcType.getReturnType();
        codeGenerator.convert(r.expression.getType(), returnType);
        //Si es VoidType, no llega hasta aqui porque no tiene statement Return, lo hace en FunctionDefinition
//        if (returnType instanceof VoidType)
//            codeGenerator.ret(0, f.getLocalBytes(), 0);
//        else
        codeGenerator.ret(f.getReturnBytes(), f.getLocalBytes(), f.getParamBytes());

        return null;
    }


}
