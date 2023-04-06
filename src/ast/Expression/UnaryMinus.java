package ast.Expression;

import ast.Expression.Expression;
import visitor.Visitor;

public class UnaryMinus extends AbstractExpression implements Expression {
    public int line, column;
    public Expression expression;

    public UnaryMinus(int line, int column, Expression expression){
        this.line=line;
        this.column=column;
        this.expression=expression;
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
