public abstract class  Crc {

    abstract int[] calculateCrcTable();

    abstract int compute(byte[] bytes);

    abstract int verification(byte[] bytes);
}
