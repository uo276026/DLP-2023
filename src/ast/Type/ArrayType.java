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

    @Override
    public Type squareBrackets(Type other){
        //a[b] -> Comprobamos que el tipo de b es Int
        if(other instanceof IntType)
            return this.type; //devolvemos el tipo del contenido del array
        if(other instanceof ErrorType)
            return other;
        //Si es double o char, debe dar error
        return super.arithmetic(other);
    }

}
