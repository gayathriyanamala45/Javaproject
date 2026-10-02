package task6;
import java.util.Scanner;

public class HotelRoom {






    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("1. AC Room");
        System.out.println("2. Non-AC Room");

        System.out.print("Enter room type: ");
        int roomType = sc.nextInt();

        switch (roomType) {

            case 1:
                System.out.println("1. Single Room");
                System.out.println("2. Double Room");

                System.out.print("Enter room choice: ");
                int acRoom = sc.nextInt();

                switch (acRoom) {

                    case 1:
                        System.out.println("AC Single Room booked");
                        break;

                    case 2:
                        System.out.println("AC Double Room booked");
                        break;

                    default:
                        System.out.println("Invalid room choice");
                }
                break;

            case 2:
                System.out.println("1. Single Room");
                System.out.println("2. Double Room");

                System.out.print("Enter room choice: ");
                int nonAcRoom = sc.nextInt();

                switch (nonAcRoom) {

                    case 1:
                        System.out.println("Non-AC Single Room booked");
                        break;

                    case 2:
                        System.out.println("Non-AC Double Room booked");
                        break;

                    default:
                        System.out.println("Invalid room choice");
                }
                break;

            default:
                System.out.println("Invalid room type");
        }}}
    

