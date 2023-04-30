package ast.Expression;

import ast.ASTNode;
import visitor.Visitor;

public class StructAccess extends AbstractExpression implements ASTNode, Expression {
    public String name;
    public Expression expression;
    public int line, column;


    public StructAccess(int line, int column, String name, Expression expression){
        this.line=line;
        this.column=column;
        this.name=name;
        this.expression=expression;
    }

    public String getName(){
        return name;
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
