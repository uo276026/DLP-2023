package semantic;
import ast.*;
import ast.Expression.Variable;
import ast.Statement.Statement;
import visitor.AbstractVisitor;
import symboltable.SymbolTable;
import ast.Type.*;

public class IdentificationVisitor extends AbstractVisitor {

    SymbolTable st = new SymbolTable();

    @Override
    public Void visit(VariableDefinition variableDefinition, Object p) {
        variableDefinition.getType().accept(this,p);
        Definition def;
        def = st.findInCurrentScope(variableDefinition.getName());
        if (def == null) { //Si no la encuentra en el global, no existe, puede definirla
            st.insert(variableDefinition);
        } else { //error, ya existe, no se puede volver a definir
            ErrorType et = new ErrorType(variableDefinition.getLine(), variableDefinition.getColumn(),
                    "ERROR in line " + variableDefinition.getLine() + ": Variable '" + variableDefinition.getName()
                            + "' is already defined");
        }

        return null;
    }

    @Override
    public Void visit(FunctionDefinition f, Object p) {
        if(st.find(f.getName())==null){
            st.insert(f);
        }
        st.set();
        f.tipo.accept(this,p);
        for(Statement s: f.statements)
            s.accept(this,p);
        st.reset();
        return null;
    }



    @Override
    public Void visit(Variable e, Object p) {
        Definition def = st.find(e.getName());
        if(def==null) {
            ErrorType et = new ErrorType(e.getLine(), e.getColumn(),
                    "ERROR in line " + e.getLine() + ": Variable '" + e.getName()
                            + "' is not defined");
        }
        e.def=def;
        return null;
    }


}


