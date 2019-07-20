package function;

import intercepter.BasicInterpreter;
import lexer.ParseException;

public class FuncInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new FuncParser(), new NestedEnv());

    }
}
