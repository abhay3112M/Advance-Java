package in.abhay.banking;

public class CurrentAccount extends BankAccount{
	static private double interestRate = 0;
	public CurrentAccount(int accountNumber, String name,String password, double amount) {
		super(accountNumber,name,password,amount,interestRate);
	}
}
