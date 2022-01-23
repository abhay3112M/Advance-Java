package com.bank;
import java.util.ArrayList ;
import com.bank.account.BankAccount;
import com.bank.utils.IoHelp;
import com.bank.services.*;

public class Bank {
	static int interestRate;
	static ArrayList<BankAccount> sbi=new ArrayList<BankAccount>();;
	static {
		interestRate=12;
		//ArrayList<BankAccount> sbi = new ArrayList<BankAccount>();
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		while(true) {
			showMenu();
			int choice = IoHelp.instance.readInt("Choice: ");
			switch(choice) {
			case 1:
					createBankAccount();
					break;
			case 2:{
					int accNum = IoHelp.instance.readInt("Enter your Account number:");
					double money = IoHelp.instance.readDouble("Enter Money you want to deposit");
					int idx=findAccount(accNum);
					if(idx==-1){ 
						System.out.println("No account Exist with this Account Number");
						break;
					}
					Response response = sbi.get(idx).deposit(money);
					System.out.println(response.getMessage());
				}
					break;
			case 3:{
					int accNum = IoHelp.instance.readInt("Enter your Account number:");
					String Dummy = IoHelp.instance.readString(""); //extra line cure
					String pwd = IoHelp.instance.readString("Enter Your password:");
					double money = IoHelp.instance.readDouble("Enter Money you want to Withdraw");
					int idx=findAccount(accNum);
					if(idx==-1){ 
						System.out.println("No account Exist with this Account Number");
						break;
					}
					Response response = sbi.get(idx).withdraw(pwd,money);
					System.out.println(response.getMessage());
				}
					break;
			case 4:{
					int accNum = IoHelp.instance.readInt("Enter your Account number:");
					String Dummy = IoHelp.instance.readString(""); //extra line cure
					String pwd = IoHelp.instance.readString("Enter Your password:");
					int idx=findAccount(accNum);
					if(idx==-1){ 
						System.out.println("No account Exist with this Account Number");
						break;
					}
					Response response = sbi.get(idx).getBalanceCustomer(pwd);
					System.out.println(response.getMessage());
				}
				break;
			case 5:{
					int accNum1 = IoHelp.instance.readInt("Enter your Account number:");
					String Dummy = IoHelp.instance.readString(""); //extra line cure
					String pwd = IoHelp.instance.readString("Enter Your password:");
					double money = IoHelp.instance.readDouble("Enter Money you want to Transfer");
					int accNum2 = IoHelp.instance.readInt("Enter Account number to which you want to transfer:");
					int idx1=findAccount(accNum1);
					int idx2=findAccount(accNum2);
					if(idx1==-1 || idx2==-1){
						System.out.println("Invalid Credential");
						break;
					}
					Response response = sbi.get(idx1).withdraw(pwd,money);
					if(response.getCode()==ResponseStatus.SUCCESS) {
						sbi.get(idx2).deposit(money);
						System.out.println("Transfer Successfull");
					}else {
						System.out.println(response.getMessage());
					}
				}
					break;
			case 6:{
					int accNum = IoHelp.instance.readInt("Enter your Account number:");
					String Dummy = IoHelp.instance.readString(""); //extra line cure
					String pwd = IoHelp.instance.readString("Enter Your password:");
					int idx=findAccount(accNum);
					if(idx==-1){ 
						System.out.println("No account Exist with this Account Number");
						break;
					}
					Response response = sbi.get(idx).getInfo(pwd);
					if(response.getCode()==ResponseStatus.SUCCESS) {
						System.out.println("AccountNumber\tName\t+Balance");
						System.out.println(response.getMessage());
					}else {
						System.out.println(response.getMessage());
					}
				}
					break;
			case 7:{
				int accNum = IoHelp.instance.readInt("Enter your Account number:");
				String Dummy = IoHelp.instance.readString(""); ////extra line cure
				String pwd1 = IoHelp.instance.readString("Enter Your Current password:");
				String pwd2 = IoHelp.instance.readString("Enter Your New password:");
				int idx=findAccount(accNum);
				if(idx==-1){ 
					System.out.println("No account Exist with this Account Number");
					break;
				}
				Response response = sbi.get(idx).changePassword(pwd1, pwd2);
				System.out.println(response.getMessage());
			}
				break;
			case 8:{
				int accNum = IoHelp.instance.readInt("Enter your Account number:");
				String Dummy = IoHelp.instance.readString(""); //extra line cure
				String pwd = IoHelp.instance.readString("Enter Your password:");
				int idx=findAccount(accNum);
				if(idx==-1){ 
					System.out.println("No account Exist with this Account Number");
					break;
				}
				Response response = sbi.get(idx).getBalanceCustomer(pwd);
				if(response.getCode()==ResponseStatus.SUCCESS) {
					sbi.remove(idx);
					System.out.println("Please Collect Your all money: "+response.getMessage());
				}else {
					System.out.println(response.getMessage());
				}
			}
				break;
			case 9:
				System.out.println("Thanks Visit Again !");
				System.exit(0); 
			}
		}
	}
	
	private static void creditInterest() {
		for (BankAccount account : sbi) {
			double currBalance = account.getBalanceBank();
			account.deposit(currBalance*interestRate/1200);
		}		
	}
	
	public static boolean collisonAccount(int accNum) {
		int idx = findAccount(accNum);
		return idx!=-1;
	}
	
	public static int findAccount(int accNum) {
		int ans=-1;
		for(int i=0;i<sbi.size();i++){
			int currAccNum = sbi.get(i).getAccountNumber();
			if(currAccNum==accNum) {
				ans=i;
				break;
			}
		}
		return ans;
	}
	
	private static void createBankAccount() {
		String Dummy = IoHelp.instance.readString(""); //extra line cure
		String name = IoHelp.instance.readString("Enter your name:");
		String pwd = IoHelp.instance.readString("Choose your password:");
		double amount = IoHelp.instance.readDouble("Enter your Initial opening account balance:");
		int accountNum;
		do {
			accountNum = createAccountNumber();
		}while(collisonAccount(accountNum));
		BankAccount account = new BankAccount(accountNum,name,pwd,amount);
		sbi.add(account);
		System.out.println("Account Successfully Created, Remember the Account Number and Choosen Password.");
		System.out.println("Your Account Number is "+accountNum);
	}
	
	private static int createAccountNumber() {
		int num = IoHelp.instance.giveRandom9D();
		return num;
	}
	
	
	public static void showMenu() {
		System.out.println("----------------------------------------------------");
		System.out.println("!!! Welome to SBI Bank - Service with Security !!!");
		System.out.println("Enter 1 for Create a Bank Account");
		System.out.println("Enter 2 for Deposit");
		System.out.println("Enter 3 for Withdraw");
		System.out.println("Enter 4 for Check Balance");
		System.out.println("Enter 5 for Transfer Money");
		System.out.println("Enter 6 for Know all your Account details");
		System.out.println("Enter 7 for Changing Password");
		System.out.println("Enter 8 for Close Account");
		System.out.println("Enter 9 for EXIT");
		System.out.println("----------------------------------------------------");
	}
	

}
//824739434  pass@1
//919321781  ashu420