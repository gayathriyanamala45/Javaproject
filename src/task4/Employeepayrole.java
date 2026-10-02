package task4;

import java.util.Scanner;

public class Employeepayrole {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter employee name:");
		
		String empName=sc.nextLine();
		System.out.print("Enter employee ID:");
		int empId=sc.nextInt();
		System.out.print("Enter department:");
		sc.nextLine();
		String department=sc.nextLine();
		System.out.print("Enter basic salary:");
		double bp=sc.nextDouble();
		
		System.out.print("Enter DA:");
		double da=sc.nextDouble();
		System.out.print("Enter HRA");
		double hra=sc.nextDouble();
		
		double gp=bp+da+hra;
		System.out.print("Enter PF:");
		double pf=sc.nextDouble();
		double np=gp-pf;
		System.out.println("Employee Name:"+empName);
		System.out.println("Employee ID:"+empId);
		System.out.println("Department:"+department);
		System.out.println("Gross Pay:"+gp);
		System.out.println("Net Pay:"+np);
		sc.close();
		

	}

}
