package ast.Expression;


import ast.Type.Type;
import visitor.Visitor;

public class CharLiteral extends AbstractExpression implements Expression {
    public char value;
    public int line, column;

    public CharLiteral(int line, int column, char value) {
        this.line=line;
        this.column=column;
        this.value=value;
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
