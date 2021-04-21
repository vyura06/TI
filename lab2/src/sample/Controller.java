package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Controller {
    @FXML
    private TextField in, out, key, inBits, keyBits, outBits;
    @FXML
    private TextArea inBits1, keyBits1, outBits1;

    @FXML
    private Label label;

    private final FileChooser fileChooser = new FileChooser();

    private int getKey(TextField encodeKey) {
        final String s = filterKey(encodeKey.getText().trim());
        final int l = s.length();
        if (l == 32) {
            int bits = 0;
            for (int i = 0; i < l; i++) {
                bits <<= 1;
                if (s.charAt(i) == '1')
                    bits |= 1;
            }
            /*System.out.println(s);
            System.out.println(Integer.toBinaryString(result));*/
            return bits;
        }
        throw new IllegalArgumentException("Key length is not 32: " + s.length());
    }
    private String filterKey(String s) {
        StringBuilder sb = new StringBuilder(s.length());
        char tmp;
        for (int i = 0; i < s.length(); i++) {
            tmp = s.charAt(i);
            if (tmp == '0' || tmp == '1')
                sb.append(tmp);
        }
        return sb.toString();
    }

    private void setIn(TextField in) {
        File f = fileChooser.showOpenDialog(null);
        in.setText(f == null ? "" : f.getAbsolutePath());
    }
    private void setOut(TextField out) {
        File f = fileChooser.showSaveDialog(null);
        out.setText(f == null ? "" : f.getAbsolutePath());
    }

    private void err(Exception e) {
        label.setText((e == null || e.getMessage() == null) ? "Encode exception" :
                ("Encode exception: " + e.getMessage()));
    }

    @FXML
    private void selectEncodeIn() {
        setIn(in);
    }

    @FXML
    private void selectEncodeOut() {
        setOut(out);
    }

    @FXML
    private void selectEncodeInBits() {
        setOut(inBits);
    }

    @FXML
    private void selectEncodeKeyBits() {
        setOut(keyBits);
    }

    @FXML
    private void selectEncodeOutBits() {
        setOut(outBits);
    }

    @FXML
    private void encode() {
        try {
            int key = getKey(this.key);
            Encoder.fileEncode(
                    new File(in.getText().trim()),
                    new File(out.getText().trim()),
                    key);
            label.setText("Encoded");
        } catch (Exception e) {
            err(e);
        }
    }

    @FXML
    private void detailEncode() {
        try {
            int key = getKey(this.key);

            File inB = new File(inBits.getText().trim());
            File keyB = new File(keyBits.getText().trim());
            File outB = new File(outBits.getText().trim());
            Encoder.detailEncode(
                    new File(in.getText().trim()),
                    new File(out.getText().trim()),
                    inB, keyB, outB, key);
            inBits1.setText(readAll(inB));
            keyBits1.setText(readAll(keyB));
            outBits1.setText(readAll(outB));
            label.setText("Encoded");
        } catch (Exception e) {
            err(e);
        }
    }

    private String readAll(File in) throws IOException {
        try (FileReader reader = new FileReader(in)) {
            char[] buf = new char[2048];
            int l;
            StringBuilder sb = new StringBuilder();
            while ((l = reader.read(buf, 0, buf.length)) > 0)
                sb.append(buf, 0, l);
            return sb.toString();
        }
    }

    @FXML
    private void clearOut() {
        label.setText("");
    }
}