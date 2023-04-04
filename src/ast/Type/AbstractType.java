package ast.Type;

import visitor.Visitor;

public abstract class AbstractType implements Type{

    @Override
    public Type arithmetic(Type type) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do arithmetic operations");
    }

    @Override
    public Type squareBrackets(Type type) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Indexing operations");
    }

    @Override
    public Type parenthesis(Type type) {
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
    public Type comparison(Type type) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Comparison operations");
    }

    @Override
    public Type logic(Type type) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Logic operations");
    }

    @Override
    public Type logic() {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Logic operations");
    }

    @Override
    public Type MustPromoteTo(Type type) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Assignment operations");
    }

    @Override
    public Type BuiltInType(Type type) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Function Definition operations");
    }

    @Override
    public Type canBeCastTo(Type type) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Cast operations");
    }

    @Override
    public Type dot(Type type) {
        return new ErrorType(this.getLine(), this.getColumn(),
                "ERROR in line " + this.getLine() + ": Wrong type, it can't do Struct Access operations");
    }
}
