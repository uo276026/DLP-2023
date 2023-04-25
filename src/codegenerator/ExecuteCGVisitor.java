package codegenerator;

import ast.Definition;
import ast.Expression.Arithmetic;
import ast.FunctionDefinition;
import ast.Program;
import ast.Statement.Assignment;
import ast.Statement.Input;
import ast.Statement.Print;
import ast.Statement.Statement;
import ast.Type.FunctionType;
import ast.Type.Type;
import ast.VariableDefinition;

public class ExecuteCGVisitor extends AbstractCGVisitor<Void, Void>{

    public AddressCGVisitor addressCGVisitor;
    public ValueCGVisitor valueCGVisitor;

    public ExecuteCGVisitor(CodeGenerator codeGenerator) {
        super(codeGenerator);
        addressCGVisitor= new AddressCGVisitor(codeGenerator);
        valueCGVisitor=new ValueCGVisitor(codeGenerator, addressCGVisitor);
    }

    @Override
    public Void visit(Program program, Void p) {
        for(Definition d: program.defs) {
            if(d instanceof FunctionDefinition){
                codeGenerator.call(d.getName());
                codeGenerator.halt();
            }
            d.accept(this, p);
        }
        return null;
    }

    @Override
    public Void visit(VariableDefinition varDef, Void p) {
        codeGenerator.varDefinition(varDef);
        //varDef.type.accept(this, p);
        return null;
    }

    @Override
    public Void visit(FunctionDefinition f, Void p) {
        codeGenerator.printLine(f.getLine());
        codeGenerator.printString("\n"+f.getName()+":");
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
        codeGenerator.ret(returnType.numberOfBytes(), localBytes, funcType.getParamBytes());

        return null;
    }

    @Override
    public Void visit(FunctionType functionType, Void p) {
        int paramBytes=0;
        codeGenerator.printString("\t\t' * Parameters");
        for(VariableDefinition s: functionType.parameters) {
            paramBytes += s.getType().numberOfBytes();
            codeGenerator.varDefinition(s);
            s.accept(this, p);
        }
        functionType.setParamBytes(paramBytes);
        return null;
    }

    @Override
    public Void visit(Assignment a, Void p) {
        codeGenerator.printString("\t\t' * Assignment");
        a.expression1.accept(addressCGVisitor, p);
        a.expression2.accept(valueCGVisitor, p);
        codeGenerator.store(a.expression1.getType());
        return null;
    }

    @Override
    public Void visit(Print print, Void p) {
        codeGenerator.printString("\t\t' * Write");
        print.expression.accept(valueCGVisitor, p);
        codeGenerator.out(print.expression.getType());
        return null;
    }

    @Override
    public Void visit(Input input, Void p) {
        codeGenerator.printString("\t\t' * Read");
        input.expression.accept(addressCGVisitor, p);
        codeGenerator.in(input.expression.getType());
        codeGenerator.store(input.expression.getType());
        return null;
    }









}
