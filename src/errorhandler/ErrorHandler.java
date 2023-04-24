package errorhandler;

import ast.Type.ErrorType;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class ErrorHandler {

    public List<ErrorType> errors = new ArrayList<ErrorType>();
    public boolean error;
    public static ErrorHandler eh;

    private ErrorHandler(){ }

    public static ErrorHandler getInstance() {
        if(eh==null){
            eh = new ErrorHandler();
            return eh;
        } else {
            return eh;
        }
    }

    public boolean anyError(){
        return error;
    }

    public void showErrors(PrintStream print){
        for(ErrorType err: errors){
            print.println(err.getMessage()+"\n");
        }
    }

    public void addError(ErrorType err){
        errors.add(err);
        this.error=true;
    }


}
