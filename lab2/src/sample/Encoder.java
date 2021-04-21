package sample;

import java.io.*;

public class Encoder {
    public static void encode(InputStream in, OutputStream out, BitGenerator g) throws IOException {
        int key, bitIndex, bufIndex, bufLen;
        byte[] buf = new byte[2048];
        while ((bufLen = in.read(buf, 0, buf.length)) > 0) {
            for (bufIndex = 0; bufIndex < bufLen; bufIndex++) {
                key = 0;
                for (bitIndex = 0; bitIndex < 8; bitIndex++) {
                    key <<= 1;
                    key |= g.nextBit();
                }
                buf[bufIndex] ^= key;
            }
            out.write(buf, 0, bufLen);
        }
    }
    public static void fileEncode(File in, File encode, int bitKey) throws IOException {
        try (InputStream is = new FileInputStream(in);
             OutputStream os = new FileOutputStream(encode)) {
            encode(is, os, new BitGenerator(bitKey));
        }
    }

    public static void encode(InputStream in, OutputStream bitOut, OutputStream out, BitGenerator g) throws IOException {
        int key, bitIndex, bufIndex, bufLen;
        byte[] buf = new byte[2048];
        while ((bufLen = in.read(buf, 0, buf.length)) > 0) {
            for (bufIndex = 0; bufIndex < bufLen; bufIndex++) {
                key = 0;
                for (bitIndex = 0; bitIndex < 8; bitIndex++) {
                    key <<= 1;
                    key |= g.nextBit();
                }
                buf[bufIndex] ^= key;
                bitOut.write(key);
            }
            out.write(buf, 0, bufLen);
        }
    }

    public static void detailEncode(File in, File out, File inBits, File keyBits, File outBits, int key) throws IOException {
        try (InputStream is = new FileInputStream(in);
             OutputStream keyBos = new BitPrinter(new FileOutputStream(keyBits));
             OutputStream outBos = new DoubleOutputStream(
                     new BitPrinter(new FileOutputStream(outBits)),
                     new FileOutputStream(out))) {
            encode(is, keyBos, outBos, new BitGenerator(key));
        }
        try (InputStream is = new FileInputStream(in);
             OutputStream os = new BitPrinter(new FileOutputStream(inBits))) {
            int r;
            while ((r = is.read()) != -1)
                os.write(r);
        }
    }
}