package function;


import intercepter.Environment;

import java.util.HashMap;

public class NestedEnv implements Environment {

    protected HashMap<String, Object> values;
    protected Environment outer;

    public NestedEnv(Environment outer) {
        values = new HashMap<String, Object>();
        this.outer = outer;
    }

    public NestedEnv() {
        this(null);
    }

    public void setOuter(Environment outer) {
        this.outer = outer;
    }

    public void putNew(String name, Object vaule) {

        values.put(name, vaule);
    }
    public void put(String name, Object value) {
        Environment e = where(name);
        if (e != null) {
             ((FuncEvaluator.EnvEx)e).putNew(name, value);
        } else {
            putNew(name, value);
        }

    }

    public Environment where(String name) {
        if (values.containsKey(name)) {
            return this;
        } else if (outer != null) {
            return ((FuncEvaluator.EnvEx)outer).where(name);
        } else {
            return null;
        }
    }

    public Object get(String name) {
        if (values.containsKey(name)) {
            return values.get(name);
        } else if (outer != null) {
            return outer.get(name);
        } else {
            return null;
        }
    }
}
