package bank.account;

public class BankAccount {
	
	private String Name;
	private int AccountNumber;
	private int InterestRate = 5;
	private String Password;
	private int Balance=0;
	
	public void openAccount(String name,int accNo,String pwd) {
		this.Name = name;
		this.AccountNumber=accNo;
		this.Password=pwd;
		System.out.println("Account Successfully Created");
	}
	
	public void depositMoney(int money) {
		if(money<=0)
			System.out.println("Money deposite is only granted if money will more than Zero Rupees");
		else {
			this.Balance+=money;
			System.out.println("Money Successfully deposited"); 
		}
	}
	
	public void withdrawMoney(String pwd,int money) {
		if(money<=0) {
			System.out.println("Enter money greater than Zero Rupees");
			return ;
		}
		if((this.Password).equals(pwd) && this.Balance>=money) {
			System.out.println("Money Successfully withdrawn");
			this.Balance-=money;
		}
		else {
			if(!(this.Password).equals(pwd))
				System.out.println("Wrong Password");
			else
				System.out.println("Over Withdrawal Not Possible");
		}	
	}
	
	public void showDetails(String pwd) {
		if(this.Password.equals(pwd)) {
			System.out.println("Account Number: "+this.AccountNumber);
			System.out.println("Customer Name: "+this.Name);
			System.out.println("Total Balance: "+this.Balance);
		}
		else {
			System.out.println("Wrong Password");
		}
	}

}
