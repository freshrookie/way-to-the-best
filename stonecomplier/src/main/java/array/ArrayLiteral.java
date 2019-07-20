package array;

import ast.ASTList;
import ast.ASTree;

import java.util.List;

public class ArrayLiteral extends ASTList {
    public ArrayLiteral(List<ASTree> children) {
        super(children);
    }

    public int size() {
        return numChildren();
    }

}
