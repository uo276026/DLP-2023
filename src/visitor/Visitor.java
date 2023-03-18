package visitor;

import ast.*;

public interface Visitor<TP,TR> {

    TR visit(Arithmetic e, TP p);
    TR visit(Variable e, TP p);
    TR visit(Assignment e, TP p);
    TR visit(ArrayAccess e, TP p);
    TR visit(Program program, TP p);
    TR visit(StructAccess structAccess, TP p);
    TR visit(StructField structField, TP p);
    TR visit(ArrayType arrayType, TP p);
    TR visit(VariableDefinition variableDefinition, TP p);
    TR visit(Cast cast, TP p);
    TR visit(CharLiteral charLiteral, TP p);
    TR visit(CharType charType, TP p);
    TR visit(Comparison comparison, TP p);
    TR visit(Conditional conditional, TP p);
    TR visit(DoubleLiteral doubleLiteral, TP p);
    TR visit(DoubleType doubleType, TP p);
    TR visit(ErrorType errorType, TP p);
    TR visit(FunctionDefinition functionDefinition, TP p);

    TR visit(FunctionInvocation functionInvocation, TP p);

    TR visit(FunctionType functionType, TP p);

    TR visit(Input input, TP p);

    TR visit(IntType intType, TP p);

    TR visit(IntLiteral intLiteral, TP p);

    TR visit(UnaryMinus unaryMinus, TP p);

    TR visit(UnaryNot unaryNot, TP p);

    TR visit(VoidType voidType, TP p);

    TR visit(Iterative iterative, TP p);

    TR visit(Logical logical, TP p);

    TR visit(Print print, TP p);

    TR visit(Return aReturn, TP p);

    TR visit(StructType structType, TP p);


}
