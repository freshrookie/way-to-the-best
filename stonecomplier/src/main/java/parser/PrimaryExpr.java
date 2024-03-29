package parser;

import ast.ASTList;
import ast.ASTree;

import java.util.List;

public class PrimaryExpr extends ASTList {

    public PrimaryExpr(List<ASTree> children) {
        super(children);
    }

    public static ASTree create(List<ASTree> c) {
        return c.size() == 1 ? c.get(0) : new PrimaryExpr(c);
    }

}
