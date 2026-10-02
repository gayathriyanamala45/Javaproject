package task9;

public class InheritanceEmployeeProgram {



    
    static class EmployeeDetails {
        int empid;
        String empname;
        double basicSalary;

        EmployeeDetails(int empid, String empname, double basicSalary) {
            this.empid = empid;
            this.empname = empname;
            this.basicSalary = basicSalary;
        }

        void display() {
            System.out.println("Employee ID: " + empid);
            System.out.println("Employee Name: " + empname);
            System.out.println("Basic Salary: " + basicSalary);
        }
    }

    static class Manager extends EmployeeDetails {

        Manager(int empid, String empname, double basicSalary) {
            super(empid, empname, basicSalary);
        }

        void calculateSalary() {
            double salary = basicSalary + (basicSalary * 0.20);
            System.out.println("Manager Salary: " + salary);
        }

        void display() {
            super.display();
            System.out.println("Designation: Manager");
            calculateSalary();
        }
    }

    static class Developer extends EmployeeDetails {

        Developer(int empid, String empname, double basicSalary) {
            super(empid, empname, basicSalary);
        }

        void calculateSalary() {
            double salary = basicSalary + (basicSalary * 0.15);
            System.out.println("Developer Salary: " + salary);
        }

        void display() {
            super.display();
            System.out.println("Designation: Developer");
            calculateSalary();
        }
    }

    static class Intern extends EmployeeDetails {

        Intern(int empid, String empname, double basicSalary) {
            super(empid, empname, basicSalary);
        }

        void calculateSalary() {
            double salary = basicSalary + (basicSalary * 0.10);
            System.out.println("Intern Salary: " + salary);
        }

        void display() {
            super.display();
            System.out.println("Designation: Intern");
            calculateSalary();
        }
    }

    public static void main(String[] args) {

        Manager m = new Manager(101, "Gayathri", 50000);
        Developer d = new Developer(102, "Githa", 40000);
        Intern i = new Intern(103, "Anitha", 20000);

        System.out.println("----- Manager -----");
        m.display();

        System.out.println("\n----- Developer -----");
        d.display();

        System.out.println("\n----- Intern -----");
        i.display();
    }
}