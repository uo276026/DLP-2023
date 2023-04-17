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
    public int numberOfBytes() {
        return 1;
    }

    @Override
    public Type arithmetic(Type other, int line, int column){
        //Si other es char, devolvemos int
        if(other instanceof IntType)
            return new IntType(line, column);
        if(other instanceof ErrorType)
            return other;
        //Si es double o int, debe dar error
        return super.arithmetic(other, line, column);
    }

    @Override
    public Type canBeCastTo(Type other, int line, int column){
        if(other instanceof IntType || other instanceof DoubleType || other instanceof CharType || other instanceof ErrorType)
            return this;
        return super.arithmetic(this, line, column);
    }

    @Override
    public Type unaryminus(int line, int column){
        return new IntType(line, column);
    }

}
