package intercepter;

import ast.*;
import javassist.gluonj.Reviser;
import lexer.StoneException;
import lexer.Token;
import parser.*;

import java.util.Iterator;
import java.util.List;

@Reviser public class BasicEvaluator {
    public static final int TRUE = 1;
    public static final int FALSE = 0;

    @Reviser public static abstract class ASTreeEx extends ASTree {
        public abstract Object eval(Environment environment);

    }

    @Reviser public static class ASTListEx extends ASTList {
        public ASTListEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            throw new StoneException("cannot eval:" + toString(), this);
        }

    }

    @Reviser public static class ASTLeafEx extends ASTLeaf {

        public ASTLeafEx(Token token) {
            super(token);
        }

        public Object eval(Environment environment) {
            throw new StoneException("cannot eval:" + toString(), this);
        }

    }

    @Reviser public static class NumberEx extends NumberLiteral {
        public NumberEx(Token token) {
            super(token);
        }

        public Object eval(Environment environment) {
            return value();
        }

    }

    @Reviser public static class StringEx extends StringLiteral {
        public StringEx(Token token) {
            super(token);
        }

        public Object eval(Environment environment) {
            return value();
        }

    }

    @Reviser public static class NameEx extends Name {
        public NameEx(Token token) {
            super(token);
        }

        public Object eval(Environment environment) {
            Object o = environment.get(name());

            if (o == null) {
                throw new StoneException("undefined name: " + name(), this);
            } else {
                return o;
            }
        }
    }

    @Reviser public static class NegativeEx extends NegativeExpr {

        public NegativeEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            Object eval = ((ASTreeEx)operand()).eval(environment);
            if (eval instanceof Integer) {
                return new Integer(-(Integer)((Integer)eval).intValue());

            } else {
                throw new StoneException("bad type for -", this);
            }
        }

    }

    @Reviser public static class BinaryEx extends BinaryExpr {
        public BinaryEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            String operator = operator();
            if ("=".equals(operator)) {
                Object right = ((ASTreeEx)right()).eval(environment);
                return computeAssign(environment, right);
            } else {
                Object left = ((ASTreeEx)left()).eval(environment);
                Object right = ((ASTreeEx)right()).eval(environment);
                return computeOp(left, operator, right);
            }

        }

        protected Object computeAssign(Environment environment, Object right) {
            Object left = left();
            if (left instanceof Name) {
                environment.put(((Name)left).name(), right);
                return right;
            } else {
                throw new StoneException("bad assignment", this);
            }

        }

        private Object computeOp(Object left, String operator, Object right) {
            if (left instanceof Integer && right instanceof Integer) {
                return computeNumber((Integer)left, operator, (Integer)right);
            } else if (operator.equals("+")) {
                return String.valueOf(left) + String.valueOf(right);
            } else if (operator.equals("==")) {
                if (left == "null") {
                    return right == null ? TRUE : FALSE;
                } else {
                    return left.equals(right) ? TRUE : FALSE;
                }
            } else {
                throw new StoneException("bad type", this);
            }

        }

        private Object computeNumber(Integer left, String operator, Integer right) {
            int a = left.intValue();
            int b = right.intValue();
            if (operator.equals("+")) {
                return a + b;
            } else if (operator.equals("-")) {
                return a - b;
            } else if (operator.equals("*")) {
                return a * b;
            } else if (operator.equals("/")) {
                return a / b;
            } else if (operator.equals("%")) {
                return a % b;
            } else if (operator.equals("==")) {
                return a == b ? TRUE : FALSE;
            } else if (operator.equals(">")) {
                return a > b ? TRUE : FALSE;
            } else if (operator.equals("<")) {
                return a < b ? TRUE : FALSE;
            } else {
                throw new StoneException("bad operator", this);
            }

        }
    }

    @Reviser public static class BlockEx extends BlockStmnt {
        public BlockEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            Object result = 0;
            Iterator<ASTree> iterable = this.iterator();
            while (iterable.hasNext()) {
                ASTree next = iterable.next();
                if (!(next instanceof NullStmnt)) {
                    result = ((ASTreeEx)next).eval(environment);
                }
            }
            return result;

        }
    }

    @Reviser public static class IfEx extends IfStmnt {
        public IfEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            Object eval = ((ASTreeEx)condition()).eval(environment);
            if (eval instanceof Integer && (!((Integer)eval).equals(FALSE))) {
                return ((ASTreeEx)thenBlock()).eval(environment);
            } else {
                if (elseBlock() != null) {
                    return ((ASTreeEx)elseBlock()).eval(environment);
                } else {
                    return 0;
                }
            }
        }
    }

    @Reviser public static class WhileEx extends WhileStmnt {
        public WhileEx(List<ASTree> children) {
            super(children);
        }

        public Object eval(Environment environment) {
            Object result = 0;
            for (; ; ) {
                if (((ASTreeEx)condition()).eval(environment) instanceof Integer && ((ASTreeEx)condition())
                    .eval(environment).equals(FALSE)) {
                    return result;
                } else {
                    result = ((ASTreeEx)body()).eval(environment);
                }
            }
        }

    }

}






