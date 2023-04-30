package ast.Type;

import ast.Expression.StructField;
import visitor.Visitor;


import java.util.List;

public class StructType extends AbstractType {

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

    @Override
    public int numberOfBytes() {
        int sumBytes=0;
        for(StructField s:fields)
            sumBytes+=s.type.numberOfBytes();
        return sumBytes;
    }

    @Override
    public Type dot(String name, int line, int column){
        for(StructField sf:fields){
            if (sf.getName().equals(name))
                return sf.type;
        }
        return new ErrorType(getLine(), getColumn(), "ERROR in line "+line+": " +
                "Variable '"+name+"' doesn't exist in Struct");
    }

    public StructField getField(String fieldName){
        for(StructField sf:fields){
            if(sf.getName().equals(fieldName))
                return sf;
        }
        return null;
    }

    @Override
    public String toString(){
        //RecordType[fields:[Field[name:day,type:IntType offset:0], Field[name:month,type:IntType offset:2], Field[name:year,type:IntType offset:4]]] date (offset -54)
        String str = "RecordType[fields:[";
        for(int i=0;i<= fields.size()-1;i++){
            StructField field= fields.get(i);
            str+="Field[name:"+field.getName()+",type:"+field.type+" offset:"+field.getOffSet()+"]";
            if(i!=fields.size()-1)
                str+=", ";

        }
        str+="]]";
        return str;
    }

}
