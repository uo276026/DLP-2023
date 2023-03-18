package ast;

import visitor.Visitor;

import java.util.List;

public class Iterative implements Statement{
    public Expression expression;
    public List<Statement> ifBody;
    public List<Statement> elseBody;

    public int line;
    public int column;

    public Iterative(int line, int column, Expression expression, List<Statement> ifBody, List<Statement> elseBody){
        this.expression=expression;
        this.ifBody=ifBody;
        this.elseBody=elseBody;
        this.line=line;
        this.column=column;
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
