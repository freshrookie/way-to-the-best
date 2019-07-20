package lexer;

import ast.ASTree;

public class StoneException extends RuntimeException {
    public StoneException(String m) {
        super(m);
    }

    public StoneException(String m, ASTree tree) {
        super(m + " " + tree.location());
    }
}
