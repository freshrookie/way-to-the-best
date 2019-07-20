package local;

import function.ClosureParser;
import function.NestedEnv;
import intercepter.BasicInterpreter;
import lexer.ParseException;

public class NativeInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClosureParser(), new Natives().environment(new NestedEnv()));
    }
}
