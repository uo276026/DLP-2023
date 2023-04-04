package ast.Expression;

import ast.ASTNode;
import ast.Type.Type;

public interface Expression extends ASTNode {

    public boolean getLValue();
    public void setLValue(boolean lvalue);
    public Type getType();
    public void setType(Type type);

}
