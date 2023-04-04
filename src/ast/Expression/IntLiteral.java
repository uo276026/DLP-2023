package ast.Expression;

import ast.Type.Type;
import visitor.Visitor;

public class IntLiteral extends AbstractExpression implements Expression {
    public int value;
    public int line, column;

    public IntLiteral(int line, int column, int value) {
        this.line=line;
        this.column=column;
        this.value=value;
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
