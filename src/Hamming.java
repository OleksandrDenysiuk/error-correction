public class Hamming {

    //рахує кількість контрольних бітів
    //приймає стрічку даних в бітовому вигляді
    //вертає ціле число, кількістю контрольних бітів
    private static int countAmountControlBits(String bytes) {

        int amount = 0;

        while (Math.pow(2, amount) <= bytes.length()) {
            amount++;
        }

        return amount;
    }


    //рахує код хемінга
    //приймає: таблицю бітів з позиціями контрольних бітів як 0, кількість контрольних бітів
    //повертає: таблицю з вирахуваними контрольними бітами
    public static int[] calculation(String bytes) {
        int[] ar =  setPositionControlPoints(bytes);
        int r = countAmountControlBits(bytes);

        int[] bits = new int[r];

        for (int i = 0; i < r; i++) {
            int x = (int) Math.pow(2, i);
            int amount = 0;
            for (int j = x - 1; j < ar.length; j += (x + x)) {
                for (int k = j; k < j + x; k++) {
                    if (k > ar.length - 1) {
                        break;
                    }
                    if (ar[k] == 1) {
                        amount++;
                    }
                }
            }
            if (amount % 2 != 0) {
                bits[i] = 1;
                ar[x - 1] = 1;
            }
        }

        return bits;
    }

    //розширяє стрічку бітових даних позиціями контрольних бітів
    //приймає: Bytes in String
    //повертає: таблицю бітів з озставленими позиціями контрольних бітів нулями 0
    private static int[] setPositionControlPoints(String bytes) {

        int amount =  countAmountControlBits(bytes);

        int[] hamming = new int[bytes.length() + amount];

        int posCP = 0;

        int j = 0;

        for (int i = 0; i < hamming.length; i++) {
            if (Math.pow(2, posCP) - 1 == i) {
                posCP++;
            } else {
                hamming[i] = bytes.charAt(j) - '0';
                j++;
            }
        }

        return hamming;
    }

    public static int verification(int[] array1, int[] array2){

        int pos = 0;

        if(array1.length != array2.length){
            return -1;
        }

        for (int i = 0; i < array1.length; i++){
            if(array1[i] != array2[i]){
                pos += Math.pow(2, i);
            }
        }

        if(pos != 0){
            return pos;
        }

        return 0;
    }
}
