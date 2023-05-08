package codegenerator;

import ast.Expression.ArrayAccess;
import ast.Expression.StructAccess;
import ast.Expression.StructField;
import ast.Expression.Variable;
import ast.Type.IntType;
import ast.Type.StructType;
import ast.VariableDefinition;

public class AddressCGVisitor extends AbstractCGVisitor<Object, Void>{

    public ValueCGVisitor valueCGVisitor;

    public AddressCGVisitor(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    public void setValueCGVisitor(ValueCGVisitor valueCGVisitor){
        this.valueCGVisitor=valueCGVisitor;
    }

    /**
     * address[[Variable v -> ID]]() =
     *      if(v.definition.getScope()==0)
     *          pusha v.definition.offset
     *      else {
     *          push bp
     *          push definition.offset
     *          add v.type.suffix
     *      }
     */
    @Override
    public Void visit(Variable v, Object p) {
        VariableDefinition varDef= (VariableDefinition) v.def;
        if(varDef.getScope()==0){ //globales
            codeGenerator.pusha(varDef.getOffSet());
        } else {
            codeGenerator.pushBP();
            codeGenerator.push(varDef.getOffSet());
            codeGenerator.add(IntType.getInstance());
        }
        return null;
    }

    /**
     * address[[StructAccess exp1 -> exp2 ID]]() =
     *      address[[exp2]]
     *      push exp2.getField(exp1.fieldName).offset
     *      addi
     */
    @Override
    public Void visit(StructAccess sa, Object p) {
        sa.expression.accept(this,p);
        StructType structType = (StructType) sa.expression.getType();
        StructField sf = structType.getField(sa.getName());
        codeGenerator.push(sf.getOffSet());
        codeGenerator.add(IntType.getInstance());
        return null;
    }

    /**
     * address[[ArrayAccess e -> exp2 exp3]]() =
     *      address[[exp2]]
     *      value[[exp3]]
     *      push e.type.numberOfBytes()
     *      mul e.type.suffix
     *      add e.type.suffix
     */
    @Override
    public Void visit(ArrayAccess e, Object p) {
        e.name.accept(this,p);
        e.value.accept(valueCGVisitor,p);
        codeGenerator.push(e.getType().numberOfBytes());
        codeGenerator.mul(IntType.getInstance());
        codeGenerator.add(IntType.getInstance());
        return null;
    }



}
