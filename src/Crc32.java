public class Crc32 extends Crc{

    private int[] crcTable32;

    public Crc32() {
        this.crcTable32 = calculateCrcTable();
    }

    @Override
    protected int[] calculateCrcTable()
    {
        int polynomial = 0x814141AB;
        int[] crcTable32 = new int[256];

        for (int divident = 0; divident < 256; divident++) /* iterate over all possible input byte values 0 - 255 */
        {
            int curByte = (int)(divident << 24); /* move divident byte into MSB of 32Bit CRC */
            for (byte bit = 0; bit < 8; bit++)
            {
                if ((curByte & 0x80000000) != 0)
                {
                    curByte <<= 1;
                    curByte ^= polynomial;
                }
                else
                {
                    curByte <<= 1;
                }
            }

            crcTable32[divident] = curByte;
        }

        return crcTable32;
    }

    @Override
    public int compute(byte[] bytes)
    {
        int crc = 0;
        for(byte b : bytes)
        {
            /* XOR-in next input byte into MSB of crc and get this MSB, that's our new intermediate divident */
            byte pos = (byte)((crc ^ (b << 24)) >> 24);
            /* Shift out the MSB used for division per lookuptable and XOR with the remainder */
            crc = ((crc << 8) ^ (crcTable32[pos & 0xff]));
        }

        return crc;
    }

    public int verification(byte[] bytes){
        return compute(bytes);
    }

}
