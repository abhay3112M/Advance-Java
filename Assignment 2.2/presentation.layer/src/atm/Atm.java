package atm;
import input.services.Input;
import bank.account.services.BankAccount;

public class Atm {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Input input = new Input();
		BankAccount[] accounts = new BankAccount[10];
		int idx=0;
		while(true){
			showMenu();
			int choice = input.readInt("Choice: ");
			switch(choice) {
				case 1:{
						if(idx>=10) {
							System.out.println("All the Bank Account full, Try afer some time");
							break;
						}
						String Dummy = input.readString("");
						BankAccount account = new BankAccount();
						String name = input.readString("Enter your name:");
						String pwd = input.readString("Choose your password:");
						double amount = input.readDouble("Enter your Initial opening account balance:");
						double rate = input.readDouble("Enter the InterestRate");
						int accountNum=0;
						do {
							accountNum = account.openBankAccount(name, pwd, amount, rate);
						}while(collisonAccount(accounts,accountNum));
						accounts[idx++] = account;
						System.out.println("Account Successfully Created Remember the Account Number and Choosen Password.");
						System.out.println("Your Account Number is "+accountNum);
				}
					break;
				case 2:{
					int i=findAccount(accounts);
					if(i==-1) break;
					double money = input.readDouble("Enter Money you want to deposit");
					String response = accounts[i].deposit(money);
					System.out.println(response);
				}
					break;
				case 3:{
					int i=findAccount(accounts);
					if(i==-1) break;
					String Dummy = input.readString("");
					String pwd = input.readString("Enter Your password:");
					double money = input.readDouble("Enter Money you want to Withdraw");
					String response = accounts[i].withdraw(pwd,money);
					System.out.println(response);
				}
					break;
				case 4:{
					int i=findAccount(accounts);
					if(i==-1) break;
					String Dummy = input.readString("");
					String pwd = input.readString("Enter Your password:");
					String response = accounts[i].getBalance(pwd);
					System.out.println(response);
				}
					break;
				case 5:{
					int i=findAccount(accounts);
					if(i==-1) break;
					String Dummy = input.readString("");
					String pwd = input.readString("Enter Your password:");
					if(!accounts[i].getPassword().equals(pwd)) {
						System.out.println("Invalid credential.");
						break;
					}
					BankAccount response = accounts[i].showDetails();
					System.out.println("AccountNumber\tName\tBalance\tInterest Rate");
					//System.out.printf("%d\t%s\t%f\t%f\n",response.accountNumber,response.name,response.balance,response.interestRate);
				}
					break;
				case 6:{
					int i=findAccount(accounts);
					if(i==-1) break;
					String Dummy = input.readString("");
					String pwd1 = input.readString("Enter Your Current password:");
					String pwd2 = input.readString("Enter Your New password:");
					String response = accounts[i].setPassword(pwd1, pwd2);
					System.out.println(response);
				}
					break;
				case 7:
					System.out.println("Thanks Visit Again !");
					System.exit(0); 
			}
		}
	}
	
	public static void showMenu() {
		System.out.println("!!! Welome to Ninja ATM - Service with Security !!!");
		System.out.println("Enter 1 for Create a Bank Account");
		System.out.println("Enter 2 for Deposit");
		System.out.println("Enter 3 for Withdraw");
		System.out.println("Enter 4 for Check Balance");
		System.out.println("Enter 5 for Know all your Account details");
		System.out.println("Enter 6 for Changing Password");
		System.out.println("Enter 7 for EXIT");
	}
	
	public static boolean collisonAccount(BankAccount[] accounts,int accNum) {
		for(int i=0;i<accounts.length;i++) {
			if(accounts[i]==null) return false;
			if(accounts[i].getAccountNum()==accNum) return true; 
		}
		return false;
	}
	
	public static int findAccount(BankAccount[] accounts) {
		Input input = new Input();
		int accNum = input.readInt("Enter your Account number:");
		int ans=-1;
		for(int i=0;i<accounts.length;i++) {
			if(accounts[i]==null) break;
			if(accounts[i].getAccountNum()==accNum) {
				ans=i;
				break;
			}
		}
		if(ans==-1)
			System.out.println("No account Exist with this Account Number");
		return ans;
	}

}

//680658065
