package in.abhay.banking;

public class OverDraftAccount extends BankAccount{
	static double interestRate = 7;
	public OverDraftAccount(int accountNumber, String name,String password, double amount) {
		super(accountNumber,name,password,amount,interestRate);
	}
}
