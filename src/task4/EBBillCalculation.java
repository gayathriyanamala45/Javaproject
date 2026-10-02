package task4;

import java.util.Scanner;

public class EBBillCalculation {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		System.out.print("Enter Consumer Name:");
		String name=sc.nextLine();
		System.out.print("Enter Consumer ID:");
		int id=sc.nextInt();
		System.out.print("Enter Type of Building(Domestic/commercial);");
		String Type=sc.next();
		System.out.print("Enter Previous Reading");
		int previous=sc.nextInt();
		
		System.out.print("Enter Current Reading:");
		int current=sc.nextInt();
		int units=current- previous;
		double amount;
		if(Type.equalsIgnoreCase("Domestic")) {
			amount=units*2.0;
			
		}else if(Type.equalsIgnoreCase("Commercial")) {
			amount=units*5.0;
			
		}else {
			System.out.println("Invalid building type");
			sc.close();
			return;
		}
		System.out.println("\n-----EB BILL------");
		System.out.println("ConsuSystem Name:"+name);
		
		System.out.println("ConsuSystem ID:"+id);
		
		System.out.println("Building Type:"+Type);
		
		System.out.println("Previous Reding:"+previous);
		
		System.out.println("Current Reading:"+current);
		
		System.out.println("Units Consumed:"+units);
		System.out.println("Bill Amount:₹"+amount);
		
		
		

	}

}
