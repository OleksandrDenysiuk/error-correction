
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {

        //create new message
        Message newMessage = new Message();

        //user enter word
        Scanner scan = new Scanner(System.in);
        String word = scan.nextLine();

        //change string word to binary String
        String wordInBinary = Converter.to8BytesString(word);

        //SET variables Message
        //count CRC
        Crc crc;
        String crcInBinaryString;

        System.out.println("Please choose crc. ");
        System.out.println("For crc 16 press - 1");
        System.out.println("For crc 32 press - 2");
        int crcType = Integer.valueOf(scan.nextLine());

        switch (crcType){
            case 1: {
                System.out.println("You choose crc 16");
                crc = new Crc16();
                crcInBinaryString = Converter.to16BytesString(crc.compute(word.getBytes()));
                break;
            }

            case 2: {
                System.out.println("You choose crc 32");
                crc = new Crc32();
                crcInBinaryString = Converter.to32BytesString(crc.compute(word.getBytes()));
                break;
            }

            default: {
                System.out.println("Default crc 32");
                crc = new Crc32();
                crcInBinaryString = Converter.to16BytesString(crc.compute(word.getBytes()));
                break;
            }
        }

        newMessage.setContent(wordInBinary);

        newMessage.setCrc(crcInBinaryString);

        newMessage.showColorContentCrc();

        newMessage.setControlBits(Hamming.calculation(wordInBinary + crcInBinaryString));

        //generate message
        newMessage.generateMessage();
        newMessage.showColorContentCrcHamming();

        //brake bits
        System.out.println("Enter number of bits you want to brake");
        int number = scan.nextInt();

        for(int i = 0; i < number; i++){
            System.out.println("Enter position(count from 1) of bit you want to brake:");
            int brakeBitPosition = scan.nextInt();

            newMessage.brakeBit(brakeBitPosition);
            newMessage.showColorContentCrcHammingBrakeBit(brakeBitPosition);
        }

        System.out.println("Hamming check...");

        int result = Hamming.verification(newMessage.getMessage());

        if(result == 0){
            System.out.println("Everything`s okay!");
        }else if(result == 1){
            int position = Hamming.calculatePositionBrokenBit(newMessage.getMessage());
            System.out.println("Something is wrong on position: " + position);
            newMessage.showColorContentCrcHammingBrakeBit(position);
            System.out.println("Repairing it... ");
            newMessage.repair(position);
            System.out.println("Result after repairing");
            newMessage.showColorContentCrcHammingBrakeBit(position);
        }else{
            System.out.println("Fatal error");
        }

        System.out.println("CRC check...");
        int resultCrc = crc.verification(Converter.toByteArray(newMessage.getContentCrc()));

        if(resultCrc == 0){
            System.out.println("Everything okay!");
        }else {
            System.out.println("Something wrong!" + Integer.toBinaryString(result));
        }
    }
}
