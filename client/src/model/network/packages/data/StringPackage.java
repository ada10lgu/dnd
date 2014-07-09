package model.network.packages.data;

import java.io.IOException;
import java.io.InputStream;
import model.network.packages.Package;

public class StringPackage extends Package {

	private String s;

	public StringPackage(String s) {
		super(STRING, "String package");
		this.s = s;
	}

	public StringPackage(InputStream is) throws IOException {
		super(STRING, "String package");
		byte[] string = readData(is, -1);
		readPackages(is, 0);
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
