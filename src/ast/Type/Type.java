package ast.Type;

import ast.ASTNode;

import java.util.List;

public interface Type extends ASTNode {

    Type arithmetic(Type other);
    Type squareBrackets(Type other);
    Type parenthesis(List<Type> other);
    Type asLogical();
    Type arithmetic();
    Type comparison(Type other);
    Type logic(Type other);
    Type logic();
    Type MustPromoteTo(Type other);
    Type BuiltInType(Type other);
    Type canBeCastTo(Type other);
    Type dot(Type other);




}
