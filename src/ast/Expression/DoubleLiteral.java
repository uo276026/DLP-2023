package ast.Expression;

import ast.Type.DoubleType;
import ast.Type.IntType;
import ast.Type.Type;
import visitor.Visitor;

public class DoubleLiteral extends AbstractExpression implements Expression{
    public double value;
    public int line, column;

    public DoubleLiteral(int line, int column, double v) {
        this.line=line;
        this.column=column;
        this.value=v;
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
