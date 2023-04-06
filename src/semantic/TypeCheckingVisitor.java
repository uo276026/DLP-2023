package semantic;

import ast.*;
import ast.Expression.*;
import ast.Type.*;
import visitor.AbstractVisitor;
import ast.Statement.*;

import java.util.List;
import java.util.stream.Collectors;

public class TypeCheckingVisitor extends AbstractVisitor<Type, Void> {

    /*3 preguntas:
        - ¿Hay que calcular algo, el nuevo atributo?
        - ¿Hay que recorrer, es decir hay que entrar en sus hijos y recorrerlos?
        - ¿Hay que comprobar algo?
    */

    @Override
    public Void visit(Arithmetic a, Type p) {
        a.ex1.accept(this,p);
        a.ex2.accept(this,p);
        a.setLValue(false); //Una op nunca ira a la izquierda

        a.setType(a.ex1.getType().arithmetic(a.ex2.getType(), a.getLine(), a.getColumn()));
        return null;
    }

    //Assignment NO ES UNA EXPRESION no hay que hacer nada con el lvalue,
    //visita hijo izq, hijo derecho, comprobar si izq es lvalue, si no lo es, error
    @Override
    public Void visit(Assignment a, Type p) {
        a.expression1.accept(this,p);
        a.expression2.accept(this,p);
        if(!a.expression1.getLValue()) {
            ErrorType et = new ErrorType(a.expression1.getLine(), a.expression1.getColumn(),
                    "ERROR in line " + a.expression1.getLine() + ": Wrong type at assignment");
        }

        //El tipo de exp1 ya esta definido, pero hay que comprobar que es el mismo que tipo 2
        a.expression1.setType(a.expression2.getType().MustPromoteTo(a.expression1.getType(), a.getLine(), a.getColumn()));
        return null;
    }

    //Igual que la asignacion, comprobar que la expression es lvalue
    @Override
    public Void visit(Input a, Type p) {
        a.expression.accept(this,p);
        if(!a.expression.getLValue()){
            ErrorType et = new ErrorType(a.expression.getLine(), a.expression.getColumn(),
                    "ERROR in line " + a.expression.getLine() + ": Wrong type at Input");
        }
        return null;
    }

    @Override
    public Void visit(Variable v, Type p) {
        v.setLValue(true);

        v.setType(v.def.getType());
        return null;
    }

    @Override
    public Void visit(IntLiteral i, Type p) {
        i.setLValue(false);

        i.setType(new IntType(i.getLine(), i.getColumn()));
        return null;
    }

    @Override
    public Void visit(StructAccess sa, Type p) {
        sa.expression.accept(this,p);
        sa.setLValue(true);

        sa.setType(sa.expression.getType().dot(sa.name, sa.getLine(), sa.getColumn()));
        return null;
    }

    //visitar expresiones hijos,
    @Override
    public Void visit(ArrayAccess e, Type p) {
        e.value.accept(this,p);
        e.name.accept(this,p);
        e.setLValue(true);

        e.setType(e.name.getType().squareBrackets(e.value.getType(), e.getLine(), e.getColumn()));
        return null;
    }

    @Override
    public Void visit(Cast cast, Type p) {
        cast.typeCast.accept(this,p);
        cast.expression.accept(this,p);
        cast.setLValue(false);

        cast.setType(cast.typeCast.canBeCastTo(cast.expression.getType(), cast.getLine(), cast.getColumn()));
        return null;
    }

    @Override
    public Void visit(CharLiteral c, Type p) {
        c.setLValue(false);

        c.setType(new CharType(c.getLine(), c.getColumn()));
        return null;
    }

    @Override
    public Void visit(Comparison c, Type p) {
        c.expression1.accept(this,p);
        c.expression2.accept(this,p);
        c.setLValue(false);

        c.setType(c.expression1.getType().comparison(c.expression2.getType(), c.getLine(), c.getColumn()));
        return null;
    }

    @Override
    public Void visit(DoubleLiteral d, Type p) {
        d.setLValue(false);

        d.setType(new DoubleType(d.getLine(), d.getColumn()));
        return null;
    }

    @Override
    public Void visit(FunctionDefinition f, Type p) {
        f.getType().accept(this,p);
        for(Statement s: f.statements)
            s.accept(this, f.tipoFuncion.getReturnType());
        f.setLValue(false);
        return null;
    }

    @Override
    public Void visit(FunctionInvocation f, Type p) {
        f.defName.accept(this,p);
        for(Expression s: f.expressions)
            s.accept(this,p);
        f.setLValue(true);

        List<Type> tipos = f.expressions.stream().map(Expression::getType).collect(Collectors.toList());
        f.setType(f.defName.getType().parenthesis(tipos, f.getLine(), f.getColumn()));
        return null;
    }



    @Override
    public Void visit(UnaryMinus unaryMinus, Type p) {
        unaryMinus.expression.accept(this,p);
        unaryMinus.setLValue(false);

        unaryMinus.setType(unaryMinus.expression.getType().arithmetic(unaryMinus.getLine(), unaryMinus.getColumn()));
        return null;
    }

    @Override
    public Void visit(UnaryNot unaryNot, Type p) {
        unaryNot.expression.accept(this,p);
        unaryNot.setLValue(false);

        //No implementada, no se muy bien como funciona
        unaryNot.setType(unaryNot.expression.getType().unarynot(unaryNot.getLine(), unaryNot.getColumn()));
        return null;
    }

    @Override
    public Void visit(Logical logical, Type p) {
        logical.expression1.accept(this,p);
        logical.expression2.accept(this,p);
        logical.setLValue(false);

        logical.setType(logical.expression1.getType().logic(logical.expression2.getType(), logical.getLine(), logical.getColumn()));
        return null;
    }

    @Override
    public Void visit(Conditional c, Type p) {
        c.expression.accept(this,p);

        c.expression.getType().asLogical(c.getLine(),c.getColumn());

        for(Statement s: c.ifBody)
            s.accept(this,p);
        for(Statement s: c.elseBody)
            s.accept(this,p);
        return null;
    }

    @Override
    public Void visit(Iterative c, Type p) {
        c.expression.accept(this,p);

        c.expression.getType().asLogical(c.getLine(),c.getColumn());

        for(Statement s: c.whileBody)
            s.accept(this,p);
        return null;
    }

    @Override
    public Void visit(Return r, Type other) {
        r.expression.accept(this,other);

        r.expression.getType().BuiltInType(other, r.getLine(), r.getColumn());
        return null;
    }


}
