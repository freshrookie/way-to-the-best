package function;

import parser.Parser;

public class ClosureParser extends FuncParser {

    public ClosureParser() {
        primary.insertChoice(Parser.rule(Fun.class).sep("fun").ast(paramList).ast(block));
    }
}
