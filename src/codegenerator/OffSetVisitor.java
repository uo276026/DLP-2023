package codegenerator;
import ast.Expression.StructField;
import ast.FunctionDefinition;
import ast.Statement.Statement;
import ast.Type.FunctionType;
import ast.Type.StructType;
import ast.VariableDefinition;
import visitor.AbstractVisitor;

public class OffSetVisitor extends AbstractVisitor<Integer, Void> {

    int sumGlobalOffset=0;

    @Override
    public Void visit(VariableDefinition vd, Integer sumGlobal) {
        vd.type.accept(this, sumGlobal);
        int sumLocalOffset=0;
        if(vd.getScope()==0) { //VARIABLES GLOBALES
            vd.setOffSet(sumGlobalOffset);
            sumGlobalOffset+=vd.getType().numberOfBytes();
        } else { //VARIABLES LOCALES, por parametros no llega aqui
            sumLocalOffset+=vd.type.numberOfBytes();
            vd.setOffSet(-sumLocalOffset);
        }
        return null;
    }

    @Override
    public Void visit(FunctionType f, Integer p) {
        f.returnType.accept(this,p);
        // no visitar PARAMETROS, calculamos el offset aqui
        int sumaOffsetParametrosDerecha=0;
        for(int i=f.parameters.size()-1;i>=0;i--){
            f.parameters.get(i).setOffSet(4+sumaOffsetParametrosDerecha);
            sumaOffsetParametrosDerecha+=f.parameters.get(i).getType().numberOfBytes();
        }
        return null;
    }

    @Override
    public Void visit(StructType structType, Integer p) {
        //FIELDS
        int sumOffsetFields=0;
        for(StructField sf:structType.fields) {
            sf.accept(this, p);
            sf.setOffSet(sumOffsetFields);
            sumOffsetFields+=sf.getType().numberOfBytes();
        }
        return null;
    }


}
