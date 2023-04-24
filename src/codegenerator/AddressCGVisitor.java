package codegenerator;

import ast.Expression.ArrayAccess;
import ast.Expression.StructAccess;
import ast.Expression.Variable;
import ast.Statement.Print;
import ast.Type.StructType;
import ast.VariableDefinition;

public class AddressCGVisitor extends AbstractCGVisitor<Void, Void>{

    public ValueCGVisitor valueCGVisitor;

    public AddressCGVisitor(CodeGenerator codeGenerator) {
        super(codeGenerator);
    }

    public void setValueVisitor( ValueCGVisitor valueCGVisitor ) {
        this.valueCGVisitor = valueCGVisitor;
    }

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

    @Override
    public Void visit(StructAccess sa, Void p) {
        sa.expression.accept(this,p);
        StructType type = (StructType) sa.expression.getType();
        codeGenerator.push(sa.getType().numberOfBytes());
        codeGenerator.mul(sa.getType());
        codeGenerator.add(sa.getType());
        return null;
    }

    @Override
    public Void visit(ArrayAccess e, Void p) {
        e.value.accept(this,p);
        e.name.accept(this,p);
        codeGenerator.push(e.getType().numberOfBytes());
        codeGenerator.mul(e.getType());
        codeGenerator.add(e.getType());
        return null;
    }

    @Override
    public Void visit(Print a, Void p) {
        codeGenerator.printString("\t\t' * Write");
        a.expression.accept(this,p);
        return null;
    }



}
