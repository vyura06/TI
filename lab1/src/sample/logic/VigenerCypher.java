package sample.logic;

public class VigenerCypher implements Cipher {
    private final String alphabet;

    public VigenerCypher(String alphabet) {
        this.alphabet = alphabet;
    }

    protected int indexOf(char c) {
        for (int i = 0, l = alphabet.length(); i < l; i++)
            if (c == alphabet.charAt(i))
                return i;
        return -1;
    }

    private char encodeChar(char in, char key) {
        return encodeChar(in, key, 0);
    }
    private char decodeChar(char in, char key) {
        return decodeChar(in, key, 0);
    }

    private char encodeChar(char in, char key, int off) {
        int i = indexOf(in);
        if (i < 0)
            throw new IllegalArgumentException("Illegal in character: " + in);
        int k = indexOf(key);
        if (k < 0)
            throw new IllegalArgumentException("Illegal key character: " + key);

        return alphabet.charAt((i + k + off) % alphabet.length());
    }
    private char decodeChar(char in, char key, int off) {
        int i = indexOf(in);
        if (i < 0)
            throw new IllegalArgumentException("Illegal in character: " + in);
        int k = indexOf(key);
        if (k < 0)
            throw new IllegalArgumentException("Illegal key character: " + key);

        int sum = i - k - off;
        while (sum < 0)
            sum += alphabet.length();
        return alphabet.charAt(sum % alphabet.length());
    }

    public String encode(String input, String key) {
        if (input == null || key == null)
            throw new NullPointerException();

        int keyLen = key.length();
        if (keyLen == 0)
            throw new IllegalArgumentException("Error: Enter letters \nin Russian into the key");
        int inLen = input.length();
        int i = 0;

        StringBuilder sb = new StringBuilder(inLen);
        if (inLen < keyLen) {
            while (i < inLen) {
                sb.append(encodeChar(input.charAt(i), key.charAt(i)));
                i++;
            }
        } else {
            while (i < keyLen) {
                sb.append(encodeChar(input.charAt(i), key.charAt(i)));
                i++;
            }
            label:
            for (int shift = 1; ; shift++) {
                for (int k = 0; k < keyLen; k++) {
                    if (i >= inLen)
                        break label;
                    sb.append(encodeChar(input.charAt(i), key.charAt(k), shift));
                    i++;
                }
            }
        }
        return sb.toString();
    }
    public String decode(String input, String key) {
        if (input == null || key == null)
            throw new NullPointerException();
        int keyLen = key.length();
        if (keyLen == 0)
            throw new IllegalArgumentException("Error: Enter letters \nin Russian into the key");
        int inLen = input.length();
        int i = 0;
        StringBuilder sb = new StringBuilder(inLen);

        if (inLen < keyLen) {
            while (i < inLen) {
                sb.append(decodeChar(input.charAt(i), key.charAt(i)));
                i++;
            }
        } else {
            while (i < keyLen) {
                sb.append(decodeChar(input.charAt(i), key.charAt(i)));
                i++;
            }
            label:
            for (int shift = 1; ; shift++) {
                for (int k = 0; k < keyLen; k++) {
                    if (i >= inLen)
                        break label;
                    sb.append(decodeChar(input.charAt(i), key.charAt(k), shift));
                    i++;
                }
            }
        }
        return sb.toString();
    }

    public String printTable() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0, l = alphabet.length(), max = l - 1; ; i++) {
            sb.append(alphabet, i, l);
            sb.append(alphabet, 0, i);
            if (i == max)
                break;
            sb.append('\n');
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        String s = "abcdefghijklmnopqrstuvwxyz";

        VigenerCypher cipher = new VigenerCypher(s);
        String input = "ZWish you were here".toLowerCase();
        String key = "SIAMESE".toLowerCase();

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < input.length(); i++)
            if (s.indexOf(input.charAt(i)) >= 0)
                sb.append(input.charAt(i));
        input = sb.toString();

        System.out.println(cipher.printTable());
        System.out.println(input);
        String encode = cipher.encode(input, key);
        System.out.println(encode);
        System.out.println(cipher.decode(encode, key));
    }
}
