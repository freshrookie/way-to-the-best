package array;

import function.ClosureEvaluator;
import javassist.gluonj.util.Loader;
import local.NativeEvaluator;
import object.ClassEvaluator;
import object.ClassInterpreter;

public class ArrayRunner {
    public static void main(String[] args) throws Throwable {
        Loader.run(ClassInterpreter.class, args, ClassEvaluator.class, ArrayEvaluator.class, NativeEvaluator.class,
            ClosureEvaluator.class);

    }
}
