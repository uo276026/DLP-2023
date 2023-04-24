package ast.Statement;

import ast.Expression.Expression;
import visitor.Visitor;

public class Print implements Statement {
    public Expression expression;

    public int line;
    public int column;

    public Print(int line, int column, Expression exps){
        this.line=line;
        this.column=column;
        this.expression=exps;
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
