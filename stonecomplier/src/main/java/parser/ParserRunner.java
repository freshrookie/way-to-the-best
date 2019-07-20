package parser;

import ast.ASTree;
import lexer.CodeDialog;
import lexer.Lexer;
import lexer.ParseException;
import lexer.Token;

public class ParserRunner {
    public static void main(String[] args) throws ParseException {

        Lexer l = new Lexer(new CodeDialog());
        BasicParser bp = new BasicParser();
        while (l.peek(0) != Token.EOF) {
            ASTree parse = bp.parse(l);
            System.out.println("=>" + parse.toString());
        }
    }


}
