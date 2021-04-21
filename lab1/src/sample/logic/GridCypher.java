package sample.logic;

public class GridCypher implements Cipher {
    private final String alphabet;

    public GridCypher(String s) {
        if (s == null) throw new NullPointerException();
        if (s.length() < 16) throw new IllegalArgumentException();
        this.alphabet = s;
    }

    public String encode(String in, String key) {
        int l = 16 - in.length();
        if (l > 0)
            in += alphabet.substring(0, l);

        char[] matrix = {
                in.charAt(0), in.charAt(12), in.charAt(8), in.charAt(4),
                in.charAt(5), in.charAt(9), in.charAt(13), in.charAt(1),
                in.charAt(10), in.charAt(6), in.charAt(2), in.charAt(14),
                in.charAt(15), in.charAt(3), in.charAt(7), in.charAt(11)
        };
        return new String(matrix);
    }
    public String decode(String in, String key) {
        return decode(in, in.length());
    }
    public String decode(String in, int len) {
        if (len > 16)
            len = 16;
        int l = 16 - in.length();
        if (l > 0)
            in += alphabet.substring(0, l);

        char[] matrix = {
                in.charAt(0), in.charAt(7), in.charAt(10), in.charAt(13),
                in.charAt(3), in.charAt(4), in.charAt(9), in.charAt(14),
                in.charAt(2), in.charAt(5), in.charAt(8), in.charAt(15),
                in.charAt(1), in.charAt(6), in.charAt(11), in.charAt(12)
        };

        return new String(matrix, 0, len);
    }
/*
    private String[][] matrixOf(String key) {
        return new String[][]{
                {"1", "14", "11", "5"},
                {"7", "12", "16", "2"},
                {"10", "8", "4", "15"},
                {"13", "3", "6", "9"}};
    }

    public String encode(String input, String key) {
        return encrypt(input, matrixOf(key));
    }
    public String decode(String input, String key) {
        return decrypt(input, matrixOf(key));
    }

    public String encrypt(String input, String[][] matrix) {
        int wordLength = input.length();
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int key = Integer.parseInt(String.valueOf(matrix[i][j])) - 1;
                if (key < input.length()) {
                    matrix[i][j] = String.valueOf(input.charAt(key));
                } else {
                    matrix[i][j] = String.valueOf(alphabet.charAt(key - wordLength));
                }
            }
        }

        StringBuilder result = new StringBuilder();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                result.append(matrix[i][j]);
            }
        }
        return result.substring(0, wordLength);
    }
    public String decrypt(String input, String[][] matrix) {
        String[][] temp = new String[4][4];
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                try {
                    temp[i][j] = String.valueOf(input.charAt(index++));
                } catch (StringIndexOutOfBoundsException e) {
                    throw new IllegalArgumentException();
                }
            }
        }

        StringBuilder stringBuilder = new StringBuilder(input);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix.length; j++) {
                int key = Integer.parseInt(String.valueOf(matrix[i][j]));
                if (key <= input.length()) {
                    stringBuilder.replace(key - 1, key, temp[i][j]);
                }
            }
        }
        return stringBuilder.substring(0, input.length());
    }*/
}