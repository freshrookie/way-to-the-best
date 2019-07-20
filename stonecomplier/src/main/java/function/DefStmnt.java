package function;

import ast.ASTLeaf;
import ast.ASTList;
import ast.ASTree;
import parser.BlockStmnt;

import java.util.List;

public class DefStmnt extends ASTList {

    public DefStmnt(List<ASTree> children) {
        super(children);
    }

    public String name() {
        return ((ASTLeaf)child(0)).token().getText();

    }

    public ParameterList parameters() {
        return (ParameterList)child(1);
    }

    public BlockStmnt body() {
        return (BlockStmnt)child(2);
    }

    public String toString() {
        return "(def" + name() + " " + parameters() + " " + body() + ")";
    }
}
