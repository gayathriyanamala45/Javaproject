package task8;
import java.util.Scanner;

public class Employee {

public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of employees: ");
        int employees = sc.nextInt();

        System.out.print("Enter number of months: ");
        int months = sc.nextInt();

        String[] names = new String[employees];
        double[][] salary = new double[employees][months];

        for (int i = 0; i < employees; i++) {
            System.out.print("Enter employee name: ");
            names[i] = sc.next();

            for (int j = 0; j < months; j++) {
                System.out.print("Enter salary for month " + (j + 1) + ": ");
                salary[i][j] = sc.nextDouble();
            }
        }

        double highestSalary = 0;
        String highestEmployee = "";

        for (int i = 0; i < employees; i++) {
            double total = 0;

            for (int j = 0; j < months; j++) {
                total = total + salary[i][j];
            }

            if (total > highestSalary) {
                highestSalary = total;
                highestEmployee = names[i];
            }
        }

        System.out.println("\nHighest Salary Employee: " + highestEmployee);
        System.out.println("Total Salary: " + highestSalary);

        sc.close();
    }
}