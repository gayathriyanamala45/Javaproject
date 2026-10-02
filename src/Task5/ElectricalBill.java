package Task5;
import java.util.Scanner;

public class ElectricalBill {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter type (house/shop): ");
        String type = sc.nextLine();

        System.out.print("Enter units: ");
        int units = sc.nextInt();

        double amount;

        if (type.equalsIgnoreCase("house")) {

            if (units <= 100) {
                amount = units * 2;
                System.out.println("Discount applicable");
            } else if (units <= 200) {
                amount = units * 3;
            } else {
                amount = units * 5;
            }

        } else {
            // Shop - no discount
            amount = units * 6;
        }

        System.out.println("Electricity Bill = " + amount);

        sc.close();
    }
}

