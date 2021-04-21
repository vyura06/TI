package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import sample.logic.*;

import java.io.*;

public class MainWindow {
    @FXML
    private TextArea encodeTF;
    @FXML
    private TextField encodeKey;
    @FXML
    private ChoiceBox<Cipher> encodeCB;

    @FXML
    private TextArea decodeTF;
    @FXML
    private TextField decodeKey;
    @FXML
    private ChoiceBox<Cipher> decodeCB;
    @FXML
    private TextArea result;

    @FXML
    private Label out;

    public static final String RU = "абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static final String RU_UP = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    public static final String EN = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static String filter(String s, String alphabet) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (alphabet.indexOf(c) >= 0)
                sb.append(c);
        }
        return sb.toString();
    }

    private final ObservableList<Cipher> ciphers = FXCollections.observableArrayList(
            new Cipher() {
                private final Cipher c = new RailwayFenceCypher();

                public String encode(String input, String key) {
                    return c.encode(filter(input, RU), keyFilter(key));
                }
                public String decode(String input, String key) {
                    return c.decode(filter(input, RU), keyFilter(key));
                }

                private String keyFilter(String s) {
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < s.length(); i++) {
                        char c = s.charAt(i);
                        if (Character.isLetterOrDigit(c))
                            sb.append(c);
                    }
                    return sb.toString();
                }
            },
            new Cipher() {
                private final GridCypher c = new GridCypher(EN);
                int len = 0;

                public String encode(String input, String key) {
                    input = filter(input, EN);
                    len = input.length();
                    return len == 0 ? "" : c.encode(input, key);
                }
                public String decode(String input, String key) {
                    return c.decode(filter(input, EN), len);
                }
            },
            new Cipher() {
                private final Cipher c = new VigenerCypher(RU_UP);

                public String encode(String input, String key) {
                    return c.encode(filter(input.toUpperCase(), RU_UP), filter(key.toUpperCase(), RU_UP));
                }
                public String decode(String input, String key) {
                    return c.decode(filter(input.toUpperCase(), RU_UP), filter(key.toUpperCase(), RU_UP));
                }
            }
    );
    private final String[] values = {
            "Railway Fence Cypher",
            "Grid Cypher",
            "Vigener Cypher"
    };

    private final FileChooser chooser = new FileChooser();

    @FXML
    private void initialize() {
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt"));
        StringConverter<Cipher> converter = new StringConverter<>() {
            public String toString(Cipher cipher) {
                for (int i = 0; i < ciphers.size(); i++)
                    if (cipher == ciphers.get(i))
                        return values[i];
                return String.valueOf(cipher);
            }

            public Cipher fromString(String s) {
                if (s != null)
                    for (int i = 0; i < ciphers.size(); i++)
                        if (s.equals(values[i]))
                            return ciphers.get(i);
                return null;
            }
        };

        encodeCB.setConverter(converter);
        encodeCB.setItems(ciphers);
        encodeCB.setValue(ciphers.get(0));

        decodeCB.setConverter(converter);
        decodeCB.setItems(ciphers);
        decodeCB.setValue(ciphers.get(0));
    }

    private void loadTF(TextArea area) {
        File in = chooser.showOpenDialog(null);
        if (in != null) {
            try {
                area.setText(Files.readAll(in));
            } catch (Exception e) {
            }
        }
    }
    private void saveTF(TextArea area) {
        File out = chooser.showSaveDialog(null);
        if (out != null) {
            try {
                Files.writeAll(out, area.toString());
            } catch (Exception e) {
            }
        }
    }

    //Console encode
    @FXML
    private void loadToEncodeTF() {
        loadTF(encodeTF);
    }
    @FXML
    private void encode() {
        Cipher cipher = encodeCB.getSelectionModel().getSelectedItem();
        try {
            String encode = cipher.encode(encodeTF.getText(), encodeKey.getText());
            decodeTF.setText(encode);
        } catch (Exception e) {
            out.setText(e.getMessage() == null ? "Encode exception" : e.getMessage());
        }
    }

    //Console decode
    @FXML
    private void loadToDecodeTF() {
        loadTF(decodeTF);
    }
    @FXML
    private void decode() {
        Cipher cipher = decodeCB.getSelectionModel().getSelectedItem();
        try {
            String decode = cipher.decode(decodeTF.getText(), decodeKey.getText());
            result.setText(decode);
        } catch (Exception e) {
            out.setText(e.getMessage() == null ? "Decode exception" : e.getMessage());
        }
    }

    @FXML
    private void saveResult() {
        saveTF(result);
    }

    @FXML
    private void clearOut() {
        out.setText("");
    }
}