package codegenerator;
import ast.Expression.StructField;
import ast.FunctionDefinition;
import ast.Statement.Statement;
import ast.Type.FunctionType;
import ast.Type.StructType;
import ast.VariableDefinition;
import visitor.AbstractVisitor;

public class OffSetVisitor extends AbstractVisitor<Void, Void> {

    int sumGlobalOffset, sumLocalOffset=0;

    @Override
    public Void visit(VariableDefinition vd, Void p) {
        vd.type.accept(this, p);
        if(vd.getScope()==0) { //VARIABLES GLOBALES
            vd.setOffSet(sumGlobalOffset);
            sumGlobalOffset+=vd.type.numberOfBytes();
        } else { //VARIABLES LOCALES, por parametros no llega aqui
            sumLocalOffset+=vd.type.numberOfBytes();
            vd.setOffSet(-sumLocalOffset);
        }
        return null;
    }

    @Override
    public Void visit(FunctionDefinition f, Void p) {
        f.getFunctionType().accept(this,p);
        sumLocalOffset=0; //ponemos a 0 ya que son variables locales de una nueva funcion
        for(Statement s: f.statements)
            s.accept(this, p);
        return null;
    }

    @Override
    public Void visit(FunctionType f, Void p) {
        f.returnType.accept(this,p);
        // no visitar PARAMETROS, calculamos el offset aqui
        int sumaOffsetParametrosDerecha=0;
        for(int i=f.parameters.size()-1;i>=0;i--){
            f.parameters.get(i).setOffSet(4+sumaOffsetParametrosDerecha);
            sumaOffsetParametrosDerecha+=f.parameters.get(i).type.numberOfBytes();
        }
        return null;
    }

    @Override
    public Void visit(StructType structType, Void p) {
        //FIELDS
        int sumOffsetFields=0;
        for(StructField sf:structType.fields) {
            sf.accept(this, p);
            sf.setOffSet(sumOffsetFields);
            sumOffsetFields+=sf.type.numberOfBytes();
        }
        return null;
    }


}
