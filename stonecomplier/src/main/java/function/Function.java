package function;

import intercepter.Environment;
import parser.BlockStmnt;

public class Function {
    protected ParameterList parameters;
    protected BlockStmnt body;
    protected Environment env;

    public Function(ParameterList parameters, BlockStmnt body, Environment environment) {
        this.parameters = parameters;
        this.body = body;
        this.env = environment;
    }

    public ParameterList parameters() {
        return parameters;
    }

    public BlockStmnt body() {
        return body;
    }

    public Environment makeEnv() {
        return new NestedEnv(env);
    }

    @Override public String toString() {
        return "<fun:" + hashCode() + ">";
    }
}





