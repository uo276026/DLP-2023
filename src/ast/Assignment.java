package ast;

import visitor.Visitor;

public class Assignment implements Statement{
    public Expression expression1;
    public Expression expression2;

    public int line;
    public int column;

    public Assignment(int line, int column, Expression ex1, Expression ex2){
        this.line=line;
        this.column=column;
        this.expression1=ex1;
        this.expression2=ex2;
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
