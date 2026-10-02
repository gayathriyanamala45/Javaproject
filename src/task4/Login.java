package task4;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username: ");
        String username = sc.nextLine();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (username.equals("admin") && password.equals("1234")) {
            System.out.println("Login successful");
        } else {
            System.out.println("Login failed");
        }

        sc.close();
    }
}


