package task7;

public class SNandReturnitsIP {


    public static void main(String[] args) {

        int[] arr = {10, 20, 30, 40, 50};

        int search = 30;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == search) {
                System.out.println("Number found at index: " + i);
            }
        }
    }
}