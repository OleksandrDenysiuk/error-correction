
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        //create new message
        Message newMessage = new Message();

        //user enter word
        Scanner in = new Scanner(System.in);
        String word = in.nextLine();

        //change string word to binary String
        String wordInBinary = Converter.to8BytesString(word);

        //SET variables Message

        //count CRC
        Crc16 crc16 = new Crc16();

        String crcInBinaryString = Converter.to16BytesString(crc16.compute_CRC16(word.getBytes()));

        newMessage.setContent(wordInBinary);

        newMessage.setCrc(crcInBinaryString);

        newMessage.showColorContentCrc();

        newMessage.setControlBits(Hamming.calculation(wordInBinary + crcInBinaryString));

        //generate message
        newMessage.generateMessage();
        newMessage.showColorContentCrcHamming();

        //brake bit
        System.out.println("Enter position(count from 1) of bit you want to brake:");
        int brakeBitPosition = Integer.valueOf(in.nextLine());

        newMessage.brakeBit(brakeBitPosition);
        newMessage.showColorContentCrcHammingBrakeBit(brakeBitPosition);

        System.out.println("Hamming check...");

        int[] oldControlBits = newMessage.getControlBits();
        newMessage.setControlBits(Hamming.calculation(Converter.toString(newMessage.getContentCrc())));

        int result = Hamming.verification(oldControlBits, newMessage.getControlBits());

        if(result == 0){
            System.out.println("Everything`s okay!");
        }else if(result > 1){
            System.out.println("Something is wrong on position: " + result);
            newMessage.showColorContentCrcHammingBrakeBit(result);
            System.out.println("Repairing it... ");
            newMessage.repair(result);
            System.out.println("Result after repairing");
            newMessage.showColorContentCrcHammingBrakeBit(result);
        }else{
            System.out.println("Something is wrong with arrays");
        }

        System.out.println("CRC check...");
        int resultCrc = crc16.verification(Converter.toByteArray(newMessage.getContentCrc()));

        if(resultCrc == 0){
            System.out.println("Everything okay!");
        }else {
            System.out.println("Something wrong!" + Integer.toBinaryString(result));
        }
    }

}
