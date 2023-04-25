package codegenerator;

import ast.Definition;
import ast.Type.CharType;
import ast.Type.DoubleType;
import ast.Type.ErrorType;
import ast.Type.Type;
import ast.VariableDefinition;
import com.sun.jdi.IntegerType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CodeGenerator {

    private PrintWriter out;

    public CodeGenerator(String outFileName, String inFileName){
        try {
            out = new PrintWriter(new File(outFileName));
            out.println("\n#source "+'\u0022'+inFileName+'\u0022'+"\n");
        } catch (FileNotFoundException e) {
            new ErrorType(0,0,"Error: Output file doesn´t exist");
        }

    }

    public void pushBP(){
        out.println("\t\tpush\tbp");
        out.flush();
    }

    public void push(char c){
        out.println("\t\tpushb\t"+(int)c);
        out.flush();
    }

    public void push(int i){
        out.println("\t\tpushi\t"+i);
        out.flush();
    }

    public void push(double d){
        out.println("\t\tpushf\t"+d);
        out.flush();
    }


    //direccion
    public void pusha(int offSet) {
        out.println("\t\tpusha\t"+offSet);
        out.flush();
    }

    public void load(Type type) {
        out.println("\t\tload"+type.suffix());
        out.flush();
    }

    public void store(Type type) {
        out.println("\t\tstore"+type.suffix());
        out.flush();
    }

    public void mul(Type type) {
        out.println("\t\tmul"+type.suffix());
        out.flush();
    }

    public void sub(Type type) {
        out.println("\t\tsub"+type.suffix());
        out.flush();
    }

    public void div(Type type) {
        out.println("\t\tdiv"+type.suffix());
        out.flush();
    }

    public void add(Type type) {
        out.println("\t\tadd"+type.suffix());
        out.flush();
    }

    public void mod(Type type) {
        out.println("\t\tmod"+type.suffix());
        out.flush();
    }

    public void call(String name) {
        out.println("\n' Invocation to the "+ name +" function");
        out.println("call "+name);
        out.flush();
    }

    public void halt() {
        out.println("halt\n");
        out.flush();
    }

    public void varDefinition(VariableDefinition d) {
        out.println("\t\t' * "+d.getType().toString()+" "+d.getName()+" (offset "+d.getOffSet()+")");
        out.flush();
    }

    public void printString(String str) {
        out.println(str);
        out.flush();
    }

    public void printLine(int line) {
        out.println("\n#line\t"+line);
        out.flush();
    }


    public void enter(int i) {
        out.println("\t\tenter\t"+i);
        out.flush();
    }

    public void out(Type type) {
        out.println("\t\tout"+type.suffix());
        out.flush();
    }

    public void in(Type type) {
        out.println("\t\tin"+type.suffix());
        out.flush();
    }

    public void convert(Type type, Type typeTo) {
        if(type.suffix()!=typeTo.suffix()){
            if(type.suffix()=="f"&&typeTo.suffix()=="b"){
                out.println("\t\tf2i");
                out.println("\t\ti2b");
            } else if(type.suffix()=="b"&&typeTo.suffix()=="f"){
                out.println("\t\tb2i");
                out.println("\t\ti2f");
            } else {
                out.println("\t\t" + type.suffix() + "2" + typeTo.suffix());
            }
            out.flush();
        }
    }

    public void operation(String operator, Type type) {
        switch(operator){
            case "+":
                add(type);
                break;
            case "-":
                sub(type);
                break;
            case "*":
                mul(type);
                break;
            case "/":
                div(type);
                break;
            case "%":
                mod(type);
                break;
        }
    }


    public void comparison(String operator, Type type) {
        switch(operator){
            case "==":
                out.println("\t\teq"+type.suffix()); //equals
                break;
            case "!=":
                out.println("\t\tne"+type.suffix()); //not equals
                break;
            case "<":
                out.println("\t\tlt"+type.suffix()); //lower than
                break;
            case ">":
                out.println("\t\tgt"+type.suffix()); //greater than
                break;
            case ">=":
                out.println("\t\tge"+type.suffix()); //greater or equals
                break;
            case "<=":
                out.println("\t\tle"+type.suffix()); //lower or equals
                break;
        }
        out.flush();
    }

    public void logical(String operator) {
        switch(operator){
            case "&&":
                out.println("\t\tand");
                break;
            case "||":
                out.println("\t\tor");
                break;
        }
        out.flush();
    }

    public void not() {
        out.println("\t\tnot");
        out.flush();
    }

    public void ret(int returnBytes, int localBytes, int paramBytes) {
        out.println("\t\tret\t"+returnBytes+", "+localBytes+", "+paramBytes);
        out.flush();
    }
}
