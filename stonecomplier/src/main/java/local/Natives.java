package local;

import intercepter.Environment;
import lexer.StoneException;

import javax.swing.*;
import java.lang.reflect.Method;

public class Natives {

    public Environment environment(Environment environment) {
        appendNatives(environment);
        return environment;
    }

    private void appendNatives(Environment environment) {
        append(environment, "print", Natives.class, "print", Object.class);
        append(environment, "read", Natives.class, "read");
        append(environment, "length", Natives.class, "length", String.class);
        append(environment, "toInt", Natives.class, "toInt", Object.class);
        append(environment, "currentTime", Natives.class, "currentTime");


    }


    private void append(Environment environment, String name, Class<?> clazz, String methodName,
        Class<?>... params) {
        Method method =null;
        try {
            method = clazz.getMethod(methodName, params);

        } catch (NoSuchMethodException e) {
            throw new StoneException("cannot find a native function: " + methodName);
        }
        environment.put(name,new NativeFunction(methodName,method));
    }

    public static int print(Object object) {
        System.out.println(object.toString());
        return 0;
    }

    public static String read() {
        return JOptionPane.showInputDialog(null);
    }

    public static int length(String s) {
        return s.length();
    }

    public static int toInt(Object value) {
        if (value instanceof String) {
            return Integer.parseInt((String)value);
        } else if (value instanceof Integer) {
            return (Integer)((Integer)value).intValue();
        } else {
            throw new NumberFormatException(value.toString());
        }
    }

    private static long startTime = System.currentTimeMillis();

    public static int currentTime() {
        return (int)(System.currentTimeMillis()-startTime);
    }



}
