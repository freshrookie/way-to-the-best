package lexer;

import com.sun.glass.ui.Size;
import javafx.scene.layout.Pane;

import javax.swing.*;
import java.io.*;

public class CodeDialog extends Reader {
    private String buffer;
    private int pos;

    public int read(char[] cbuf, int off, int len) throws IOException {
        if (buffer == null) {
            String s = showDialog();
            if (s == null) {
                return -1;
            }
            print(s);
            buffer = s + "\n";
            pos = 0;
        }

        int size = 0;
        int length = buffer.length();
        while (size < len && pos < length) {
            cbuf[off + size++] = buffer.charAt(pos++);
        }
        if (pos == length) {
            buffer = null;
        }
        return size;

    }

    public void close() throws IOException {
    }

    protected void print(String s) {
        System.out.println(s);
    }

    protected String showDialog() {
        JTextArea jTextArea = new JTextArea(20, 40);
        JScrollPane jScrollPane = new JScrollPane(jTextArea);
        int input = JOptionPane
            .showOptionDialog(null, jScrollPane, "Input", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null,
                null, null);
        if (input == JOptionPane.OK_OPTION) {
            return jTextArea.getText();
        }
        return null;

    }

    public static Reader file() throws FileNotFoundException {
        JFileChooser jFileChooser = new JFileChooser();
        if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return new BufferedReader(new FileReader(jFileChooser.getSelectedFile()));
        }
        throw new FileNotFoundException("no file specified");
    }

}
