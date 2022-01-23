package com.bank.utils;

public class Encrypt {
	static int salt;
	static {
		salt=10;
	}
	public String encrypt(String str) {
		return generateHash(str);
	}

	public boolean match(String hash,String plain) {
		return generateHash(plain).equals(hash);
	}
	
	private String generateHash(String str) {
		String newPassword="";
		for(int i=0;i<str.length();i++) {
			int ch=(int) str.charAt(i)+salt;
			newPassword+=""+ch;
		}
		return newPassword;
	}
	
	public static Encrypt instance=new Encrypt();

}
