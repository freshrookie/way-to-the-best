package intercepter;

import ast.ASTree;
import function.NestedEnv;
import lexer.CodeDialog;
import lexer.Lexer;
import lexer.ParseException;
import lexer.Token;
import parser.BasicParser;
import parser.NullStmnt;

public class BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new BasicParser(), new BasicEnv());
    }

    protected static void run(BasicParser basicParser, Environment basicEnv) throws ParseException {
        Lexer lexer = new Lexer(new CodeDialog());
        while (lexer.peek(0) != Token.EOF) {
            ASTree parse = basicParser.parse(lexer);
            if (!(parse instanceof NullStmnt)) {
                Object eval = ((BasicEvaluator.ASTreeEx)parse).eval(basicEnv);
                System.out.println("=>" + eval);
            }

        }
    }
}
