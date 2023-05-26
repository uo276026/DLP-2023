package ast.Type;

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
        if(other instanceof CharType)
            return new IntType(line, column);
        if(other instanceof ErrorType | other instanceof IntType | other instanceof DoubleType)
            return other;
        return super.arithmetic(other, line, column);
    }

    @Override
    public Type canBeCastTo(Type other, int line, int column){
        if(other instanceof IntType || other instanceof DoubleType || other instanceof CharType || other instanceof ErrorType)
            return this;
        return super.canBeCastTo(this, line, column);
    }

    @Override
    public Type unaryminus(int line, int column){
        return new IntType(line, column);
    }


    @Override
    public String toString(){
        return "CharType";
    }

    @Override
    public String suffix(){
        return "b";
    }

    @Override
    public Type MustPromoteTo(Type other, int line, int column) {
        if(other.getClass().equals(this.getClass()) | other instanceof ErrorType |other instanceof IntType | other instanceof DoubleType)
            return other;
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Different types at Assignment");
    }

    @Override
    public Type BuiltInType(Type other, int line, int column) {
        if(this.getClass().equals(other.getClass()) | other instanceof ErrorType)
            return other;
        if(other instanceof IntType | other instanceof DoubleType)
            return this;
        return new ErrorType(line, column,
                "ERROR in line " + line + ": Different types at Return");
    }

    @Override
    public Type comparison(Type other, int line, int column) {
        if(other instanceof IntType | other instanceof DoubleType | other instanceof CharType)
            return new IntType(line, column);
        if(other instanceof ErrorType)
            return other;
        return super.arithmetic(other, line, column);
    }
}
