package ast;

import visitor.Visitor;

import java.util.List;

public class FunctionType implements Type{

    public Type returnType;
    public List<VariableDefinition> parametros; //parametros

    public int line, column;

    public FunctionType(Type returnType, List<VariableDefinition> vars){
        this.returnType=returnType;
        this.parametros=vars;
    }

    public FunctionType(int line, int column, Type returnType, List<VariableDefinition> vars){
        this.line=line;
        this.column=column;
        this.returnType=returnType;
        this.parametros=vars;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public List<VariableDefinition> getParametros(){
        return parametros;
    }

    public Type getReturnType(){
        return returnType;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP p) {
        return v.visit(this,p);
    }
}
