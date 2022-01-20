package bank.account.services;
import input.services.Input;

public class BankAccount {
	
	private String name; //account holder name
	private int accountNumber;
	private String password;
	private double balance;
	private double interestRate;
	
	Input input=new Input();
	
	public int openBankAccount(String name,String password,double amount,double interestRate) {
		this.accountNumber = input.giveRandom9D();
		this.name=name;
		this.password=password;
		this.balance=amount;
		this.interestRate=interestRate;
		
		return this.accountNumber;
	}
	
	public String deposit(double amount) {
		if(amount>0) {
			balance+=amount;
			return "Amount is deposited";
		} else {
			return "Invalid amount. Deposit Failed";
		}		
	}
	
	public String withdraw(String pwd,double amount) {
		if(amount<0 ) {
			return "Invalid Amount. Withdraw Rejected";
		}else if(!this.password.equals(pwd)) {
			return "Invalid credential. withdraw rejected";
		}else if(amount>balance) {
			return "Insufficient Balance. Withraw Rejected";
		}else {
			balance-=amount;
			return "Please collect your cash...";
		}
	}
	
	public void changePassword(String pwd) {
		this.password=pwd;
		return ;
	}
	
	public int getAccountNum() {
		return this.accountNumber;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public String getBalance(String pwd) {
		if(!this.password.equals(pwd))
			return "Invalid credential.";
		return ""+this.balance;
	}
	
	public String setPassword(String pwd1,String pwd2) {
		if(!this.password.equals(pwd1))
			return "Wrong Current Password.";
		this.password=pwd2;
		return "Password Successfully updated";
	}
	
	public BankAccount showDetails() {
		return this;
	}
	
}
