package sample;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

public class BitPrinter extends OutputStream {
    private final OutputStream out;

    public BitPrinter(OutputStream out) {
        this.out = Objects.requireNonNull(out);
    }

    public void write(int byteBits) throws IOException {
        int mask = 1 << 7;
        for (int i = 0; i < 8; i++) {
            out.write(((byteBits & mask) == 0 ? '0' : '1'));
            mask >>= 1;
        }
    }
    public void flush() throws IOException {
        out.flush();
    }
    public void close() throws IOException {
        out.close();
    }
}
