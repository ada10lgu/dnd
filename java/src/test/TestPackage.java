package test;

import general.connection.packages.LoginPackage;
import general.connection.packages.Package;
import general.connection.packages.SuperPackage;

import java.util.Arrays;

public class TestPackage {

	public static void main(String[] args) {

		
		
		Package p1 = new LoginPackage("Lars","pass");
		
		Package p =  new SuperPackage((byte) 5, Package.ACK, p1);
		
		
		
		
		print(p);
	}

	public static void print(Package p) {
		System.out.println(p);
		System.out.println(Arrays.toString(p.getBytes()));
	}
}
