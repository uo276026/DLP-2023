package ast.Expression;

import ast.ASTNode;
import ast.Type.Type;
import visitor.Visitor;

public class StructField extends AbstractExpression implements ASTNode {

    public Type type;
    public String name;
    public int line,column;

    public StructField(int line, int column, Type type, String name){
        this.line=line;
        this.column=column;
        this.type=type;
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

    public String getName() {
        return name;
    }

    @Override
    public <TP, TR> TR accept(Visitor<TP, TR> v, TP p) {
        return v.visit(this,p);
    }
}
