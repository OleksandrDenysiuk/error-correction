import java.util.List;

public class Converter {
    //convert some String word to binary String by 8 bit
    //in: word String
    //return: binary by 8 bit String
    public static String to8BytesString(String data){

        byte[] bytes = data.getBytes();

        String bytesString = "";

        for(byte b : bytes){
            bytesString += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        }

        return bytesString;
    }

    //convert some number Short to binary String by 16 bit
    public static String to16BytesString(short number){
        return String.format("%16s", Integer.toBinaryString(number & 0xFFFF)).replace(' ', '0');
    }

    //convert some number int to binary String by 16 bit
    public static String to16BytesString(int number){
        return String.format("%16s", Integer.toBinaryString(number & 0xFFFF)).replace(' ', '0');
    }

    //convert some number int to binary String by 16 bit
    public static String to32BytesString(int number){
        return String.format("%32s", Integer.toBinaryString(number)).replace(' ', '0');
    }

    public static byte[] toByteArray(List<Byte> list){
        Byte[] bytes = new Byte[list.size()];

        bytes = list.toArray(bytes);

        return toPrimitives(bytes);
    }


    public static byte[] toPrimitives(Byte[] oBytes)
    {
        byte[] bytes = new byte[oBytes.length];

        for(int i = 0; i < oBytes.length; i++) {
            bytes[i] = oBytes[i];
        }

        return bytes;
    }

    public static String toString(List<Byte> list){
        String str = "";

        for(byte b : list){
            str += b;
        }

        return str;
    }

}
