package codegenerator;

import ast.Expression.*;
import ast.Statement.Input;
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
        a.ex1.accept(this,p);
        a.ex2.accept(this,p);
        codeGenerator.operation(a.operator, a.getType());
        return null;
    }

    @Override
    public Void visit(Comparison a, Void p) {
        a.expression1.accept(this,p);
        a.expression2.accept(this,p);
        codeGenerator.comparison(a.operator, a.getType());
        return null;
    }

    @Override
    public Void visit(Logical a, Void p) {
        a.expression1.accept(this,p);
        a.expression2.accept(this,p);
        codeGenerator.logical(a.operator);
        return null;
    }

    public Void visit(Cast c, Void p){
        c.expression.accept(this,p);
        codeGenerator.convert(c.expression.getType(), c.getType());
        return null;
    }

    @Override
    public Void visit(UnaryNot u, Void p) {
        u.expression.accept(this,p);
        codeGenerator.not();
        return null;
    }

}
