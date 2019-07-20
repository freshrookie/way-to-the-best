package parser;

import ast.ASTree;
import ast.BinaryExpr;
import ast.Name;
import ast.NumberLiteral;
import lexer.Lexer;
import lexer.ParseException;
import lexer.Token;


import java.util.HashSet;

public class BasicParser {

   protected HashSet<String> reserved = new HashSet<String>();
    protected Parser.Operators operators = new Parser.Operators();
    protected  Parser expr0 = Parser.rule();
    protected  Parser primary = Parser.rule(PrimaryExpr.class)
        .or(Parser.rule().sep("(").ast(expr0).sep(")"), Parser.rule().number(NumberLiteral.class),
            Parser.rule().identifier(Name.class, reserved), Parser.rule().string(StringLiteral.class));

    protected   Parser factor = Parser.rule().or(Parser.rule(NegativeExpr.class).sep("-").ast(primary), primary);

    protected   Parser expr = expr0.expression(BinaryExpr.class, factor, operators);

    protected    Parser statement0 = Parser.rule();
    protected   Parser block = Parser.rule(BlockStmnt.class).sep("{").option(statement0)
        .repeat(Parser.rule().sep(";", Token.EOL).option(statement0)).sep("}");

    protected  Parser simple = Parser.rule(PrimaryExpr.class).ast(expr);

    protected Parser statement = statement0
        .or(Parser.rule(IfStmnt.class).sep("if").ast(expr).ast(block).option(Parser.rule().sep("else").ast(block)),
            Parser.rule(WhileStmnt.class).sep("while").ast(expr).ast(block), simple);

    protected  Parser program = Parser.rule().or(statement0, Parser.rule(NullStmnt.class).sep(";", Token.EOL));

    public BasicParser() {

        reserved.add(";");
        reserved.add("}");
        reserved.add(Token.EOL);

        operators.add("=", 1, Parser.Operators.RIGHT);
        operators.add("==", 2, Parser.Operators.LEFT);
        operators.add(">", 2, Parser.Operators.LEFT);
        operators.add("<", 2, Parser.Operators.LEFT);
        operators.add("+", 3, Parser.Operators.LEFT);
        operators.add("-", 3, Parser.Operators.LEFT);
        operators.add("*", 4, Parser.Operators.LEFT);
        operators.add("/", 4, Parser.Operators.LEFT);
        operators.add("%", 4, Parser.Operators.LEFT);

    }

    public ASTree parse(Lexer lexer) throws ParseException {
        return program.parse(lexer);
    }
}
