package task4;

import java.util.Scanner;

public class LoanEligibility {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter employment status (employed/unemployed): ");
        String status = sc.nextLine();

        System.out.print("Enter monthly salary: ");
        double salary = sc.nextDouble();

        System.out.print("Enter civil score: ");
        int civilScore = sc.nextInt();

        if (status.equalsIgnoreCase("employed")) {

            if (salary >= 40000) {

                if (civilScore >= 700) {
                    System.out.println("Person is eligible for loan");
                } else {
                    System.out.println("Person is not eligible for loan");
                }

            } else {
                System.out.println("Person is not eligible for loan");
            }

        } else {
            System.out.println("Person is not eligible for loan");
        }

        sc.close();
    }
}
