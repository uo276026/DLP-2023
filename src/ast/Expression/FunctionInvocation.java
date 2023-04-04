package ast.Expression;

import ast.Statement.Statement;
import visitor.Visitor;

import java.util.List;

public class FunctionInvocation extends AbstractExpression implements Statement, Expression{
    public List<Expression> expressions; //Parametros
    public Variable defName; //def f, buscara una variable de nombre f


    public int line;
    public int column;

    public FunctionInvocation(int line, int column, List<Expression> expressions, Variable defName) {
        this.line=line;
        this.column=column;
        this.expressions =expressions;
        this.defName=defName;
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
}
