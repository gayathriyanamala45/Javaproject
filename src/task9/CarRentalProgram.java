package task9;

public class CarRentalProgram {


    void rentCar(int hours) {
        double amount = hours * 200;
        System.out.println("Rental for " + hours + " hours: " + amount);
    }

    void rentCar() {
        double amount = 1500;
        System.out.println("Rental for 1 day: " + amount);
    }

    void rentCar(int days, boolean driver) {
        double amount;

        if (driver) {
            amount = days * 2000;
            System.out.println("Rental for " + days +
                    " days with driver: " + amount);
        } else {
            amount = days * 1500;
            System.out.println("Rental for " + days +
                    " days without driver: " + amount);
        }
    }

    public static void main(String[] args) {

        CarRentalProgram c = new CarRentalProgram();

        c.rentCar(5);          // 5 hours
        c.rentCar();           // 1 day
        c.rentCar(3, true);    // 3 days with driver
        c.rentCar(3, false);   // 3 days without driver
    }
}