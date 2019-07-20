package parser;

import ast.ASTList;
import ast.ASTree;

import java.util.List;

public class NullStmnt extends ASTList {
    public NullStmnt(List<ASTree> children) {
        super(children);
    }
}
