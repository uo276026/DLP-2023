package ast.Type;

import visitor.Visitor;

public class ArrayType extends AbstractType {
    public int size;
    public Type type;
    public int line, column;

    public ArrayType(int line, int column, int size, Type type){
        this.line=line;
        this.column=column;
        this.type=type;
        this.size=size;
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
