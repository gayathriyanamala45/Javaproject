import java.util.Scanner;

public class StringDelimiter {

    public static void main(String[] args) {

        Scanner sc = new Scanner("apple/mango/orange");

        sc.useDelimiter("/");

        while (sc.hasNext()) {
            System.out.println(sc.next());
        }

        sc.close();
    }
}