package input.services;
import java.util.*;

public class Input {
	Scanner sc = new Scanner(System.in);
	
	public int readInt(String str) {
		System.out.println(str);
		int num = sc.nextInt();
		return num;
	}
	
	public long readLong(String str) {
		System.out.println(str);
		long num = sc.nextLong();
		return num;
	}
	
	public double readDouble(String str) {
		System.out.println(str);
		double num = sc.nextDouble();
		return num;
	}
	
	public String readString(String str) {
		System.out.println(str);
		String s = sc.nextLine();
		return s;
	}
	
	public Boolean readBool(String str) {
		System.out.println(str);
		Boolean bool = sc.nextBoolean();
		return bool;
	}
	
	public int giveRandom9D() {
		Random random = new Random(); 
		int num = 100000000 + random.nextInt(900000000);
		return num;
	}
}
