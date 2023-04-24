package codegenerator;

import ast.Expression.*;
import ast.VariableDefinition;

public class ValueCGVisitor extends AbstractCGVisitor<Void, Void>{

    public AddressCGVisitor addressCGVisitor;

    public ValueCGVisitor(CodeGenerator codeGenerator, AddressCGVisitor addressCGVisitor) {
        super(codeGenerator);
        this.addressCGVisitor=addressCGVisitor;
    }

    @Override
    public Void visit(Variable v, Void p) {
        v.accept(addressCGVisitor, p);
        codeGenerator.load(v.getType());
        return null;
    }

    @Override
    public Void visit(IntLiteral i, Void o ) {
        codeGenerator.push(i.value);
        return null;
    }

    @Override
    public Void visit(CharLiteral c, Void o ) {
        codeGenerator.push(c.value);
        return null;
    }

    @Override
    public Void visit(DoubleLiteral d, Void o ) {
        codeGenerator.push(d.value);
        return null;
    }

    @Override
    public Void visit(Arithmetic a, Void p) {
        codeGenerator.convert(a.ex1.getType(), a.getType());
        codeGenerator.convert(a.ex2.getType(), a.getType());
        //codeGenerator.operation();
        return null;
    }

}
