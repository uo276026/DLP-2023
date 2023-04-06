package ast.Expression;

import ast.Type.Type;
import visitor.Visitor;

public class Cast extends AbstractExpression implements Expression {
    public Type typeCast;
    public Expression expression;
    public int line, column;

    public Cast(int line, int column, Type typeCast, Expression exp){
        this.line=line;
        this.column=column;
        this.typeCast=typeCast;
        this.expression=exp;
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
