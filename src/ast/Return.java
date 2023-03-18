package ast;

import visitor.Visitor;

public class Return implements Statement{
    public Expression expression;

    public int line;
    public int column;

    public Return(int line, int column, Expression ex){
        this.line=line;
        this.column=column;
        this.expression=ex;
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
