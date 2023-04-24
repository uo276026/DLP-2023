package ast.Expression;

import visitor.Visitor;

public class IntLiteral extends AbstractExpression {
    public int value;
    public int line, column;

    public IntLiteral(int line, int column, int value) {
        this.line=line;
        this.column=column;
        this.value=value;
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
