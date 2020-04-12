public class Crc16 extends Crc{

    private int[] crcTable16;

    public Crc16() {
        this.crcTable16 = calculateCrcTable();
    }


    protected int[] calculateCrcTable() {
        short generator = 0x1021;
        int[] crcTable16 = new int[256];

        for (short divident = 0; divident < 256; divident++) /* iterate over all possible input byte values 0 - 255 */
        {
            short curByte = (short)(divident << 8); /* move divident byte into MSB of 16Bit CRC */
            for (byte bit = 0; bit < 8; bit++)
            {
                if ((curByte & 0x8000) != 0)
                {
                    curByte <<= 1;
                    curByte ^= generator;
                }
                else
                {
                    curByte <<= 1;
                }
            }

            crcTable16[divident] = curByte;
        }

        return crcTable16;
    }

    public int compute(byte[] bytes) {
        short crc = 0;
        for (byte b : bytes)
        {
            /* XOR-in next input byte into MSB of crc, that's our new intermediate divident */
            byte pos = (byte)((crc >> 8) ^ b); /* equal: ((crc ^ (b << 8)) >> 8) */
            /* Shift out the MSB used for division per lookuptable and XOR with the remainder */
            crc = (short) ((crc << 8) ^ (int)(crcTable16[pos & 0xff]));
        }
        return crc;
    }

    public int verification(byte[] bytes){
        return compute(bytes);
    }
}
