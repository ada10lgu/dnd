package general.connection.packages;

import java.io.IOException;
import java.io.InputStream;

public class StringPackage extends Package {

	private String s;
	
	public StringPackage(String s) {
		super(STRING);
		this.s = s;
	}

	protected StringPackage(InputStream is) throws IOException {
		super(STRING);
		int size = read(is);
		byte[] string = new byte[size];
		for (int i = 0; i < size; i++)
			string[i] = (byte) read(is);
		byte extra = read(is);
		if (extra != 0)
			throw new IOException("StringPackage does not support nested packages");
		
		s = new String(string);
	}

	@Override
	protected byte[] getData() {
		return s.getBytes();
	}

	@Override
	protected Package[] getPackages() {
		return new Package[0];
	}
	
	@Override
	public String toString() {
		return s;
	}

}
