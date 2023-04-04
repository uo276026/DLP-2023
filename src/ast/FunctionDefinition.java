package ast;

import ast.Type.*;
import ast.Statement.*;
import ast.Expression.*;
import visitor.Visitor;

import java.util.List;

public class FunctionDefinition extends AbstractExpression implements Definition, Expression {

    public List<Statement> statements;
    public FunctionType tipo;
    public String name;
    public int scope;

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

    public FunctionType getFunctionType(){
        return tipo;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getScope() {
        return scope;
    }

    @Override
    public void setScope(int scope) {
        this.scope=scope;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP p) {
        return v.visit(this,p);
    }
}
