package ast.Expression;

import visitor.Visitor;

public class UnaryNot extends AbstractExpression implements Expression {

    public Expression expression;
    public int line, column;

    public UnaryNot(int line, int column, Expression expression){
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
