package ast;

import java.util.Iterator;

public abstract class ASTree implements Iterable<ASTree>{

    public abstract Iterator<ASTree> children();

    public abstract ASTree child(int i);

    public Iterator<ASTree> iterator() {
        return children();
    }

    public abstract int numChildren();

    public abstract String location();


}
