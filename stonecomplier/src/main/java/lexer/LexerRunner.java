package lexer;

public class LexerRunner {
    public static void main(String[] args) throws ParseException {

        Lexer lexer = new Lexer(new CodeDialog());
        Token read ;
        while ((read= lexer.read()) != Token.EOF) {
            System.out.println("=>" + read.getText());
        }
    }
}
