package ast;

import visitor.Visitor;

public class UnaryMinus extends AbstractExpression implements Expression{
    public int line, column;
    public Expression expression;

    public UnaryMinus(int line, int column, Expression expression){
        this.line=line;
        this.column=column;
        this.expression=expression;
    }

    @Override
    public int getLine() {
        return 0;
    }

    @Override
    public int getColumn() {
        return 0;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP p) {
        return v.visit(this,p);
    }
}
