package parser;

import ast.ASTList;
import ast.ASTree;

import java.util.List;

public class NegativeExpr extends ASTList {

    public NegativeExpr(List<ASTree> children) {
        super(children);
    }

    public ASTree operand() {
        return child(0);
    }

    public String toString() {
        return "-" + operand();
    }

}
