package model.general;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HelpMethods {
	
	
	
	private HelpMethods() {

	}

	public static String nl() {
		return System.getProperty("line.separator");
	}
	
	/**
	 * 
	 * @param word
	 *            the string to be hashed
	 * @return SHA1-hash of the word
	 */
	public static String hash(String word) {
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
}
