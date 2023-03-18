package ast;

import visitor.Visitor;

import java.util.List;

public class Program implements ASTNode {

    public List<Definition> defs;

    public int line;
    public int column;


    public Program(List<Definition> defs){
        this.defs=defs;
    }
    public Program(int line, int column, List<Definition> defs){
        this.line=line;
        this.column=column;
        this.defs=defs;
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
