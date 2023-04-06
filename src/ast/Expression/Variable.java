package ast.Expression;

import ast.Definition;
import ast.Expression.Expression;
import ast.Type.Type;
import visitor.Visitor;

public class Variable extends AbstractExpression {
    public String name;
    public int line, column;
    public boolean lvalue;
    public Definition def;

    public Variable(int line, int column, String name) {
        this.line=line;
        this.column=column;
        this.name=name;
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

    public String getName() {
        return name;
    }




}
