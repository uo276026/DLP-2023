package ast.Expression;

import ast.Type.*;

public abstract class AbstractExpression implements Expression {
    public boolean lvalue;
    private Type type;

    @Override
    public boolean getLValue() {
        return lvalue;
    }

    @Override
    public void setLValue(boolean lvalue) {
        this.lvalue=lvalue;
    }

    @Override
    public Type getType() {
        return this.type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }


}
