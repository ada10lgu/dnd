package client.model;

import general.connection.packages.*;
import general.connection.packages.Package;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import client.model.connection.Client;

public class UserModel {

	private Client c;

	public UserModel() {
		c = new Client("localhost", 12345);
	}

	public boolean login(String username, String password) {
		password = hash(password);
		LoginPackage l = new LoginPackage(username, password);
		
		c.write(new SuperPackage((byte)5, Package.SEND, l));
		return false;
	}

	/**
	 * 
	 * @param word
	 *            the string to be hashed
	 * @return SHA1-hash of the word
	 */
	private String hash(String word) {
		byte[] data = word.getBytes();
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		byte[] b = md.digest(data);
		String result = "";
		for (int i = 0; i < b.length; i++) {
			result += Integer.toString((b[i] & 0xff) + 0x100, 16).substring(1);
		}
		return result;
	}

	public void close() {
		c.close();

	}
}
