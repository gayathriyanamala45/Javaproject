package task12;

        import java.util.ArrayList;
        import java.util.Collections;

    public class MaximumNumberInAL {

    public static void main(String[] args) {

        ArrayList<Integer> numbers = new ArrayList<>();

        numbers.add(10);
        numbers.add(25);
        numbers.add(5);
        numbers.add(40);
        numbers.add(15);

        System.out.println("Original ArrayList: " + numbers);

        int maximum = Collections.max(numbers);
        System.out.println("Maximum number: " + maximum);

        Collections.reverse(numbers);
        System.out.println("Reversed ArrayList: " + numbers);
    }
}