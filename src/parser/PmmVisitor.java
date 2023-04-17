// Generated from java-escape by ANTLR 4.11.1
package parser;

 /* TODO LO QUE PONGAIS AQUI SE COPIA EN LA CLASE pmmPARSER */
    import ast.*;
    import java.util.*;
    import ast.Type.*;
    import ast.Statement.*;
    import ast.Expression.*;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link PmmParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface PmmVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link PmmParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(PmmParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#main}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMain(PmmParser.MainContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#definition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDefinition(PmmParser.DefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#parametros_funcion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParametros_funcion(PmmParser.Parametros_funcionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#return_func_tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturn_func_tipo(PmmParser.Return_func_tipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(PmmParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#llamada_funcion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLlamada_funcion(PmmParser.Llamada_funcionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#declaracion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaracion(PmmParser.DeclaracionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#idents}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdents(PmmParser.IdentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#asignacion}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAsignacion(PmmParser.AsignacionContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#tipo}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTipo(PmmParser.TipoContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#struct_fields}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_fields(PmmParser.Struct_fieldsContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#struct_field}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStruct_field(PmmParser.Struct_fieldContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#contenido_bucle}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContenido_bucle(PmmParser.Contenido_bucleContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(PmmParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link PmmParser#expresiones_coma}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpresiones_coma(PmmParser.Expresiones_comaContext ctx);
}