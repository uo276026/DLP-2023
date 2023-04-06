package semantic;

import ast.*;
import ast.Expression.*;
import ast.Type.*;
import visitor.AbstractVisitor;
import ast.Statement.*;

import java.util.List;
import java.util.stream.Collectors;

public class TypeCheckingVisitor extends AbstractVisitor {

    /*3 preguntas:
        - ¿Hay que calcular algo, el nuevo atributo?
        - ¿Hay que recorrer, es decir hay que entrar en sus hijos y recorrerlos?
        - ¿Hay que comprobar algo?
    */

    @Override
    public Void visit(Arithmetic a, Object p) {
        a.ex1.accept(this,p);
        a.ex2.accept(this,p);
        a.setLValue(false); //Una op nunca ira a la izquierda

        a.setType(a.ex1.getType().arithmetic(a.ex2.getType()));
        return null;
    }

    //Assignment NO ES UNA EXPRESION no hay que hacer nada con el lvalue,
    //visita hijo izq, hijo derecho, comprobar si izq es lvalue, si no lo es, error
    @Override
    public Void visit(Assignment a, Object p) {
        a.expression1.accept(this,p);
        a.expression2.accept(this,p);
        if(!a.expression1.getLValue()) {
            ErrorType et = new ErrorType(a.expression1.getLine(), a.expression1.getColumn(),
                    "ERROR in line " + a.expression1.getLine() + ": Wrong type at assignment");
        }

        //El tipo de exp1 ya esta definido, pero hay que comprobar que es el mismo que tipo 2
        a.expression1.setType(a.expression2.getType().MustPromoteTo(a.expression1.getType()));
        return null;
    }

    //Igual que la asignacion, comprobar que la expression es lvalue
    @Override
    public Void visit(Input a, Object p) {
        a.expression.accept(this,p);
        if(!a.expression.getLValue()){
            ErrorType et = new ErrorType(a.expression.getLine(), a.expression.getColumn(),
                    "ERROR in line " + a.expression.getLine() + ": Wrong type at Input");
        }
        return null;
    }

    @Override
    public Void visit(Variable v, Object p) {
        v.setLValue(true);

        v.setType(v.def.getType());
        return null;
    }

    @Override
    public Void visit(IntLiteral i, Object p) {
        i.setLValue(false);

        i.setType(new IntType(i.getLine(), i.getColumn()));
        return null;
    }

    @Override
    public Void visit(StructAccess sa, Object p) {
        sa.expression.accept(this,p);
        sa.setLValue(false);
        return null;
    }

    //visitar expresiones hijos,
    @Override
    public Void visit(ArrayAccess e, Object p) {
        e.expression.accept(this,p);
        e.name.accept(this,p);
        e.setLValue(true);

        e.setType(e.getType().squareBrackets(e.expression.getType()));
        return null;
    }

    @Override
    public Void visit(Cast cast, Object p) {
        cast.type.accept(this,p);
        cast.expression.accept(this,p);
        cast.setLValue(false);
        return null;
    }

    @Override
    public Void visit(CharLiteral c, Object p) {
        c.setLValue(false);

        c.setType(new CharType(c.getLine(), c.getColumn()));
        return null;
    }

    @Override
    public Void visit(Comparison c, Object p) {
        c.expression1.accept(this,p);
        c.expression2.accept(this,p);
        c.setLValue(false);
        return null;
    }

    @Override
    public Void visit(DoubleLiteral d, Object p) {
        d.setLValue(false);

        d.setType(new DoubleType(d.getLine(), d.getColumn()));
        return null;
    }

    @Override
    public Void visit(FunctionDefinition f, Object p) {
        f.getType().accept(this,p);
        for(Statement s: f.statements)
            s.accept(this,p);
        f.setLValue(false);

        return null;
    }

    @Override
    public Void visit(FunctionInvocation f, Object p) {
        f.defName.accept(this,p);
        for(Expression s: f.expressions)
            s.accept(this,p);
        f.setLValue(true);

        List<Type> tipos = f.expressions.stream().map(Expression::getType).collect(Collectors.toList());
        f.setType(f.defName.getType().parenthesis(tipos));
        return null;
    }


    @Override
    public Void visit(UnaryMinus unaryMinus, Object p) {
        unaryMinus.expression.accept(this,p);
        unaryMinus.setLValue(false);
        return null;
    }

    @Override
    public Void visit(UnaryNot unaryNot, Object p) {
        unaryNot.expression.accept(this,p);
        unaryNot.setLValue(false);
        return null;
    }

    @Override
    public Void visit(Logical logical, Object p) {
        logical.expression1.accept(this,p);
        logical.expression2.accept(this,p);
        logical.setLValue(false);
        return null;
    }


}
