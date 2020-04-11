import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Message {
    String content;
    String crc;
    int[] controlBits;

    List<Byte> message;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCrc() {
        return crc;
    }

    public void setCrc(String crc) {
        this.crc = crc;
    }

    public int[] getControlBits() {
        return controlBits;
    }

    public void setControlBits(int[] controlBits) {
        this.controlBits = controlBits;
    }

    public List<Byte> getMessage() {
        return message;
    }

    public void setMessage(List<Byte> message) {
        this.message = message;
    }

    public void generateMessage() {
        message = new ArrayList<>();

        for (char c : this.content.toCharArray()) {
            int bit = Character.getNumericValue(c);
            message.add((byte)bit);
        }

        for (char c : crc.toCharArray()) {
            int bit = Character.getNumericValue(c);
            message.add((byte)bit);
        }

        int posCP = 0;

        for (int bit : controlBits) {
            message.add((int) Math.pow(2, posCP) - 1, (byte)bit);
            posCP++;
        }

    }

    public List<Byte> getContentCrc(){

        //todo: after brakeBit tod, change to content + crc

        List<Byte> massageCopy = new ArrayList<>(message);

        int posCP = controlBits.length - 1;

        for (int i = 0; i < controlBits.length; i++) {
            massageCopy.remove((int) Math.pow(2, posCP) - 1);
            posCP--;
        }

        return massageCopy;
    }

    public List<Byte> brakeBit(int bit) {

        //todo: brake crc and content too
        byte oldBit = message.get(bit - 1);

        this.message.set(bit - 1, (byte)(oldBit ^ 1));

        return this.message;
    }

    public void repair(int pos) {
        brakeBit(pos);
    }

    @Override
    public String toString() {
        return "TestMessage{" +
                "content='" + content + '\'' +
                ", crc='" + crc + '\'' +
                ", controlBits=" + Arrays.toString(controlBits) +
                ", message=" + message +
                '}';
    }

    public void showColorContentCrc() {

        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\\u001b[33m";

        List<String> dataString = new ArrayList<>();


        for (char c : content.toCharArray()) {
            dataString.add(RESET + c);
        }

        for (char c : crc.toCharArray()) {
            dataString.add(GREEN + c + RESET);
        }

        System.out.println(dataString);
    }

    public void showColorContentCrcHamming() {

        String RED = "\u001B[31m";
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String YELLOW = "\\u001b[33m";

        List<String> dataString = new ArrayList<>();


        for (char c : content.toCharArray()) {
            dataString.add(RESET + c);
        }

        for (char c : crc.toCharArray()) {
            dataString.add(GREEN + c + RESET);
        }

        int posCP = 0;

        for (int bit : controlBits) {
            dataString.add((int) Math.pow(2, posCP) - 1, RED + bit + RESET);
            posCP++;
        }

        System.out.println(dataString);
    }

    public void showColorContentCrcHammingBrakeBit(int pos) {

        String RED = "\u001B[31m";
        String RESET = "\u001B[0m";
        String GREEN = "\u001B[32m";
        String BLUE = "\u001b[34m";

        List<String> dataString = new ArrayList<>();


        for (char c : content.toCharArray()) {
            if(dataString.size() - 1 == pos){

            }
            dataString.add(RESET + c);
        }

        for (char c : crc.toCharArray()) {
            dataString.add(GREEN + c + RESET);
        }

        int posCP = 0;

        for (int bit : controlBits) {
            dataString.add((int) Math.pow(2, posCP) - 1, RED + bit + RESET);
            posCP++;
        }

        dataString.set(pos - 1, BLUE + message.get(pos - 1) + RESET);

        System.out.println(dataString);
    }
}
