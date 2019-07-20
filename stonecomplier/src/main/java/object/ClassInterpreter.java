package object;

import function.NestedEnv;
import intercepter.BasicInterpreter;
import lexer.ParseException;
import local.Natives;

public class ClassInterpreter extends BasicInterpreter {
    public static void main(String[] args) throws ParseException {
        run(new ClassParser(), new Natives().environment(new NestedEnv()));
    }
}
