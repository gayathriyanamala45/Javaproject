package Task5;

import java.util.Scanner;

public class StudentGrade{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.print("Enter marks: ");
        int marks = sc.nextInt();

        if (marks > 90) {
            System.out.println("O Grade");
        } else if (marks > 75 && marks < 90) {
            System.out.println("A Grade");
        } else if (marks > 50 && marks < 75) {
            System.out.println("B Grade");
        } else if (marks > 35) {
            System.out.println("Pass");
        } else {
            System.out.println("Fail");
        }

        sc.close();
    }
}
