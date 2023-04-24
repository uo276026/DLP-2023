package ast.Expression;

import visitor.Visitor;


public class Arithmetic extends AbstractExpression  {

    public int line, column;
    public Expression ex1;
    public Expression ex2;
    public String operator;


    public Arithmetic(int line, int column, Expression ex1, String operator, Expression ex2) {
        this.line=line;
        this.column=column;
        this.ex1=ex1;
        this.ex2=ex2;
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
        return v.visit(this, p);
    }
}
