package ast.Type;

import ast.ASTNode;

import java.util.List;

public interface Type extends ASTNode {

    int numberOfBytes();
    public String suffix();

    Type arithmetic(Type other, int line, int column);
    Type squareBrackets(Type other, int line, int column);
    Type parenthesis(List<Type> other, int line, int column);
    Type asLogical(int line, int column);
    Type unaryminus(int line, int column);
    Type comparison(Type other, int line, int column);
    Type logic(Type other, int line, int column);
    Type unarynot(int line, int column);
    Type MustPromoteTo(Type other, int line, int column);
    Type BuiltInType(Type other, int line, int column);
    Type canBeCastTo(Type other, int line, int column);
    Type dot(String name, int line, int column);




}
