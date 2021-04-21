package sample;

//x^33 + x^13 + 1
public class BitGenerator {
    private long bits;

    public BitGenerator(int bits) {
        this.bits = bits;
    }

    // | >>> 32
    //11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11
    //                                                 |
    //00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 11
    //                               | >>> 12
    //11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11
    //                                                 |
    //00 00 00 00 00 00 11 11 11 11 11 11 11 11 11 11 11

    //00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 11
    // xor
    //00 00 00 00 00 00 11 11 11 11 11 11 11 11 11 11 11
    //00 00 00 00 00 00 11 11 11 11 11 11 11 11 11 11 00
    // result in 1 bit
    //00 00 00 00 00 00 11 11 11 11 11 11 11 11 11 11 00
    // & 1                                             |
    //00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00

    // << 1
    //1 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 11 0

    public int nextBit() {
        final long firstBit = bits >>> 32;
        long newBit = (firstBit ^ (bits >>> 12)) & 1;
        bits = (bits << 1) | newBit;
        return (int) (firstBit & 1);
    }
    public long getBits() {
        return bits;
    }
    public String bitsToStr() {
        String str = Long.toBinaryString(getBits());
        int l = str.length();
        return l > 33 ? str.substring(l - 33) :
                ("0".repeat(33 - l) + str);
    }

    public static void main(String[] args) {
        BitGenerator g = new BitGenerator(-1);
        System.out.println(g.bitsToStr());
        for (int i = 0; i < 53; i++) {
            g.nextBit();
            System.out.println(g.bitsToStr());

        }
    }
}