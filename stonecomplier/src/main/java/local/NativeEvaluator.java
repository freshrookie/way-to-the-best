package local;

import ast.ASTree;
import function.FuncEvaluator;
import intercepter.BasicEvaluator;
import intercepter.Environment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import lexer.StoneException;

import java.util.List;

@Require(FuncEvaluator.class)
@Reviser
public class NativeEvaluator {
    @Reviser public static class NativeArgEx extends FuncEvaluator.ArgumentsEx {

        public NativeArgEx(List<ASTree> children) {
            super(children);
        }

        @Override public Object eval(Environment callerEnv, Object value) {

            if (! (value instanceof NativeFunction)) {
                return super.eval(callerEnv, value);
            }
            NativeFunction func = (NativeFunction)value;
            int numOfParameters = func.numOfParameters();
            if (numOfParameters != size()) {
                throw new StoneException("bad number of arguments", this);

            }

            Object[] objects = new Object[numOfParameters];
            int n = 0;
            for (ASTree asTree : this) {
                objects[n++]=((BasicEvaluator.ASTreeEx)asTree).eval(callerEnv);
            }

            return func.invoke(objects, this);

        }

    }



}
