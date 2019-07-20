package function;

import ast.ASTree;
import intercepter.Environment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;

import java.util.List;

@Require(FuncEvaluator.class) @Reviser public class ClosureEvaluator {
    @Reviser public static class FunEx extends Fun {

        public FunEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment env) {
            return new Function(parameters(), body(), env);
        }
    }
}
