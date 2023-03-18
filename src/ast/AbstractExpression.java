package ast;

public abstract class AbstractExpression implements Expression{
    public boolean lvalue;

    @Override
    public boolean getLValue() {
        return lvalue;
    }

    @Override
    public void setLValue(boolean lvalue) {
        this.lvalue=lvalue;
    }

}
