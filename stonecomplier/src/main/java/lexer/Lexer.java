package lexer;

import java.io.IOException;
import java.io.LineNumberReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexer {
    public static String pat1 = "[0-9]+";

    public static String pat2 = "\"(\\\\\"|\\\\\\\\|\\\\n|[^\"])*\"";

    public static String pat3 = "[A-Z_a-z][A-Z_a-z0-9]*|==|<=|>=|&&|\\|\\||\\p{Punct}";

    public static String reg = "\\s*((//.*)|(" + pat1 + ")|(" + pat2 + ")|(" + pat3 + "))?";

    public static final Pattern PATTERN = Pattern.compile(reg);

    private List<Token> queue = new ArrayList();

    private LineNumberReader reader;

    boolean hasMore;

    public Lexer(Reader reader) {
        hasMore = true;
        this.reader = new LineNumberReader(reader);
    }

    public Token read() throws ParseException {
        if (fillQueue(0)) {
            return queue.remove(0);
        }
        return Token.EOF;
    }

    public Token peek(int i) throws ParseException {
        if (fillQueue(i)) {
            return queue.get(i);
        }
        return Token.EOF;
    }

    protected boolean fillQueue(int i) throws ParseException {
        while (i >= queue.size()) {
            if (!hasMore) {
                return false;
            }
            readLine();
        }
        return true;
    }

    private void readLine() throws ParseException {
        String line = null;
        try {
            line = reader.readLine();
        } catch (IOException e) {
            throw new ParseException(e);
        }

        if (line == null) {
            hasMore = false;
            return;
        }
        Matcher matcher = PATTERN.matcher(line);
        matcher.useTransparentBounds(true).useAnchoringBounds(false);
        int lineNumber = reader.getLineNumber();
        int pos = 0;
        int end = line.length();
        while (pos < end) {
            matcher.region(pos, end);
            if (matcher.lookingAt()) {
                addToken(matcher, lineNumber);
                pos = matcher.end();
            } else {
                throw new ParseException("can't parse the line");
            }
        }
        queue.add(new IdToken(lineNumber, Token.EOL));

    }

    private void addToken(Matcher matcher, int lineNumber) {
        String m = matcher.group(1);
        if (m != null) {
            if (matcher.group(2) == null) {
                if (matcher.group(3) != null) {
                    queue.add(new NumToken(lineNumber, Integer.parseInt(m)));
                } else if (matcher.group(4) != null) {
                    queue.add(new StrToken(lineNumber, toStringLiteral(m)));
                } else {
                    queue.add(new IdToken(lineNumber, m));
                }
            }
        }
    }

    protected String toStringLiteral(String s) {
        StringBuilder stringBuilder = new StringBuilder();
        int length = s.length() - 1;
        for (int i = 1; i < length; i++) {
            char c = s.charAt(i);
            if (c == '\\' && i + 1 < length) {
                char c2 = s.charAt(i + 1);
                if (c2 == '\\' || c2 == '\"') {
                    c = s.charAt(++i);
                } else if (c2 == 'n') {
                    ++i;
                    c = '\n';
                }
            }
            stringBuilder.append(c);
        }
        return stringBuilder.toString();
    }

    protected static class NumToken extends Token {
        private int num;

        protected NumToken(int lineNumber, int num) {
            super(lineNumber);
            this.num = num;
        }

        @Override public boolean isNumber() {
            return true;
        }

        @Override public String getText() {
            return Integer.toString(num);
        }

        @Override public int getNumber() {
            return num;
        }
    }

    protected static class IdToken extends Token {

        private String id;

        protected IdToken(int lineNumber, String id) {
            super(lineNumber);
            this.id = id;
        }

        @Override public boolean isIdentifier() {
            return true;
        }

        @Override public String getText() {
            return id;
        }
    }

    protected static class StrToken extends Token {
        private String literal;

        protected StrToken(int lineNumber, String literal) {
            super(lineNumber);
            this.literal = literal;
        }

        @Override public boolean isString() {
            return true;
        }

        @Override public String getText() {
            return literal;
        }

    }

}
