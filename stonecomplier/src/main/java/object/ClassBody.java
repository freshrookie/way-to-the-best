package object;

import ast.ASTList;
import ast.ASTree;

import java.util.List;

public class ClassBody extends ASTList {
    public ClassBody(List<ASTree> children) {
        super(children);
    }
}
