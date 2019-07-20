package intercepter;

import java.util.HashMap;
import java.util.HashSet;

public class BasicEnv implements Environment {
    private HashMap<String, Object> values;

    public BasicEnv() {
        this.values = new HashMap<String, Object>();
    }

    public void put(String name, Object value) {
        values.put(name, value);
    }

    public Object get(String name) {
        return values.get(name);
    }
}
