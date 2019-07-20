package array;

import function.FuncParser;
import javassist.gluonj.Reviser;
import parser.Parser;

@Reviser
public class ArrayParser extends FuncParser {

    Parser elements = Parser.rule(ArrayLiteral.class).ast(expr).repeat(Parser.rule().sep(",").ast(expr));

    public ArrayParser() {
        reserved.add("]");
        primary.insertChoice(Parser.rule().sep("[").maybe(elements).sep("]"));
        postfix.insertChoice(Parser.rule(ArrayRef.class).sep("[").ast(expr).sep("]"));
    }

}
