package object;

import intercepter.Environment;
import lexer.StoneException;

import java.security.PublicKey;

public class ClassInfo {
    protected ClassInfo superClass;
    protected Environment environment;
    protected ClassStmnt definition;

    public ClassInfo(ClassStmnt cs,Environment env) {
        definition = cs;
        environment = env;
        Object obj = env.get(cs.superClass());
        if (obj == null) {
            superClass = null;

        } else if (obj instanceof ClassInfo) {
            superClass = (ClassInfo)obj;
        } else {
            throw new StoneException("unknow super class" + cs.superClass(), cs);
        }

    }
    public String name(){
        return definition.name();
    }

    public ClassInfo superClass() {
        return superClass;
    }

    public ClassBody body() {
        return definition.body();
    }

    public Environment environment() {
        return environment;
    }

    public String toString() {
        return "<class" + name() + ">";
    }
}
