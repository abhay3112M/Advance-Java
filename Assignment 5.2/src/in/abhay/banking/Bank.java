package in.abhay.banking;

public class Bank {

	
	private String name;
	private double rate;
	

	public Bank(String name, double rate) {
		// TODO Auto-generated constructor stub
		this.name=name;
		this.rate=rate;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	public double getRate() {
		// TODO Auto-generated method stub
		return rate;
	}
	
	int accountCount=0;	
	int lastAccountNumber=0;
	BankAccount [] accounts=new BankAccount[100];


	public int openAccount(String name, String password, double amount,int accountType) {
		// TODO Auto-generated method stub
		int accountNumber= ++lastAccountNumber;
		accountCount++;
		//BankAccount account;
		if(accountType==1)
			accounts[accountNumber]=new SavingAccount(accountNumber, name,password,amount);
		else if(accountType==2)
			accounts[accountNumber]=new CurrentAccount(accountNumber, name,password,amount);
		else if(accountType==3)
			accounts[accountNumber]=new OverDraftAccount(accountNumber, name,password,amount);
		//accounts[accountNumber]= account;
		return accountNumber;
	}
	
	
	public double closeAccount(int accountNumber, String password) {
		// TODO Auto-generated method stub
		var account=getAccount(accountNumber,password);
		if(account==null)
			return -1;
		
		accounts[accountNumber]=null;
		accountCount--;
		return account.getBalance();
	}

	public int getAccountCount() {
		// TODO Auto-generated method stub
		return accountCount;
	}

	public BankAccount getAccount(int accountNumber, String password) {
		// TODO Auto-generated method stub
		if(accountNumber<1 || accountNumber>lastAccountNumber )
				return null;
		
		var account=accounts[accountNumber];
		if(account==null)
			return null;
		
		if(!account.authenticate(password))
			return null;
		
		return account;
	}

	public boolean deposit(int accountNumber, int amount) {
		// TODO Auto-generated method stub
		if(accountNumber<1 || accountNumber>lastAccountNumber)	return false;
		var account=accounts[accountNumber];
		if(account==null)	return false;
		return account.deposit(amount);
	}

	public Response withdraw(int accountNumber, int amount, String password) {
		// TODO Auto-generated method stub
		if(accountNumber<1 || accountNumber>lastAccountNumber || accounts[accountNumber]==null)
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Account Does Not Exist");
		var account=accounts[accountNumber];
		return account.withdraw(amount, password);
	}

	public Response transfer(int accountNumber, int amount, String password, int targetAccount) {
		// TODO Auto-generated method stub
		var account1 = getAccount(accountNumber,password);
		if(account1==null)
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Source Account Credential");
		if(targetAccount<1 || targetAccount>lastAccountNumber || accounts[targetAccount]==null)
			return new Response(ResponseStatus.INVALID_CREDENTIALS,"Invalid Target Account");
		var account2 = accounts[targetAccount];
		Response response = account1.withdraw(amount, password);
		if(response.getCode()==ResponseStatus.SUCCESS) {
			account2.deposit(amount);
			return response;
		}
		return response;
	}

	public void creditInterest() {
		// TODO Auto-generated method stub
		for(int i=0;i<accounts.length;i++) {
			if(accounts[i]==null) continue;
			double interestRate = accounts[i].getInterestRate();
			double balance = accounts[i].getBalance();
			double intersestAmount = (balance*interestRate/1200);
			accounts[i].deposit((int)intersestAmount);
		}
	}
	
	public double getBalance(int accountNumber, String password) {
		//TODO Auto-generated method stub
		var account = getAccount(accountNumber,password);
		if(account==null) return -1;
		return account.getBalance();
	}

	public double getInterest(int accountNumber, String password) {
		// TODO Auto-generated method stub
		var account = getAccount(accountNumber,password);
		if(account==null) return -1;
		return account.getInterestRate();
	}


}















