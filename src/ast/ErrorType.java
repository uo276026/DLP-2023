package ast;

import errorhandler.ErrorHandler;
import visitor.Visitor;

public class ErrorType implements Type{
    public int line, column;
    public String message;

    public ErrorType(int line, int column, String message){
        this.line=line;
        this.column=column;
        this.message=message;
        ErrorHandler.getInstance().addError(this);
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public String getMessage(){
        return message;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP p) {
        return v.visit(this,p);
    }
}
