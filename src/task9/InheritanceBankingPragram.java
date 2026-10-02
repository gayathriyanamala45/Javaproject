package task9;

import java.util.Scanner;

public class InheritanceBankingPragram {
class Bank{
	double balance;
	String accountHolderName;
	Bank(double balance,String accontHolderName){
		this.balance=balance;
		this.accountHolderName=accountHolderName;
	}
}
	class Account extends Bank{
		Account(double balance,String accountHolderName){
			super(balance,accountHolderName);
	
		}
		void deposite(double amount) {
			balance=balance+amount;
			System.out.println("Ammount Deposite:"+amount);
			System.out.print("Current Balance:"+balance);
			
		}
		void withdraw(double amount) {
			if (amount<=balance) {
				balance=balance-amount;
				System.out.println("Amount Withdrawn:"+amount);
				System.out.print("Current Balance:"+balance);
				
			}else {
				System.out.println("Insufficient Balance");
			}
		}
	}
	class ATM extends Account{
		int pin=1234;
		ATM(double balance,String accountHolderName){
			super(balance,accountHolderName);
		}
		void checkpin(int enteredpin) {
			if(enteredpin==pin) {
				Scanner sc=new
						Scanner(System.in);
				System.out.println("PIN is correct");
				System.out.println("1.Withdraw");
				System.out.println("2.Deposit");
				System.out.println("Enter your choice:");
				int choice=sc.nextInt();
				if(choice==1) {
					System.out.print("Enter amount to withdraw:");
					double amount=sc.nextDouble();
					withdraw(amount);
							
				}
				else if (choice==2) {
					
				
					System.out.print("Enter amount to deposit:");
					
					double amount=sc.nextDouble();
					
					deposite(amount);
			
				}
				else {
					
					System.out.println("Invalid choice");
				}
		}else {
			System.out.println("Wrong PIN-Access is Denied");
		}
	}}
	public static void main(String[]args) {
		
		InheritanceBankingPragram bank=new InheritanceBankingPragram();
		
			
	
			
			    ATM obj=bank.new ATM(20000,"gayathri");
				System.out.println("Account Holder:"+obj.accountHolderName);
				System.out.println("Intial Balance:"+obj.balance);
				Scanner sc=new Scanner(System.in);
				System.out.print("Enter ATM PIN:");
				int enteredpin=sc.nextInt();
				obj.checkpin(enteredpin);
				sc.close();
			}
				}
		

				
				
				
				
				
				
				
				
				
				
				
				
				
			
		
	
	
	
	
	
	
	

