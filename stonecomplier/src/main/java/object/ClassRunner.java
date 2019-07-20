package object;

import function.ClosureEvaluator;
import javassist.gluonj.util.Loader;
import local.NativeEvaluator;

public class ClassRunner {

    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class, NativeEvaluator.class, ClosureEvaluator.class);
    }
}
