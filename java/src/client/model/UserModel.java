package client.model;

import general.GenericModel;
import general.connection.connection.Connection;
import general.connection.packages.operator.LoginPackage;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class UserModel implements GenericModel {

	private Connection c;

	public UserModel() throws IOException {
		
		c = Connection.createConnection("localhost", 12345,this);
	}

	public int login(String username, String password,Connection c) {
		throw new UnsupportedOperationException();
	}
	
	public boolean login(String username, String password) {
		password = hash(password);
		LoginPackage lp = new LoginPackage(username, password);
		
		byte id =c.write(lp);
		//DataPackage dp = (DataPackage) c.getResponse(id);
		//System.out.println(dp);
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

	@Override
	public void exit(Connection c) {
		this.c.close();
		
	}

}
