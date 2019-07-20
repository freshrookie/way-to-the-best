package array;

import ast.ASTree;
import function.FuncEvaluator;
import intercepter.BasicEvaluator;
import intercepter.Environment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import lexer.StoneException;

import java.util.List;

@Require({FuncEvaluator.class, ArrayParser.class}) @Reviser public class ArrayEvaluator {
    @Reviser public static class ArrayLitEx extends ArrayLiteral {
        public ArrayLitEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            int s = numChildren();
            int i = 0;
            Object[] res = new Object[s];
            for (ASTree asTree : this) {
                res[i++] = ((BasicEvaluator.ASTreeEx)asTree).eval(environment);
            }
            return res;
        }
    }

    @Reviser public static class ArrayRefEx extends ArrayRef {
        public ArrayRefEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment, Object value) {
            if (value instanceof Object[]) {
                Object o = ((Object[])value)[(Integer)((BasicEvaluator.ASTreeEx)index()).eval(environment)];
                return o;
            }
            throw new StoneException("bad array access" + this);
        }

    }

    @Reviser public static class AssignEx extends BasicEvaluator.BinaryEx {
        public AssignEx(List<ASTree> children) {
            super(children);
        }

        @Override protected Object computeAssign(Environment environment, Object right) {
            ASTree le = left();

            if (le instanceof FuncEvaluator.PrimaryEx) {
                FuncEvaluator.PrimaryEx p = (FuncEvaluator.PrimaryEx)le;
                if (p.hasPostfix(0) && p.postfix(0) instanceof ArrayRef) {
                    Object a = p.evalSubExpr(environment, 1);
                    if (a instanceof Object[]) {

                        ArrayRef arrayRef = (ArrayRef)(p.postfix(0));
                        Object eval = ((BasicEvaluator.ASTreeEx)arrayRef.index()).eval(environment);
                        if (eval instanceof Integer) {
                            ((Object[])a)[(Integer)eval] = right;
                            return right;
                        }
                    }
                    throw new StoneException("bad array access", this);
                }
            }
            return super.computeAssign(environment, right);
        }
    }
}
