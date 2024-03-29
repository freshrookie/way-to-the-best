package object;

import ast.ASTLeaf;
import ast.ASTree;
import function.Postfix;

import java.util.List;

public class Dot extends Postfix {
    public Dot(List<ASTree> children) {
        super(children);
    }

    public String name() {
        return  ((ASTLeaf)child(0)).token().getText();
    }

    public String toString() {
        return "." + name();
    }
}
