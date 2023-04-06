package ast.Type;

import ast.ASTNode;
import visitor.Visitor;

import java.util.List;

public abstract class AbstractType implements Type{

    @Override
    public Type arithmetic(Type other, int line, int column) {
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it can't do arithmetic operations");
    }

    @Override
    public Type squareBrackets(Type other, int line, int column) {
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it can't do Indexing operations");
    }

    @Override
    public Type parenthesis(List<Type> other, int line, int column) {
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it can't do Function Invocation operations");
    }

    @Override
    public Type asLogical(int line, int column) {
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it can't do Iterative or Conditional operations");
    }

    @Override
    public Type arithmetic(int line, int column) {
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it can't do Arithmetic operations");
    }

    @Override
    public Type comparison(Type other, int line, int column) {
        if(other.getClass().equals(this.getClass()) || other instanceof ErrorType)
            return other;
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it can't do Comparison operations");
    }

    @Override
    public Type logic(Type other, int line, int column) {
        if(other.getClass().equals(this.getClass()) || other instanceof ErrorType)
            return other;
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it can't do Logical operations");
    }

    @Override
    public Type unarynot(int line, int column) {
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it can't do UnaryNot operations");
    }

    @Override
    public Type MustPromoteTo(Type other, int line, int column) {
        if(other.getClass().equals(this.getClass()) || other instanceof ErrorType)
            return other;
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Different types at Assignment");
    }

    @Override
    public Type BuiltInType(Type other, int line, int column) {
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Different types at Return");
    }

    @Override
    public Type canBeCastTo(Type other, int line, int column) {
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it can't be Casted");
    }

    @Override
    public Type dot(String name, int line, int column) {
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Wrong type, it is not a Struct");
    }
}
