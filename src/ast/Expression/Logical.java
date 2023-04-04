package ast.Expression;

import visitor.Visitor;

public class Logical extends AbstractExpression implements Expression{
    public String operator;
    public Expression expression1;
    public Expression expression2;
    public int line, column;

    public Logical(int line, int column, Expression ex1, String operator, Expression ex2) {
        this.line=line;
        this.column=column;
        this.expression1=ex1;
        this.expression2=ex2;
        this.operator=operator;
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
        return v.visit(this,p);
    }
}
