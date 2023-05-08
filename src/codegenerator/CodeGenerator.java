package codegenerator;

import ast.Type.ErrorType;
import ast.Type.Type;
import ast.VariableDefinition;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CodeGenerator {

    private PrintWriter out;
    private int labels=0;

    public CodeGenerator(String outFileName, String inFileName){
        try {
            out = new PrintWriter(new File(outFileName));
            out.println("\n#source\t"+'\u0022'+inFileName+'\u0022'+"\n");
        } catch (FileNotFoundException e) {
            new ErrorType(0,0,"Error: Output file doesn´t exist");
        }

    }

    public void pushBP(){
        out.println("\tpush\tbp");
        out.flush();
    }

    public void pop(Type type) {
        out.println("\tpop"+type.suffix());
        out.flush();
    }

    public void push(char c){
        out.println("\tpushb\t"+(int)c);
        out.flush();
    }

    public void push(int i){
        out.println("\tpushi\t"+i);
        out.flush();
    }

    public void push(double d){
        out.println("\tpushf\t"+d);
        out.flush();
    }

    //direccion
    public void pusha(int offSet) {
        out.println("\tpusha\t"+offSet);
        out.flush();
    }

    public void load(Type type) {
        out.println("\tload"+type.suffix());
        out.flush();
    }

    public void store(Type type) {
        out.println("\tstore"+type.suffix());
        out.flush();
    }

    public void mul(Type type) {
        out.println("\tmul"+type.suffix());
        out.flush();
    }

    public void sub(Type type) {
        out.println("\tsub"+type.suffix());
        out.flush();
    }

    public void div(Type type) {
        out.println("\tdiv"+type.suffix());
        out.flush();
    }

    public void add(Type type) {
        out.println("\tadd"+type.suffix());
        out.flush();
    }


    public void mod(Type type) {
        out.println("\tmod"+type.suffix());
        out.flush();
    }

    public void mainInvocationComment(){
        out.println("\n' Invocation to the main function");
        out.flush();
    }

    public void call(String name) {
        out.println("\tcall\t"+name);
        out.flush();
    }

    public void callMain(){
        out.println("call main");
        out.flush();
    }

    public void halt() {
        out.println("halt\n");
        out.flush();
    }

    public void varDefinition(VariableDefinition d) {
        out.println("\t' * "+d.getType().toString()+" "+d.getName()+" (offset "+d.getOffSet()+")");
        out.flush();
    }

    public void printTitle(String str) {
        out.println("\t' * "+str);
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
        out.println("\tenter\t"+i);
        out.flush();
    }

    public void out(Type type) {
        out.println("\tout"+type.suffix());
        out.flush();
    }

    public void in(Type type) {
        out.println("\tin"+type.suffix());
        out.flush();
    }

    public void convert(Type type, Type typeTo) {
        if(type.suffix()!=typeTo.suffix()){
            if(type.suffix()=="f"&&typeTo.suffix()=="b"){
                out.println("\tf2i");
                out.println("\ti2b");
            } else if(type.suffix()=="b"&&typeTo.suffix()=="f"){
                out.println("\tb2i");
                out.println("\ti2f");
            } else {
                out.println("\t" + type.suffix() + "2" + typeTo.suffix());
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
                out.println("\teq"+type.suffix()); //equals
                break;
            case "!=":
                out.println("\tne"+type.suffix()); //not equals
                break;
            case "<":
                out.println("\tlt"+type.suffix()); //lower than
                break;
            case ">":
                out.println("\tgt"+type.suffix()); //greater than
                break;
            case ">=":
                out.println("\tge"+type.suffix()); //greater or equals
                break;
            case "<=":
                out.println("\tle"+type.suffix()); //lower or equals
                break;
        }
        out.flush();
    }

    public void logical(String operator) {
        switch(operator){
            case "&&":
                out.println("\tand");
                break;
            case "||":
                out.println("\tor");
                break;
        }
        out.flush();
    }

    public void not() {
        out.println("\tnot");
        out.flush();
    }

    public void ret(int returnBytes, int localBytes, int paramBytes) {
        out.println("\tret\t"+returnBytes+", "+localBytes+", "+paramBytes);
        out.flush();
    }

    public void printLabel(int label) {
        out.println(" label"+label+":");
        out.flush();
    }

    public int getLabel() {
        int temp = this.labels;
        this.labels+=1;
        return temp;
    }

    public void jz(int labelNumber) {
        out.println("\tjz\tlabel"+labelNumber);
        out.flush();
    }

    public void jmp(int labelNumber) {
        out.println("\tjmp\tlabel"+labelNumber);
        out.flush();
    }



}
