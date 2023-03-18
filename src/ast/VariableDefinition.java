package ast;

import visitor.Visitor;

public class VariableDefinition implements Definition, Statement{

    public int line;
    public int column;
    public Type type;
    public String name;

    public VariableDefinition(int line, int column, Type type, String name){
        this.line=line;
        this.column=column;
        this.type=type;
        this.name=name;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getColumn() {
        return column;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP p) {
        return v.visit(this,p);
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public String getName() {
        return name;
    }
}
