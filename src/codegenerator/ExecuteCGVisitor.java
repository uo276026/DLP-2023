package codegenerator;

import ast.Definition;
import ast.FunctionDefinition;
import ast.Program;
import ast.Statement.Assignment;
import ast.Statement.Input;
import ast.Statement.Print;
import ast.Statement.Statement;
import ast.Type.FunctionType;
import ast.Type.Type;
import ast.Type.VoidType;
import ast.VariableDefinition;

public class ExecuteCGVisitor extends AbstractCGVisitor<Void, Void>{

    public AddressCGVisitor addressCGVisitor;
    public ValueCGVisitor valueCGVisitor;

    public ExecuteCGVisitor(CodeGenerator codeGenerator) {
        super(codeGenerator);
        addressCGVisitor= new AddressCGVisitor(codeGenerator);
        valueCGVisitor=new ValueCGVisitor(codeGenerator, addressCGVisitor);
    }

    /**
     * execute[[Program program -> definition*]]() =
     *      ' * Global Variables
     *          definition*.foreach(definition -> execute[[definition]]())
     */
    @Override
    public Void visit(Program program, Void p) {
        for(Definition d: program.defs)
            d.accept(this, p);
        return null;
    }

    /**
     * execute[[VariableDefinition varDef -> type ID]]() =
     *      ' * type.toString() ID (offset varDef.offset)
     */
    @Override
    public Void visit(VariableDefinition varDef, Void p) {
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
    public Void visit(FunctionDefinition f, Void p) {
        codeGenerator.call(f.getName());
        codeGenerator.halt();
        codeGenerator.printLine(f.getLine());
        codeGenerator.printString("\n"+f.getName()+":");

        codeGenerator.printString("\t\t' * Parameters");
        f.getFunctionType().accept(this,p);

        codeGenerator.printString("\t\t' * Local variables");
        int localBytes=0;
        for (Statement s : f.statements) {
            if (s instanceof VariableDefinition) {
                localBytes += ((VariableDefinition) s).getType().numberOfBytes();
                s.accept(this, p);
            }
        }
        codeGenerator.enter(localBytes);

        for(Statement s: f.statements) {
            if (!(s instanceof VariableDefinition)) {
                codeGenerator.printLine(s.getLine());
                s.accept(this, p);
            }
        }

        FunctionType funcType= (FunctionType) f.getType();
        Type returnType= funcType.getReturnType();
        if (returnType instanceof VoidType)
            codeGenerator.ret(0, localBytes, 0);
        else
            codeGenerator.ret(returnType.numberOfBytes(), localBytes, funcType.getParamBytes());
        return null;
    }

    /**
     * execute[[FunctionType functionType -> type varDef*]]() =
     *      varDef*.forEach(varDef -> execute[[varDef]]())
     */
    @Override
    public Void visit(FunctionType functionType, Void p) {
        int paramBytes=0;
        for(VariableDefinition s: functionType.parameters) {
            paramBytes += s.getType().numberOfBytes();
            codeGenerator.varDefinition(s);
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
    public Void visit(Assignment a, Void p) {
        codeGenerator.printString("\t\t' * Assignment");
        a.expression1.accept(addressCGVisitor, p);
        a.expression2.accept(valueCGVisitor, p);
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
    public Void visit(Print print, Void p) {
        codeGenerator.printString("\t\t' * Write");
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
    public Void visit(Input input, Void p) {
        codeGenerator.printString("\t\t' * Read");
        input.expression.accept(addressCGVisitor, p);
        codeGenerator.in(input.expression.getType());
        codeGenerator.store(input.expression.getType());
        return null;
    }

}
