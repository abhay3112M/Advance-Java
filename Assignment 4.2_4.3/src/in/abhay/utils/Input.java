package in.abhay.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Input {
	 BufferedReader reader=new BufferedReader(new InputStreamReader(System.in));
	
	public String readString(String prompt) {
		try {
		System.out.print(prompt+"\n");
		return reader.readLine();
		}catch(Exception e) {
			return "";
		}
	}
	
	public int readInt(String prompt) {
		
		try {
			return Integer.parseInt(readString(prompt+"\n"));
		}catch(Exception ex) {
			return 0;
		}
	}

}