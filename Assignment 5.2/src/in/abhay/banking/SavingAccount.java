package in.abhay.banking;

public class SavingAccount extends BankAccount{
	static double interestRate = 7;
	static int minReqBalance = 5000;
	public SavingAccount(int accountNumber, String name,String password, double amount) {
		super(accountNumber,name,password,amount,interestRate);
	}
	
	//@Override
	public Response withdraw(int amount,String password) {
		if(super.getBalance()-amount<5000)
			return new Response(ResponseStatus.INSUFFICIENT_FUNDS,"Minimum Account Balance Should be mainitained 5000");
		return super.withdraw(amount, password);		
	}
	
	
}
