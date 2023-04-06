package ast.Type;

import visitor.Visitor;

import java.util.List;

public abstract class AbstractType implements Type{

    @Override
    public Type arithmetic(Type other) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do arithmetic operations");
    }

    @Override
    public Type squareBrackets(Type other) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Indexing operations");
    }

    @Override
    public Type parenthesis(List<Type> other) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Function Invocation operations");
    }

    @Override
    public Type asLogical() {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Logical operations");
    }

    @Override
    public Type arithmetic() {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Arithmetic operations");
    }

    @Override
    public Type comparison(Type other) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Comparison operations");
    }

    @Override
    public Type logic(Type other) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Logic operations");
    }

    @Override
    public Type logic() {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Logic operations");
    }

    @Override
    public Type MustPromoteTo(Type other) {
        if(other==null)
            System.out.println("LLegaaaa");
        if(other.getClass().equals(this.getClass()) || other instanceof ErrorType)
            return other;
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Different types at assignment");
    }

    @Override
    public Type BuiltInType(Type other) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Function Definition operations");
    }

    @Override
    public Type canBeCastTo(Type other) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Cast operations");
    }

    @Override
    public Type dot(Type other) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Struct Access operations");
    }
}
