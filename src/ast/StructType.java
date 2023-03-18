package ast;

import visitor.Visitor;

import java.util.List;

public class StructType implements Type{

    public List<StructField> fields;
    public int line, column;

    public StructType(int line, int column, List<StructField> fields){
        this.line=line;
        this.column=column;
        this.fields=fields;
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

}
