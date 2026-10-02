package task4;

import java.util.Scanner;

public class StudentMS {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner sc=new Scanner(System.in);
		
		System.out.print("Enter student name:");
		
		String name=sc.nextLine();
		
		System.out.print("Enter student ID:");
		
		int id=sc.nextInt();
		System.out.print("Enter mark 1:");
				int m1=sc.nextInt();
				
				System.out.print("Enter mark 2:");
				int m2=sc.nextInt();
				
				System.out.print("Enter mar 3:");
				int m3=sc.nextInt();
				
				int total=m1+m2+m3;
				double average=total/3.0;
				System.out.println("Student Name:"+name);
				System.out .println("Student ID:"+id);
				System.out.println("Total Marks:"+total);
				System.out.println("Average:"+average);
				sc.close();
				}

	}

