package ast.Type;

import ast.ASTNode;

import java.util.List;

public interface Type extends ASTNode {

    int numberOfBytes();
    public String suffix();

    Type arithmetic(Type other, int line, int column);
    Type squareBrackets(Type other, int line, int column); //Array []
    Type parenthesis(List<Type> other, int line, int column); // Function ()
    Type asLogical(int line, int column); //condition, if y while
    Type unaryminus(int line, int column); // '-'
    Type comparison(Type other, int line, int column); // >, <, >=, <=, ==, !=
    Type logic(Type other, int line, int column); // &&, ||
    Type unarynot(int line, int column); // '!'
    Type MustPromoteTo(Type other, int line, int column); // asignaciones x = y (hecho en abstract)
    Type BuiltInType(Type other, int line, int column); // return (type viene por parametro para )
    Type canBeCastTo(Type other, int line, int column); // cast
    Type dot(String name, int line, int column); // struct




}
