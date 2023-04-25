package codegenerator;

import ast.Expression.ArrayAccess;
import ast.Expression.StructAccess;
import ast.Expression.Variable;
import ast.VariableDefinition;

public class AddressCGVisitor extends AbstractCGVisitor<Void, Void>{

    public ValueCGVisitor valueCGVisitor;

    public AddressCGVisitor(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    public void setValueVisitor( ValueCGVisitor valueCGVisitor ) {
        this.valueCGVisitor = valueCGVisitor;
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
    public Void visit(Variable v, Void p) {
        VariableDefinition varDef= (VariableDefinition) v.def;
        if(varDef.getScope()==0){ //globales
            codeGenerator.pusha(varDef.getOffSet());
        } else {
            codeGenerator.pushBP();
            codeGenerator.push(varDef.getOffSet());
            codeGenerator.add(v.getType());
        }
        return null;
    }

    /**
     * address[[StructAccess sa -> exp ID]]() =
     *      address[[exp]]
     *      push exp.type.numberOfBytes()
     *      mul exp.type.suffix
     *      add exp.type.suffix
     */
    @Override
    public Void visit(StructAccess sa, Void p) {
        sa.expression.accept(this,p);
        codeGenerator.push(sa.getType().numberOfBytes());
        codeGenerator.mul(sa.getType());
        codeGenerator.add(sa.getType());
        return null;
    }

    /**
     * address[[ArrayAccess e -> exp1 exp2]]() =
     *      address[[exp1]]
     *      address[[exp2]]
     *      push e.type.numberOfBytes()
     *      mul e.type.suffix
     *      add e.type.suffix
     */
    @Override
    public Void visit(ArrayAccess e, Void p) {
        e.value.accept(this,p);
        e.name.accept(this,p);
        codeGenerator.push(e.getType().numberOfBytes());
        codeGenerator.mul(e.getType());
        codeGenerator.add(e.getType());
        return null;
    }

//    /**
//     * address[[Print a -> exp]]() =
//     *
//     *      address[[exp1]]
//     *      address[[exp2]]
//     *      push e.type.numberOfBytes()
//     *      mul e.type.suffix
//     *      add e.type.suffix
//     */
//    @Override
//    public Void visit(Print a, Void p) {
//        codeGenerator.printString("\t\t' * Write");
//        a.expression.accept(this,p);
//        return null;
//    }



}
