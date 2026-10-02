package task6;

import java.util.Scanner;

public class ATMprogram {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        double balance = 10000;

        System.out.println("1. Check Balance");
        System.out.println("2. Deposit");
        System.out.println("3. Withdraw");
        System.out.println("4. Exit");

        System.out.print("Enter your choice: ");
        int choice = sc.nextInt();

        switch (choice) {

            case 1:
                System.out.println("Balance = " + balance);
                break;

            case 2:
                System.out.print("Enter deposit amount: ");
                double deposit = sc.nextDouble();

                balance = balance + deposit;

                System.out.println("Deposit successful");
                System.out.println("Balance = " + balance);
                break;

            case 3:
                System.out.print("Enter withdrawal amount: ");
                double withdraw = sc.nextDouble();

                if (withdraw > 0 && withdraw <= balance) {
                    balance = balance - withdraw;

                    System.out.println("Withdrawal successful");
                    System.out.println("Balance = " + balance);
                } else {
                    System.out.println("Insufficient balance or invalid amount");
                }
                break;

            case 4:
                System.out.println("Thank you");
                break;

            default:
                System.out.println("Invalid choice");
        }
    }
}
