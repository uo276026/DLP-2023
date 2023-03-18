grammar Pmm;

/* SINTÁCTICO */

@header{
 /* TODO LO QUE PONGAIS AQUI SE COPIA EN LA CLASE pmmPARSER */
    import ast.*;
    import java.util.*;
}

program returns [Program ast, List<Definition> defaux = new ArrayList<Definition>()]:
     (definition { try {
                            for (int i = 0; i < $definition.ast.size(); i++) {
                                $defaux.add($definition.ast.get(i));
                            }
                    }catch(NullPointerException e){  }
                  ; } )* main {  $defaux.add($main.ast); } EOF {$ast = new Program($defaux);}

    ;

main returns [Definition ast, List<Statement> statements = new ArrayList<Statement>()]:
    'def' 'main' '(' ')' ':' '{'
            (declaracion ';' { try {
                  for(int i = 0; i < $declaracion.ast.size(); i++){ $statements.add($declaracion.ast.get(i));}}
                 catch(NullPointerException e){} } )*
            (statement { try { for(int i = 0; i < $statement.ast.size(); i++)
            { $statements.add($statement.ast.get(i));}} catch(NullPointerException e){} } )* '}'
    ;

definition returns [List<Definition> ast = new ArrayList<Definition>(), List<Statement> statements = new ArrayList<Statement>()]:
     declaracion ';' { for (int i = 0; i < $declaracion.ast.size(); i++) {
                            $ast.add($declaracion.ast.get(i));
                        } }
     | 'def' ID '(' parametros_funcion? ')' ':' (return_func_tipo) '{'
                (declaracion ';' { try {
                                        for(int i = 0; i < $declaracion.ast.size(); i++){ $statements.add($declaracion.ast.get(i));}}
                                     catch(NullPointerException e){} } )*
                     (statement { try {
                        for(int i = 0; i < $statement.ast.size(); i++){ $statements.add($statement.ast.get(i));}}
                     catch(NullPointerException e){} } )* '}'
               { List<VariableDefinition> parametros = new ArrayList<VariableDefinition>();
               try { parametros =  $parametros_funcion.ast; } catch(NullPointerException e) {}
              $ast.add(new FunctionDefinition($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.text, $statements,
                    new FunctionType($ID.getLine(), $ID.getCharPositionInLine()+1, $return_func_tipo.ast, parametros))); }
    ;

parametros_funcion returns [List<VariableDefinition> ast = new ArrayList<VariableDefinition>()]:
    ID ':' tipo { $ast.add(new VariableDefinition($ID.getLine(), $ID.getCharPositionInLine()+1, $tipo.ast, $ID.getText())); }
    | ID ':' tipo ',' parametros_funcion { $parametros_funcion.ast.add
                (new VariableDefinition($ID.getLine(), $ID.getCharPositionInLine()+1, $tipo.ast, $ID.getText()));
                 $ast = $parametros_funcion.ast;
                }
    ;

return_func_tipo returns [Type ast]:
    tipo { $ast = $tipo.ast; }
    | { $ast = new VoidType(); } //Si no llega nada el valor de return es void
    ;


expression returns [Expression ast]:
     REAL_CONSTANT { $ast = new DoubleLiteral( $REAL_CONSTANT.getLine(), $REAL_CONSTANT.getCharPositionInLine()+1,
                        LexerHelper.lexemeToReal($REAL_CONSTANT.text) ); }
    | INT_CONSTANT    { $ast = new IntLiteral( $INT_CONSTANT.getLine(), $INT_CONSTANT.getCharPositionInLine()+1,
                        LexerHelper.lexemeToInt($INT_CONSTANT.text) ); }
    | CHAR_CONSTANT { $ast = new CharLiteral( $CHAR_CONSTANT.getLine(), $CHAR_CONSTANT.getCharPositionInLine()+1,
                        LexerHelper.lexemeToChar($CHAR_CONSTANT.text) ); }
    | '(' expression ')' { $ast = $expression.ast; }
    | llamada_funcion { $ast = $llamada_funcion.ast; }
    | ID            { $ast = new Variable( $ID.getLine(), $ID.getCharPositionInLine()+1, $ID.text ); }
    | ex1=expression '[' ex2=expression']' /*acceso a array*/
            { $ast = new ArrayAccess( $ex1.ast.getLine(), $ex1.ast.getColumn(), $ex2.ast, $ex1.ast ); }
    | ID '.' ex=expression /*Acesso a struct*/ { $ast = new StructAccess($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.text, $ex.ast ); }
    | ('('tipo')') expression /*CAST*/ { $ast = new Cast( $expression.ast.getLine(), $expression.ast.getColumn(), $tipo.ast, $expression.ast ); }
    | '-' expression  { $ast = new UnaryMinus($expression.ast.getLine(), $expression.ast.getColumn(), $expression.ast); }
    | '!' expression { $ast = new UnaryNot( $expression.ast.getLine(), $expression.ast.getColumn(), $expression.ast ); }
    | ex1=expression OP=('*'|'/'|'%') ex2=expression { $ast = new Arithmetic( $ex1.ast.getLine(), $ex1.ast.getColumn(),
                                            $ex1.ast, $OP.text, $ex2.ast ); }
    | ex1=expression OP=('+'|'-') ex2=expression { $ast = new Arithmetic( $ex1.ast.getLine(), $ex1.ast.getColumn(),
        $ex1.ast, $OP.text, $ex2.ast ); }
    | ex1=expression OP=('=='|'!='|'<'|'>'|'>='|'<=') ex2=expression { $ast = new Comparison( $ex1.ast.getLine(), $ex1.ast.getColumn(),
                                                                        $ex1.ast, $OP.text, $ex2.ast ); }
    | ex1=expression OP=('&&'|'||') ex2=expression{ $ast = new Logical( $ex1.ast.getLine(), $ex1.ast.getColumn(),
                                        $ex1.ast, $OP.text, $ex2.ast ); }

    ;

// f(xx)
llamada_funcion returns [FunctionInvocation ast, List<Expression> exps = new ArrayList<Expression>()]:
    ID '(' (ex1=expression(','ex2=expression)*)? ')'
            { try { $exps.add($ex1.ast); $exps.add($ex2.ast); } catch(NullPointerException e){ }
              $ast = new FunctionInvocation($ID.getLine(), $ID.getCharPositionInLine()+1, $exps,
                new Variable($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.getText())); }
    ;

declaracion returns [List<VariableDefinition> ast=new ArrayList<VariableDefinition>()]:
    idents ':' tipo { for (int i = 0; i < $idents.ast.size(); i++) {
                      	VariableDefinition vd = new VariableDefinition(
                      	    $idents.ast.get(i).getLine(), $idents.ast.get(i).getColumn(), $tipo.ast, $idents.ast.get(i).getName());
                        $ast.add(vd);
                     }}
    ;




//modificacion con detección de errores de variable repetida
idents returns [List<Variable> ast = new ArrayList<Variable>()]:
        ID { $ast.add(new Variable($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.text)); }
        | ids=idents ',' ID { for (int i = 0; i < $ids.ast.size(); i++) {
                                if($ID.text.equals($ids.ast.get(i).getName())) {
                                    ErrorType et = new ErrorType($ID.getLine(), $ID.getCharPositionInLine()+1,
                                    "ERROR in line "+$ID.getLine()+": duplicated variable '"+$ID.text+"'");
                                }
                             }
                             // Si recorre la lista y no da error, lo añade
                             $ids.ast.add(new Variable($ID.getLine(), $ID.getCharPositionInLine()+1, $ID.text));
                             $ast = $ids.ast;
                        }
    ;


asignacion returns [Assignment ast]:
      ex1=expression '=' ex2=expression { $ast = new Assignment($ex1.ast.getLine(), $ex1.ast.getColumn(), $ex1.ast, $ex2.ast); }
    ;

tipo returns [Type ast, List<StructField> fields=new ArrayList<StructField>()]:
      aux='double' { $ast = new DoubleType($aux.getLine(), $aux.getCharPositionInLine()+1); }
    | aux='char' { $ast = new CharType($aux.getLine(), $aux.getCharPositionInLine()+1); }
    | aux='int' { $ast = new IntType($aux.getLine(), $aux.getCharPositionInLine()+1); }
    | '[' INT_CONSTANT ']' tipo
        { $ast = new ArrayType( $INT_CONSTANT.getLine(), $INT_CONSTANT.getCharPositionInLine()+1,
                    LexerHelper.lexemeToInt($INT_CONSTANT.text), $tipo.ast); }
    | 'struct' '{' struct_fields '}' {
                 $ast = new StructType($struct_fields.ast.get(0).getLine(), $struct_fields.ast.get(0).getColumn(), $struct_fields.ast); }
    ;

struct_fields returns [List<StructField> ast=new ArrayList<StructField>()]:
      struct_field ';' { for (int j = 0; j<$struct_field.ast.size(); j++) { $ast.add($struct_field.ast.get(j)); } }
    | struct_field ';' fields=struct_fields { for (int j = 0; j<$struct_field.ast.size(); j++){
                                                for (int i = 0; i < $fields.ast.size(); i++) {
                                                    if($struct_field.ast.get(j).getName().equals($fields.ast.get(i).getName())) {
                                                        ErrorType et = new ErrorType($struct_field.ast.get(j).getLine(), $struct_field.ast.get(j).getColumn(),
                                                        "ERROR in line "+$struct_field.ast.get(j).getLine()+": duplicated variable '"+$struct_field.ast.get(j).getName()+"'");
                                                    }
                                                }
                                                // Si recorre la lista y no da error, lo añade
                                                $fields.ast.add($struct_field.ast.get(j));
                                                $ast = $fields.ast;
                                             }}
    ;


struct_field returns [List<StructField> ast=new ArrayList<StructField>()]:
    idents ':' tipo { for (int i = 0; i < $idents.ast.size(); i++) {
                        $ast.add(new StructField($tipo.ast.getLine(), $tipo.ast.getColumn(), $tipo.ast, $idents.ast.get(i).getName()));
                     }
                  }
    ;

contenido_bucle returns [List<Statement> ast = new ArrayList<Statement>();]:
    statement { $ast=$statement.ast; }
    | '{' statement '}' { $ast=$statement.ast; }
    | '{' (statement { for (int i = 0; i < $statement.ast.size(); i++) { $ast.add($statement.ast.get(i)); } } )* '}'
    ;

statement returns [List<Statement> ast = new ArrayList<Statement>(), List<Expression> exps = new ArrayList<Expression>(), List<Statement> statements = new ArrayList<Statement>()]:
     // declaracion';' { for (int i = 0; i < $declaracion.ast.size(); i++) { $ast.add($declaracion.ast.get(i)); }  }
      asignacion ';' { $ast.add($asignacion.ast); }
    | llamada_funcion';' { $ast.add($llamada_funcion.ast); }
    | 'return' expression ';' { $ast.add(new Return($expression.ast.getLine(), $expression.ast.getColumn(), $expression.ast)); }
    | 'while' expression ':' '{' (statement {for (int i = 0; i < $statement.ast.size(); i++) { $statements.add($statement.ast.get(i)); }} )* '}'
        { $ast.add(new Conditional($expression.ast.getLine(), $expression.ast.getColumn(), $expression.ast, $statements)); }
    | 'if' (expression|'(' expression ')') ':' ifBody=contenido_bucle ('else' 'if' ':' contenido_bucle)* ('else' ':' elseBody=contenido_bucle)?
        { List<Statement> elseList=new ArrayList<Statement>(); try { elseList=$elseBody.ast; } catch(NullPointerException e){}
          $ast.add(new Iterative($expression.ast.getLine(), $expression.ast.getColumn(), $expression.ast, $ifBody.ast, elseList)); }
    | 'print' ex=expresiones_coma ';' { for (int i = 0; i < $ex.ast.size(); i++) {
            $ast.add(new Print($ex.ast.get(i).getLine(), $ex.ast.get(i).getColumn(), $ex.ast.get(i))); } }
    | 'input' ex=expresiones_coma ';' { for (int i = 0; i < $ex.ast.size(); i++) {
            $ast.add(new Input($ex.ast.get(i).getLine(), $ex.ast.get(i).getColumn(), $ex.ast.get(i))); } }
    ;

expresiones_coma returns [List<Expression> ast = new ArrayList<Expression>()]:
    expression { $ast.add($expression.ast); }
    | expression ',' expresiones_coma { $expresiones_coma.ast.add($expression.ast); $ast = $expresiones_coma.ast; }
    ;








//LEXICO

TRASH: [ \t\n\r] -> skip //identificarlo pero ignorarlo
   ;

//para que no reconozca la letra solo pero que sirve para usar en otros patrones
fragment
LETRA: [A-Za-z];

fragment
NUMERO: [0-9]*;

fragment
REAL_1: '.'(INT_CONSTANT)?(REAL_2)?
    ;
fragment
REAL_2: ('e'|'E')('-'|'+')?INT_CONSTANT
    ;

REAL_CONSTANT:  INT_CONSTANT?(REAL_1|REAL_2)
    ;

INT_CONSTANT: '0'|[1-9][0-9]*
            ;

COMMENT:'#'.*?('\n'|EOF) -> skip
    ;

COMMENTVARIASLINEAS:'"""'.*?'"""' -> skip
    ;

fragment
CONTENIDO_CHAR: . | ('\\'NUMERO) | ('\\''t') | ('\\''n') | ('\\''r')
    ;

CHAR_CONSTANT:  '\'' CONTENIDO_CHAR '\''
    ;

ID: (LETRA| '_') ([A-Za-z0-9]|('_'))*
     ;
