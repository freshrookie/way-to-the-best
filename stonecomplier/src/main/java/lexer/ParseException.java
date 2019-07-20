package lexer;

public class ParseException extends Exception {
    public ParseException(Exception e) {
        super(e);
    }

    public ParseException(String msg) {
        super(msg);
    }

    public ParseException(Token token) {
        this("", token);
    }
    public ParseException(String msg, Token token) {
        super("syntax error around " + location(token) + "." + msg);
    }

    private static String location(Token token) {
        if (token == Token.EOF) {
            return "the last line.";
        } else {
            return "\"" + token.getText() + "\" at line " + token.getLineNumber();
        }
    }
}
