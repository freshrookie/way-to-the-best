package parser;

import ast.ASTList;
import ast.ASTree;

import java.util.List;

public class BlockStmnt extends ASTList {
    public BlockStmnt(List<ASTree> children) {
        super(children);
    }
}
