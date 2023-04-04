package ast;

import ast.Type.Type;

public interface Definition extends ASTNode {

    public Type getType();
    public String getName();
    public int getScope();
    public void setScope(int scope);



}
