package open.bank.account;
import java.util.Scanner;

import bank.account.BankAccount;

public class OpenBankAccount {
	public static void main(String []args) {
		BankAccount bankaccount = new BankAccount();
		
		Scanner sc = new Scanner(System.in);
		
		
		
		System.out.println("Enter your name: ");
		String name = sc.nextLine();
		System.out.println("Choose your BankAccount number: ");
		int accNo = sc.nextInt();
		sc.nextLine();
		System.out.println("Choose your BankAccount Password: ");
		String pwd = sc.nextLine();
		
		
		
		bankaccount.openAccount(name,accNo,pwd); //accno-12345, pwd-pass@1
		bankaccount.depositMoney(2300);
		bankaccount.withdrawMoney(pwd,1200);
		bankaccount.showDetails(pwd);
	}
	
	
}
