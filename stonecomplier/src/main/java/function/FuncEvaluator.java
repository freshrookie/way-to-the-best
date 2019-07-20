package function;

import ast.ASTree;
import intercepter.BasicEvaluator;
import intercepter.Environment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import lexer.StoneException;
import parser.PrimaryExpr;

import java.lang.annotation.Target;
import java.util.List;

@Require(BasicEvaluator.class) @Reviser public class FuncEvaluator {
    @Reviser public static interface EnvEx extends Environment {
        void putNew(String name, Object value);

        Environment where(String name);

        void setOuter(Environment environment);
    }

    @Reviser public static class DefStmntEx extends DefStmnt {
        public DefStmntEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            ((EnvEx)environment).putNew(name(), new Function(parameters(), body(), environment));
            return name();
        }
    }


    @Reviser public static class ParamEx extends ParameterList {
        public ParamEx(List<ASTree> children) {
            super(children);
        }

        public void eval(Environment environment, int index, Object value) {
            ((EnvEx)environment).putNew(name(index), value);
        }

    }

    @Reviser public static abstract class PostfixEx extends Postfix {
        public PostfixEx(List<ASTree> children) {
            super(children);
        }

        public abstract Object eval(Environment environment, Object value);

    }

    @Reviser public static class ArgumentsEx extends Arguments {
        public ArgumentsEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment callerEnv, Object value) {
            if (!(value instanceof Function)) {
                throw new StoneException("bad function", this);
            }
            if (((Function)value).parameters().size() != size()) {
                throw new StoneException("bad number of arguments", this);
            }
            Environment newEnv = ((Function)value).makeEnv();
            int num = 0;
            for (ASTree asTree : this) {
                ((ParamEx)((Function)value).parameters())
                    .eval(newEnv, num++, ((BasicEvaluator.ASTreeEx)asTree).eval(callerEnv));
            }
            return ((BasicEvaluator.BlockEx)((Function)value).body()).eval(newEnv);

        }
    }

    @Reviser public static class PrimaryEx extends PrimaryExpr {
        public PrimaryEx(List<ASTree> children) {
            super(children);
        }

        public ASTree operand() {
            return child(0);
        }

        public Postfix postfix(int i) {
            return (Postfix)child(numChildren() - 1 - i);
        }

        public boolean hasPostfix(int i) {
            return numChildren() - i > 1;
        }

        public Object eval(Environment environment) {
            return evalSubExpr(environment, 0);
        }

        public Object evalSubExpr(Environment environment, int i) {
            if (hasPostfix(i)) {
                Object o = evalSubExpr(environment, i+1);
                return ((PostfixEx)postfix(i)).eval(environment, o);
            } else {
                return ((BasicEvaluator.ASTreeEx)operand()).eval(environment);
            }
        }

    }

}
