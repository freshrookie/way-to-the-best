package object;

import ast.ASTree;
import function.FuncEvaluator;
import function.NestedEnv;
import intercepter.BasicEvaluator;
import intercepter.Environment;
import javassist.gluonj.Require;
import javassist.gluonj.Reviser;
import lexer.StoneException;
import parser.PrimaryExpr;

import java.util.List;

@Require(FuncEvaluator.class) @Reviser public class ClassEvaluator {
    @Reviser public static class ClassStmntEx extends ClassStmnt {

        public ClassStmntEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            ClassInfo classInfo = new ClassInfo(this, environment);
            ((FuncEvaluator.EnvEx)environment).putNew(name(), classInfo);
            return name();

        }
    }

    @Reviser public static class ClassBodyEx extends ClassBody {
        public ClassBodyEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            for (ASTree asTree : this) {
                ((BasicEvaluator.ASTreeEx)asTree).eval(environment);
            }
            return null;
        }

    }

    @Reviser public static class DotEx extends Dot {
        public DotEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment, Object value) {
            String name = name();
            if (value instanceof ClassInfo) {
                if ("new".equals(name)) {
                    ClassInfo classInfo = (ClassInfo)value;
                    NestedEnv nestedEnv = new NestedEnv(environment);
                    StoneObject stoneObject = new StoneObject(nestedEnv);
                    nestedEnv.put("this", stoneObject);
                    initObject(classInfo, nestedEnv);
                    return stoneObject;

                }
            } else if (value instanceof StoneObject) {
                try {
                    return ((StoneObject)value).read(name);
                } catch (StoneObject.AccessException e) {

                }
            }
            throw new StoneException("bad member access: " + name, this);
        }

        public void initObject(ClassInfo classInfo, Environment environment) {
            if (classInfo.superClass() != null) {
                initObject(classInfo.superClass(), environment);
            }
            ((ClassBodyEx)classInfo.body()).eval(environment);
        }
    }

    @Reviser public static class AssignEx extends BasicEvaluator.BinaryEx {
        public AssignEx(List<ASTree> children) {
            super(children);
        }

        @Override protected Object computeAssign(Environment environment, Object rvalue) {
            ASTree left = left();
            if (left instanceof PrimaryExpr) {

                FuncEvaluator.PrimaryEx p = (FuncEvaluator.PrimaryEx)left;
                if (p.hasPostfix(0) && p.postfix(0) instanceof Dot) {
                    Object o = p.evalSubExpr(environment, 1);
                    if (o instanceof StoneObject) {
                        return setField((StoneObject)o, (Dot)p.postfix(0), rvalue);
                    }
                }

            }
            return super.computeAssign(environment, rvalue);

        }

        private Object setField(StoneObject o, Dot postfix, Object rvalue) {
            try {
                o.write(postfix.name(), rvalue);
                return rvalue;
            } catch (StoneObject.AccessException e) {
                throw new StoneException("bad member access " + location() + ":" + postfix.name());
            }

        }

    }

}
