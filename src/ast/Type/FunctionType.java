package ast.Type;

import ast.VariableDefinition;
import visitor.Visitor;

import java.util.List;

public class FunctionType extends AbstractType {

    public Type returnType;
    public List<VariableDefinition> parameters; //parametros

    public int line, column;

    public FunctionType(Type returnType, List<VariableDefinition> vars){
        this.returnType=returnType;
        this.parameters =vars;
    }

    public FunctionType(int line, int column, Type returnType, List<VariableDefinition> vars){
        this.line=line;
        this.column=column;
        this.returnType=returnType;
        this.parameters =vars;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public List<VariableDefinition> getParameters(){
        return parameters;
    }

    public Type getReturnType(){
        return returnType;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP p) {
        return v.visit(this,p);
    }

    @Override
    public Type parenthesis(List<Type> other) {
        if (other.size() != parameters.size())
            return new ErrorType(this.getLine(), this.getColumn(), "ERROR in line "+this.getLine()+": " +
                    "The number of parameters is wrong");
        boolean wrongType=false;
        for(int i=0;i<other.size();i++){
            if(!other.get(i).getClass().equals(getParameters().get(i).getColumn())) {
                if(other.get(i) instanceof ErrorType)
                    return other.get(i);
                return new ErrorType(other.get(i).getLine(), other.get(i).getColumn(), "ERROR in line " + other.get(i).getLine() +
                        ": The type of the parameter is not the same in the definition of '" + parameters.get(i).getName() + "'");
            }
        }
        return this.returnType;
    }
}
