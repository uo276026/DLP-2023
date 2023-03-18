package ast;

import visitor.Visitor;

public class ArrayAccess extends AbstractExpression implements ASTNode, Expression {
    public Expression name;
    public Expression expression;
    //public Expression expression2;
    public int line, column;

    public ArrayAccess(int line, int column, Expression ex, Expression name) {
        this.line=line;
        this.column=column;
        this.expression=ex;
       // this.expression2=ex2;
        this.name=name;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP p) {
        return v.visit(this, p);
    }


}
