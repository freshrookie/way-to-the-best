package function;

import ast.ASTList;
import ast.ASTree;
import parser.BlockStmnt;

import java.util.List;

public class Fun extends ASTList {
    public Fun(List<ASTree> children) {
        super(children);
    }

    public ParameterList parameters() {
        return (ParameterList)child(0);
    }

    public BlockStmnt body() {
        return (BlockStmnt)child(1);
    }

    public String toString() {
        return "(fun" + parameters() + " " + body() + ")";
    }

}
