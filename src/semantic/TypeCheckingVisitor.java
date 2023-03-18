package semantic;

import ast.*;
import visitor.Visitor;

public class TypeCheckingVisitor implements Visitor {

    //Idenxing ?
    //tipos y en 5/7 statements SOLO RECORRER

    /*3 preguntas:
        - ¿Hay que calcular algo, el nuevo atributo?
        - ¿Hay que recorrer, es decir hay que entrar en sus hijos y recorrerlos?
        - ¿Hay que comprobar algo?
    */

    @Override
    public Void visit(Arithmetic a, Object p) {
        a.ex1.accept(this,p);
        a.ex2.accept(this,p);
        a.setLValue(false); //Una op nunca ira a la izquierda
        return null;
    }

    //Assignment NO ES UNA EXPRESION no hay que hacer nada con el lvalue,
    //visita hijo izq, hijo derecho, comprobar si izq es lvalue, si no lo es, error
    @Override
    public Void visit(Assignment a, Object p) {
        a.expression1.accept(this,p);
        a.expression2.accept(this,p);
        if(!a.expression1.getLValue()) {
            ErrorType et = new ErrorType(a.expression1.getLine(), a.expression1.getColumn(),
                    "ERROR in line " + a.expression1.getLine() + ": Wrong type at assignment");
        }
        return null;
    }

    //Igual que la asignacion, comprobar que derecho es lvalue???
    @Override
    public Void visit(Input a, Object p) {
        a.expression.accept(this,p);
        return null;
    }

    @Override
    public Void visit(Print print, Object p) {
        print.expression.accept(this,p);
        return null;
    }

    @Override
    public Void visit(Variable v, Object p) {
        v.setLValue(true);
        return null;
    }

    @Override
    public Void visit(IntLiteral i, Object p) {
        i.setLValue(false);
        return null;
    }

    //fieldAccess visitar unico hijo y poner lvalue a true
    @Override
    public Void visit(StructAccess sa, Object p) {
        sa.expression.accept(this,p);
        sa.setLValue(true);
        return null;
    }

    //visitar expresiones hijos,
    @Override
    public Void visit(ArrayAccess e, Object p) {
        e.expression.accept(this,p);
        e.name.accept(this,p);
        e.setLValue(true);
        return null;
    }

    @Override
    public Void visit(Program program, Object p) {
        for(Definition d: program.defs)
            d.accept(this, p);
        return null;
    }

    @Override
    public Void visit(StructField structField, Object p) {
        structField.type.accept(this,p);
        return null;
    }

    @Override
    public Void visit(ArrayType arrayType, Object p) {
        arrayType.type.accept(this, p);
        return null;
    }

    @Override
    public Void visit(VariableDefinition variableDefinition, Object p) {
        variableDefinition.type.accept(this, p);
        return null;
    }

    @Override
    public Void visit(Cast cast, Object p) {
        cast.type.accept(this,p);
        cast.expression.accept(this,p);
        cast.setLValue(false);
        return null;
    }

    @Override
    public Void visit(CharLiteral charLiteral, Object p) {
        charLiteral.setLValue(false);
        return null;
    }

    @Override
    public Void visit(CharType charType, Object p) {
        return null;
    }

    @Override
    public Void visit(Comparison c, Object p) {
        c.expression1.accept(this,p);
        c.expression2.accept(this,p);
        c.setLValue(false);
        return null;
    }

    @Override
    public Void visit(Conditional c, Object p) {
        c.expression.accept(this,p);
        for(Statement s: c.whileBody)
            s.accept(this,p);
        return null;
    }

    @Override
    public Void visit(DoubleLiteral doubleLiteral, Object p) {
        doubleLiteral.setLValue(false);
        return null;
    }

    @Override
    public Void visit(DoubleType doubleType, Object p) {
        return null;
    }

    @Override
    public Void visit(ErrorType errorType, Object p) {
        return null;
    }

    @Override
    public Void visit(FunctionDefinition f, Object p) {
        f.tipo.accept(this,p);
        for(Statement s: f.statements)
            s.accept(this,p);
        f.setLValue(false);
        return null;
    }

    @Override
    public Void visit(FunctionInvocation f, Object p) {
        f.defName.accept(this,p);
        for(Expression s: f.expressions)
            s.accept(this,p);
        f.setLValue(false);
        return null;
    }

    @Override
    public Void visit(FunctionType functionType, Object p) {
        functionType.returnType.accept(this,p);
        for(VariableDefinition s: functionType.vars)
            s.accept(this,p);

        return null;
    }



    @Override
    public Void visit(IntType intType, Object p) {
        return null;
    }



    @Override
    public Void visit(UnaryMinus unaryMinus, Object p) {
        unaryMinus.expression.accept(this,p);
        unaryMinus.setLValue(false);
        return null;
    }

    @Override
    public Void visit(UnaryNot unaryNot, Object p) {
        unaryNot.expression.accept(this,p);
        unaryNot.setLValue(false);
        return null;
    }

    @Override
    public Void visit(VoidType voidType, Object p) {
        return null;
    }

    @Override
    public Void visit(Iterative c, Object p) {
        c.expression.accept(this,p);
        for(Statement s: c.ifBody)
            s.accept(this,p);
        for(Statement s: c.elseBody)
            s.accept(this,p);
        return null;
    }

    @Override
    public Void visit(Logical logical, Object p) {
        logical.expression1.accept(this,p);
        logical.expression2.accept(this,p);
        logical.setLValue(false);
        return null;
    }



    @Override
    public Void visit(Return r, Object p) {
        r.expression.accept(this,p);
        return null;
    }

    @Override
    public Void visit(StructType structType, Object p) {
        for(StructField sf:structType.fields)
            sf.accept(this,p);
        return null;
    }

}
