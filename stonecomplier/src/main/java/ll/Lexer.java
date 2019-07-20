package ll;

import com.sun.org.apache.xpath.internal.SourceTree;
import lexer.CodeDialog;

import java.io.IOException;
import java.io.Reader;

public class Lexer {
    private Reader reader;
    private static final int EMPTY = -1;
    private int nextChar = EMPTY;

    private int getChar() throws IOException {
        if (nextChar == EMPTY) {
            return reader.read();
        } else {
            int tmp = nextChar;
            nextChar = EMPTY;
            return tmp;
        }
    }

    public Lexer(Reader reader) {
        this.reader = reader;
    }

    public String read() throws IOException {
        int newchar;
        StringBuilder stringBuilder = new StringBuilder();
        do {
            newchar = getChar();

        } while (isSpace(newchar));

        if (newchar < 0) {
            return null;
        } else if (isDigit(newchar)) {
            do {
                stringBuilder.append((char)newchar);
                newchar = getChar();

            } while (isDigit(newchar));
            ungetChar(newchar);
            return stringBuilder.toString();
        } else if (isLetter(newchar)) {
            do {
                stringBuilder.append((char)newchar);
                newchar = getChar();
            } while (isLetter(newchar) || isDigit(newchar));
            ungetChar(newchar);
            return stringBuilder.toString();
        } else if (newchar == '=') {
            int c = getChar();

            if (c == '=') {
                return "==";
            } else {
                ungetChar(c);
                return "=";
            }
        } else {
            throw new IOException();
        }

    }

    private int ungetChar(int c) {
        return nextChar = c;
    }

    private static boolean isLetter(int c) {
        return (c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z');
    }

    private static boolean isDigit(int c) {
        return (c >= '0' && c <= '9');
    }

    private static boolean isSpace(int c) {
        return (c >= 0 && c <= ' ');
    }

    public static void main(String[] args) throws IOException {
        Lexer lexer = new Lexer(new CodeDialog());
        for (String s; (s = lexer.read()) != null; ) {
            System.out.println("=>" + s);
        }
    }
}
