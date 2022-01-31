package in.conceptarchitect.utils;

import java.util.ArrayList;

import in.conceptarchitect.banking.BankAccount;
import in.conceptarchitect.banking.CurrentAccount;

public class Helper {
	
	public ArrayList<BankAccount> currentAccounts(ArrayList<BankAccount> accounts) {
		ArrayList<BankAccount> resultAccount= new ArrayList<BankAccount>();
		for(BankAccount account : accounts) {
			if(account instanceof CurrentAccount)
				resultAccount.add(account);
		}
		return resultAccount;
	}

	public ArrayList<BankAccount> negativeBalanceAccounts(ArrayList<BankAccount> accounts) {
		ArrayList<BankAccount> resultAccount= new ArrayList<BankAccount>();
		for(BankAccount account : accounts) {
			if(account.getBalance()<0)
				resultAccount.add(account);
		}
		return resultAccount;
	}
	
	public ArrayList<BankAccount> mishraAccounts(ArrayList<BankAccount> accounts,String lastName) {
		ArrayList<BankAccount> resultAccount= new ArrayList<BankAccount>();
		for(BankAccount account : accounts) {
			var array = account.getName().split(" ");
			if(array[array.length-1].equalsIgnoreCase(lastName))
				resultAccount.add(account);
		}
		return resultAccount;
	}
	
	public ArrayList<BankAccount> regularPasswordAccounts(ArrayList<BankAccount> accounts,String password) {
		ArrayList<BankAccount> resultAccount= new ArrayList<BankAccount>();
		for(BankAccount account : accounts) {

//			if(account.)
//				resultAccount.add(account);
		}
		return resultAccount;
	}
}
