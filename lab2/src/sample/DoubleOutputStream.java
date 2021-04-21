package sample;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class DoubleOutputStream extends OutputStream {
    private final OutputStream out1, out2;

    public DoubleOutputStream(OutputStream out1, OutputStream out2) {
        this.out1 = Objects.requireNonNull(out1);
        this.out2 = Objects.requireNonNull(out2);
    }

    public void write(int b) throws IOException {
        out1.write(b);
        out2.write(b);
    }
    public void write(byte[] b) throws IOException {
        out1.write(b);
        out2.write(b);
    }
    public void write(byte[] b, int off, int len) throws IOException {
        out1.write(b, off, len);
        out2.write(b, off, len);
    }
    public void flush() throws IOException {
        out1.flush();
        out2.flush();
    }
    public void close() throws IOException {
        out1.close();
        out2.close();
    }
}
