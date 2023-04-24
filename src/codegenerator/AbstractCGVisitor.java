package codegenerator;

import ast.Definition;
import ast.Expression.*;
import ast.Statement.*;
import ast.Type.*;
import ast.FunctionDefinition;
import ast.Program;
import ast.VariableDefinition;
import visitor.Visitor;

public abstract class AbstractCGVisitor<TP,TR> implements Visitor<TP,TR> {

    public CodeGenerator codeGenerator;

    public AbstractCGVisitor(CodeGenerator codeGenerator){
        this.codeGenerator=codeGenerator;
    }

    @Override
    public TR visit(Arithmetic a, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Variable e, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Assignment a, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Input a, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Print a, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(IntLiteral i, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(StructAccess sa, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(ArrayAccess e, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Program program, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(StructField structField, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(ArrayType arrayType, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(VariableDefinition variableDefinition, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Cast cast, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(CharLiteral charLiteral, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(CharType charType, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Comparison c, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Iterative c, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(DoubleLiteral doubleLiteral, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(DoubleType doubleType, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(ErrorType errorType, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(FunctionDefinition f, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(FunctionInvocation f, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(FunctionType functionType, TP p) {
        throw new IllegalStateException();
    }



    @Override
    public TR visit(IntType intType, TP p) {
        throw new IllegalStateException();
    }



    @Override
    public TR visit(UnaryMinus unaryMinus, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(UnaryNot unaryNot, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(VoidType voidType, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Conditional c, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Logical logical, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(Return r, TP p) {
        throw new IllegalStateException();
    }

    @Override
    public TR visit(StructType structType, TP p) {
        throw new IllegalStateException();
    }

}
