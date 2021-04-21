package sample.logic;

public class RailwayFenceCypher implements Cipher {
    public String encode(String input, String key) {
        try {
            return encode(input, Integer.parseInt(key));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: \n" +
                    "Enter a number \nin the key");
        }
    }
    public String decode(String input, String key) {
        try {
            return decode(input, Integer.parseInt(key));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Error: \n" +
                    "Enter a number \nin the key ");
        }
    }

    public String encode(String data, int numRails) {
        if (numRails >= data.length())
            throw new IllegalArgumentException("Error: \n" +
                    "The key is too big. \nRepeat.");
        char[] encrypted = new char[data.length()];

        int n = 0;
        for (int k = 0; k < numRails; k++) {
            int index = k;
            boolean down = true;
            while (index < data.length()) {
                encrypted[n++] = data.charAt(index);
                if (k == 0 || k == numRails - 1) {
                    index = index + 2 * (numRails - 1);
                } else if (down) {
                    index = index + 2 * (numRails - k - 1);
                    down = false;
                } else {
                    index = index + 2 * k;
                    down = true;
                }
            }
        }
        return new String(encrypted);
    }
    public String decode(String data, int numRails) {
        if (numRails > data.length())
            throw new IllegalArgumentException("Error: \n" +
                    "The key is too big. \nRepeat.");
        char[] decrypted = new char[data.length()];
        int n = 0;
        for (int k = 0; k < numRails; k++) {
            int index = k;
            boolean down = true;
            while (index < data.length()) {
                decrypted[index] = data.charAt(n++);
                if (k == 0 || k == numRails - 1) {
                    index = index + 2 * (numRails - 1);
                } else if (down) {
                    index = index + 2 * (numRails - k - 1);
                    down = false;
                } else {
                    index = index + 2 * k;
                    down = true;
                }
            }
        }
        return new String(decrypted);
    }
}