package ast.Type;

import ast.ASTNode;
import visitor.Visitor;

public class CharType extends AbstractType {

    public int line, column;

    public CharType(int line, int column){
        this.line=line;
        this.column=column;
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
    public Type arithmetic(Type other){
        //Si other es char, devolvemos int
        if(other instanceof IntType)
            return new IntType(other.getLine(), other.getColumn());
        if(other instanceof ErrorType)
            return other;
        //Si es double o int, debe dar error
        return super.arithmetic(other);
    }

}
