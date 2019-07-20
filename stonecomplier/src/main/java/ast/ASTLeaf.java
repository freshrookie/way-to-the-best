package ast;

import lexer.Token;

import java.util.ArrayList;
import java.util.Iterator;

public class ASTLeaf extends ASTree {
    protected Token token;

    private static ArrayList<ASTree> empty = new ArrayList<ASTree>();

    public ASTLeaf(Token token) {
        this.token = token;
    }

    public Iterator<ASTree> children() {
        return empty.iterator();
    }

    public ASTree child(int i) {
        throw new UnsupportedOperationException();
    }

    public int numChildren() {
        return 0;
    }

    public String location() {
        return "location is " + token.getLineNumber();
    }

    public Token token() {
        return token;
    }

    public String toString() {
        return token.getText();
    }
}
