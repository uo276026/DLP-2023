package codegenerator;

import ast.Expression.*;

public class ValueCGVisitor extends AbstractCGVisitor<Void, Void>{

    public AddressCGVisitor addressCGVisitor;

    public ValueCGVisitor(CodeGenerator codeGenerator, AddressCGVisitor addressCGVisitor) {
        super(codeGenerator);
        this.addressCGVisitor=addressCGVisitor;
    }

    /**
     * execute[[Variable v -> ID]]() =
     *      address[[v]]()
     *      load v.type.suffix()
     */
    @Override
    public Void visit(Variable v, Void p) {
        v.accept(addressCGVisitor, p);
        codeGenerator.load(v.getType());
        return null;
    }

    /**
     * value[[IntLiteral d -> INT_CONSTANT]]() =
     *      pushi INT_CONSTANT
     */
    @Override
    public Void visit(IntLiteral i, Void o ) {
        codeGenerator.push(i.value);
        return null;
    }

    /**
     * value[[CharLiteral d -> CHAR_CONSTANT]]() =
     *      pushb (int) CHAR_CONSTANT
     */
    @Override
    public Void visit(CharLiteral c, Void o ) {
        codeGenerator.push(c.value);
        return null;
    }

    /**
     * value[[DoubleLiteral d -> DOUBLE_CONSTANT]]() =
     *      pushf DOUBLE_CONSTANT
     */
    @Override
    public Void visit(DoubleLiteral d, Void o ) {
        codeGenerator.push(d.value);
        return null;
    }


    /**
     * value[[Arithmetic a -> exp1 (+|-|*|/|%) exp2]]() =
     *      value[[exp1]]()
     *      value[[exp2]]()
     *      switch (a.operator) {
     *          case "+": add exp1.type.suffix() break;
     *          case "-": sub exp1.type.suffix() break;
     *          case "*": div exp1.type.suffix() break;
     *          case "/": mul exp1.type.suffix() break;
     *          case "%": mod exp1.type.suffix() break;
     */
    @Override
    public Void visit(Arithmetic a, Void p) {
        a.ex1.accept(this,p);
        a.ex2.accept(this,p);
        codeGenerator.operation(a.operator, a.getType());
        return null;
    }

    /**
     * value[[Comparison c -> exp1 (==|!=|>|<|>=|<=) exp2]]() =
     *      value[[exp1]]()
     *      value[[exp2]]()
     *      switch (c.operator) {
     *          case "==": eq exp1.type.suffix() break;
     *          case "!=": ne exp1.type.suffix() break;
     *          case ">": gt exp1.type.suffix() break;
     *          case "<": lt exp1.type.suffix() break;
     *          case ">=": ge exp1.type.suffix() break;
     *          case "<=": le exp1.type.suffix() break;
     */
    @Override
    public Void visit(Comparison c, Void p) {
        c.expression1.accept(this,p);
        c.expression2.accept(this,p);
        codeGenerator.comparison(c.operator, c.getType());
        return null;
    }

    /**
     * value[[Logical a -> exp1 (&&|||) exp2]]() =
     *      value[[exp1]]()
     *      value[[exp2]]()
     *      switch (a.operator) {
     *          case "&&": and exp1.type.suffix() break;
     *          case "||": or exp1.type.suffix() break;
     */
    @Override
    public Void visit(Logical a, Void p) {
        a.expression1.accept(this,p);
        a.expression2.accept(this,p);
        codeGenerator.logical(a.operator);
        return null;
    }

    /**
     * value[[Cast c -> type exp]]() =
     *      value[[exp]]()
     *      convert(exp.type, type)
     */
    public Void visit(Cast c, Void p){
        c.expression.accept(this,p);
        codeGenerator.convert(c.expression.getType(), c.getType());
        return null;
    }

    /**
     * value[[UnaryNot u -> exp]]() =
     *      value[[exp]]()
     *      not
     */
    @Override
    public Void visit(UnaryNot u, Void p) {
        u.expression.accept(this,p);
        codeGenerator.not();
        return null;
    }

}
