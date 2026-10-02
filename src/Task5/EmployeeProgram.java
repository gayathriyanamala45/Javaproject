package Task5;

import java.util.Scanner;

public class EmployeeProgram {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter age: ");
        int age = sc.nextInt();

        sc.nextLine();

        System.out.print("Enter department: ");
        String department = sc.nextLine();

        System.out.print("Enter designation: ");
        String designation = sc.nextLine();

        if (age >= 30 && department.equalsIgnoreCase("IT")) {
            System.out.println("Increment is applicable");
        } else {
            System.out.println("Increment is not applicable");
        }

        if (designation.equalsIgnoreCase("Manager")
                && department.equalsIgnoreCase("IT")) {
            System.out.println("Bonus is applicable");
        } else {
            System.out.println("Bonus is not applicable");
        }

        sc.close();
    }
}
