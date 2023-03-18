package visitor;

import ast.Type;

public abstract class AbstractVisitor<TP,TR> implements Visitor<TP,TR> {

    public TR visit(Type t, TP p) {
        return null;
    }
}
