package ast;

public interface Expression extends ASTNode{

    public boolean getLValue();
    public void setLValue(boolean lvalue);

}
