package ast;


import java.util.List;

public class BinaryExpr extends ASTList {

    public BinaryExpr(List<ASTree> children) {
        super(children);
    }

    public ASTree left() {
        return child(0);
    }

    public ASTree right() {
        return child(2);
    }

    public String operator() {
        return ((ASTLeaf)child(1)).token().getText();
    }
}
