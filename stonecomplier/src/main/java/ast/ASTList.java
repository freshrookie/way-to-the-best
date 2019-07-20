package ast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ASTList extends ASTree  {
    protected List<ASTree> children;

    public ASTList(List<ASTree> children) {
        this.children = children;
    }

    public Iterator<ASTree> children() {
        return children.iterator();
    }

    public ASTree child(int i) {
        return children.get(i);
    }

    public Iterator<ASTree> iterator() {
        return children();
    }

    public int numChildren() {
        return children.size();
    }

    public String location() {
        for (ASTree child : children) {
            if (child.location() != null) {
                return child.location();
            }
        }
        return null;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("(");
        String sep = "";
        for (ASTree child : children) {
            stringBuilder.append(sep);
            sep = " ";
            stringBuilder.append(child.toString());
        }
        stringBuilder.append(")");
        return stringBuilder.toString();
    }
}
