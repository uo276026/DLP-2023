package ast;

import visitor.Visitor;

import java.util.List;

public class FunctionDefinition extends AbstractExpression implements Definition, Expression{

    public List<Statement> statements;
    public FunctionType tipo;
    public String name;

    public int line;
    public int column;

    public FunctionDefinition(int line, int column, String name, List<Statement> statements, FunctionType tipo){
        this.line=line;
        this.column=column;
        this.name=name;
        this.statements=statements;
        this.tipo=tipo;
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
    public Type getType() {
        return tipo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP p) {
        return v.visit(this,p);
    }
}
