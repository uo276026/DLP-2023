package ast.Type;

import ast.Type.Type;
import visitor.Visitor;

public class VoidType extends AbstractType {

    public int line, column;

    public VoidType(int line, int column){
        this.line=line;
        this.column=column;
    }

    public VoidType(){

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
