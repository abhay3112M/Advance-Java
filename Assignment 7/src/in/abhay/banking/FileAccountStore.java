package in.abhay.banking;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import in.abhay.banking.exceptions.InvalidAccountException;

public class FileAccountStore implements Serializable{
	int lastAccountNumber=0;
	String path = "bankAccountObjects.txt";
	

	
	public int addAccount(BankAccount account) {
		int accountNumber= ++lastAccountNumber;
		account.setAccountNumber(accountNumber);
		try {
			ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(path)));
			oos.writeObject(account);
			oos.close();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
			//cont = false;
		}
		return accountNumber;
	}
	
	public BankAccount getAccount(int accountNumber) {
		BankAccount Baccount=null;
		boolean cont = true;
		while (cont) {
			try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(path)))) {
				  Object obj = input.readObject();
				  if (obj != null) {
					  BankAccount account = (BankAccount)obj;
					  if(account.getAccountNumber()==accountNumber) {
						  Baccount=account;
						  input.close();
						  cont = false;
					  }
				  }else{
					  input.close();
					  cont = false;
				  }
			  }catch (Exception e) {
				  System.out.println(e.getMessage());
				  cont = false;
			    // System.out.println(e.printStackTrace());
			  }
		}
		if(Baccount!=null)
			return Baccount;
		else
			throw new InvalidAccountException(accountNumber);
	}
	
	public void removeAccount(int accountNumber) {
//		if(accounts.containsKey(accountNumber))
//			accounts.remove(accountNumber);
//		else
//			throw new InvalidAccountException(accountNumber);
	}
	
	public ArrayList<BankAccount> getAllActiveAccounts() {
		ArrayList<BankAccount> allAccounts = new ArrayList<BankAccount>();
		boolean cont = true;
		while (cont) {
			try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(new File(path)))) {
				  Object obj = input.readObject();
				  if (obj != null) {
					  BankAccount account = (BankAccount)obj;
					  allAccounts.add(account);
				  }else{
					  input.close();
					  cont = false;
				  }
			  }catch (Exception e) {
				  System.out.println(e.getMessage());
				  cont = false;
			    // System.out.println(e.printStackTrace());
			  }
		}
		return allAccounts;
	}
	
	public int getAccountCount() {
		// TODO Auto-generated method stub
		return getAllActiveAccounts().size();
	}
	
	

}
