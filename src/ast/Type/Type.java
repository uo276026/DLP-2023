package ast.Type;

import ast.ASTNode;

public interface Type extends ASTNode {

    Type arithmetic(Type type);
    Type squareBrackets(Type type);
    Type parenthesis(Type type);
    Type asLogical();
    Type arithmetic();
    Type comparison(Type type);
    Type logic(Type type);
    Type logic();
    Type MustPromoteTo(Type type);
    Type BuiltInType(Type type);
    Type canBeCastTo(Type type);
    Type dot(Type type);




}
