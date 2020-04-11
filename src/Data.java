import java.util.Arrays;

public class Data {

    private byte[] bytes;

    public byte[] getBytes() {
        return bytes;
    }

    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
    }

    public Data(byte[] bytes) {
        this.bytes = bytes;
    }

    public String getBinaryString(){

        String dataString = "";

        for(byte b : bytes){
            dataString += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0');
        }

        return dataString;
    }

    public String showString(){
        return new String(bytes);
    }

    public String showBinary(){

        String dataString = "";

        for(byte b : bytes){
            dataString += String.format("%8s", Integer.toBinaryString(b & 0xFF)).replace(' ', '0') + " ";
        }

        return dataString;
    }

    @Override
    public String toString() {
        return "Data{" +
                "data=" + Arrays.toString(bytes) +
                '}';
    }
}
