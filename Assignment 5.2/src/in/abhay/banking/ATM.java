package in.abhay.banking;

import in.abhay.utils.Input;

public class ATM {

	Bank bank; //associated parent bank.
	int accountNumber;
	private String password;

	static Input keyboard=new Input();
	

	public ATM(Bank bank) {
		super();
		this.bank = bank;
	}
	
	public static void main(String []args) {
		String bankName = keyboard.readString("Enter the Bank Name");
		int interestRate = keyboard.readInt("Set Interest Rate");
		ATM helper = new ATM(new Bank(bankName,interestRate));
		while(true) {
			helper.start();
		}
	}
	
	public void start() {
		accountNumber=keyboard.readInt("account number?");
		password=keyboard.readString("password?");
		//System.out.println(accountNumber),System.out.println(password);
		//A secret menu
		if(accountNumber==-1 && password.equals("NIMDA"))
			adminMenu();
		else
			mainMenu();
	}
	
	
	
	private void adminMenu() {
		// TODO Auto-generated method stub
		while(true) {
			var choice=keyboard.readInt("1. open account \n2. credit interest \n3. view all accounts \n0. exit ?");
			switch(choice) {
				case 1:
					openAccount();
					break;
				case 2:
					creditInterest();
					break;
				case 3:
					viewAllAcounts();
					break;
				case 0:
					return ;
				default:
					showError("invalid choice. retry");
			}
		}
	}

	private void viewAllAcounts() {
		// TODO Auto-generated method stub
		showInfo("Account Number\tName\tBalance");
		for(int i=0;i<bank.accounts.length;i++) {
			if(bank.accounts[i]==null) continue;
			showInfo(""+bank.accounts[i].getAccountNumber()+"\t\t"+bank.accounts[i].getName()+"\t"+bank.accounts[i].getBalance());
		}	
	}

	private void creditInterest() {
		// TODO Auto-generated method stub
		bank.creditInterest();
	}

	private void openAccount() {
		// TODO Auto-generated method stub
		int type = keyboard.readInt("Choose 1. Saving Account  2. Current Account   3. OverDraftAccount");
		String name = keyboard.readString("Enter the Name:");
		String password = keyboard.readString("Create Password:");
		int amount=keyboard.readInt("Initial Account Balnce:");
		int accountNumber = bank.openAccount(name, password, amount,type);
		showInfo("Your Account Number is: "+accountNumber);
	}

	private void mainMenu() {
		
		while(true) {
			var choice=keyboard.readInt("1. deposit \n2. withdraw \n3. check balance \n4. transfer \n5. close account \n0. exit ?");
			switch(choice) {
			case 1:
				doDeposit();
				break;
			case 2:
				doWithdraw();
				break;
				
			case 3:
				doCheckBlance();
				break;
				
			case 4:
				doTransfer();
				break;
				
			case 5:
				doCloseAccount();
				break;
				
			case 0:
				return ;
				
			default:
				showError("invalid choice. retry");
			
			}
		}
	}

	private void showError(String string) {
		// TODO Auto-generated method stub
		System.err.println(string);
	}

	private void doCloseAccount() {
		// TODO Auto-generated method stub
		var account = bank.getAccount(accountNumber,password);
		if(account==null){
			showError("Account Does Not Exist");
			return;
		}
		double balance=bank.closeAccount(accountNumber, password);
		dispenseCash((int)balance);
	}

	private void doTransfer() {
		// TODO Auto-generated method stub
		int amount=keyboard.readInt("amount?");
		int targetAccount=keyboard.readInt("target account?");
		Response response= bank.transfer(accountNumber, amount, password, targetAccount);
		if(response.getCode()==ResponseStatus.SUCCESS) {
			showInfo("Amount Transferred Successfully");
		} else {
			showError(response.getMessage());
		}
		
	}

	private void doCheckBlance() {
		// TODO Auto-generated method stub
		var account=bank.getAccount(accountNumber,password);
		if(account==null){
			showError("Invalid Credentials");
			return;
		}
		double balance=account.getBalance();
		showInfo("Your Balance:"+balance);
	}

	private void doWithdraw() {
		// TODO Auto-generated method stub
		int amount=keyboard.readInt("Amount to withdraw?");
		Response result= bank.withdraw(accountNumber, amount, password);
		if(result.getCode()==ResponseStatus.SUCCESS)
			dispenseCash(amount);
		else
			showError(result.getMessage());
		
	}

	private void dispenseCash(int amount) {
		// TODO Auto-generated method stub
		System.out.println("Please collect your cash : "+amount);
	}

	private void doDeposit() {
		// TODO Auto-generated method stub
		int amount=keyboard.readInt("Deposit Amount?"); //ATM allows only whole sum (actully multiple of 100)
		if(amount%100!=0) {
			showError("Invalid Denomination");
			return ;
		}
		
		if(bank.deposit(accountNumber, amount))
			showInfo("Amount deposited");
		else
			showInfo("Deposit failed");
		
		
	}

	private void showInfo(String string) {
		// TODO Auto-generated method stub
		System.out.println(string);
	}
	
	
}
