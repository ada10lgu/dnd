package test;

import general.connection.packages.OperatorPackage;
import general.connection.packages.Package;
import general.connection.packages.SuperPackage;
import general.connection.packages.data.IntegerPackage;
import general.connection.packages.operator.LoginPackage;

import java.util.Arrays;

public class TestPackage {

	public static void main(String[] args) {

		
		
		Package p = new IntegerPackage(257);
		
		
		
		print(p);
	}

	public static void print(Package p) {
		System.out.println(p);
		System.out.println(Arrays.toString(p.getBytes()));
	}
}
