package sample.logic;

public interface Cipher {
    String encode(String input, String key);
    String decode(String input, String key);
}
