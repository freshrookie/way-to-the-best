package object;

import function.FuncEvaluator;
import intercepter.Environment;

public class StoneObject {

    public static class AccessException extends Exception {

    }

    protected Environment environment;

    public StoneObject(Environment env) {
        environment = env;
    }

    public String toString() {
        return "<object:" + hashCode() + ">";
    }

    public Object read(String member) throws AccessException {
        return getEnv(member).get(member);

    }

    public void write(String member, Object value) throws AccessException {

            ((FuncEvaluator.EnvEx)getEnv(member)).putNew(member, value);

    }

    private Environment getEnv(String member) throws AccessException {
        Environment where = ((FuncEvaluator.EnvEx)environment).where(member);
        if (where != null && where == environment) {
            return where;
        } else {
            throw new AccessException();
        }
    }

}
