package lexer;

public abstract class Token {

    public static final Token EOF = new Token(-1){};
    public static final String EOL = "\\n";
    private int lineNumber;



    protected Token(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public boolean isIdentifier() {
        return false;
    }

    public boolean isString() {
        return false;
    }

    public boolean isNumber() {
        return false;
    }

    public String getText() {
        return "";
    }

    public int getNumber() {
        throw new StoneException("not number token");
    }
}
