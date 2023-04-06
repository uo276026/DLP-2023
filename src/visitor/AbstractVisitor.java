package visitor;

import ast.*;
import ast.Expression.*;
import ast.Type.*;
import ast.Statement.*;

public abstract class AbstractVisitor<TP,TR> implements Visitor<TP,TR>{

    @Override
    public TR visit(Arithmetic a, TP p) {
        a.ex1.accept(this,p);
        a.ex2.accept(this,p);
        return null;
    }

    @Override
    public TR visit(Variable e, TP p) {
        return null;
    }

    @Override
    public TR visit(Assignment a, TP p) {
        a.expression1.accept(this,p);
        a.expression2.accept(this,p);
        return null;
    }

    @Override
    public TR visit(Input a, TP p) {
        a.expression.accept(this,p);
        return null;
    }

    @Override
    public TR visit(Print a, TP p) {
        a.expression.accept(this,p);
        return null;
    }

    @Override
    public TR visit(IntLiteral i, TP p) {
        return null;
    }

    @Override
    public TR visit(StructAccess sa, TP p) {
        sa.expression.accept(this,p);
        return null;
    }

    @Override
    public TR visit(ArrayAccess e, TP p) {
        e.value.accept(this,p);
        e.name.accept(this,p);
        return null;
    }

    @Override
    public TR visit(Program program, TP p) {
        for(Definition d: program.defs)
            d.accept(this, p);
        return null;
    }

    @Override
    public TR visit(StructField structField, TP p) {
        structField.type.accept(this,p);
        return null;
    }

    @Override
    public TR visit(ArrayType arrayType, TP p) {
        arrayType.type.accept(this, p);
        return null;
    }

    @Override
    public TR visit(VariableDefinition variableDefinition, TP p) {
        variableDefinition.type.accept(this, p);
        return null;
    }

    @Override
    public TR visit(Cast cast, TP p) {
        cast.typeCast.accept(this,p);
        cast.expression.accept(this,p);
        return null;
    }

    @Override
    public TR visit(CharLiteral charLiteral, TP p) {
        return null;
    }

    @Override
    public TR visit(CharType charType, TP p) {
        return null;
    }

    @Override
    public TR visit(Comparison c, TP p) {
        c.expression1.accept(this,p);
        c.expression2.accept(this,p);
        return null;
    }

    @Override
    public TR visit(Iterative c, TP p) {
        c.expression.accept(this,p);
        for(Statement s: c.whileBody)
            s.accept(this,p);
        return null;
    }

    @Override
    public TR visit(DoubleLiteral doubleLiteral, TP p) {
        return null;
    }

    @Override
    public TR visit(DoubleType doubleType, TP p) {
        return null;
    }

    @Override
    public TR visit(ErrorType errorType, TP p) {
        return null;
    }

    @Override
    public TR visit(FunctionDefinition f, TP p) {
        f.getFunctionType().accept(this,p);
        for(Statement s: f.statements)
            s.accept(this, p);
        return null;
    }

    @Override
    public TR visit(FunctionInvocation f, TP p) {
        f.defName.accept(this,p);
        for(Expression s: f.expressions)
            s.accept(this,p);
        return null;
    }

    @Override
    public TR visit(FunctionType functionType, TP p) {
        functionType.returnType.accept(this,p);
        for(VariableDefinition s: functionType.parameters)
            s.accept(this,p);
        return null;
    }



    @Override
    public TR visit(IntType intType, TP p) {
        return null;
    }



    @Override
    public TR visit(UnaryMinus unaryMinus, TP p) {
        unaryMinus.expression.accept(this,p);
        return null;
    }

    @Override
    public TR visit(UnaryNot unaryNot, TP p) {
        unaryNot.expression.accept(this,p);
        return null;
    }

    @Override
    public TR visit(VoidType voidType, TP p) {
        return null;
    }

    @Override
    public TR visit(Conditional c, TP p) {
        c.expression.accept(this,p);
        for(Statement s: c.ifBody)
            s.accept(this,p);
        for(Statement s: c.elseBody)
            s.accept(this,p);
        return null;
    }

    @Override
    public TR visit(Logical logical, TP p) {
        logical.expression1.accept(this,p);
        logical.expression2.accept(this,p);
        return null;
    }



    @Override
    public TR visit(Return r, TP p) {
        r.expression.accept(this,p);
        return null;
    }

    @Override
    public TR visit(StructType structType, TP p) {
        for(StructField sf:structType.fields)
            sf.accept(this,p);
        return null;
    }

}


