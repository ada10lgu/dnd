package model.network.packages.data;

import java.io.IOException;
import java.io.InputStream;
import model.network.packages.Package;

public class NullPackage extends Package {

	public NullPackage() {
		super(NULL,"Null package");
	}

	public NullPackage(InputStream is) throws IOException {
		super(NULL,"Null package");
		readData(is, 0);
		readPackages(is, 0);
	}

	@Override
	protected byte[] getData() {
		return new byte[0];
	}

	@Override
	protected Package[] getPackages() {
		return new Package[0];
	}

	@Override
	public String toString() {
		return "NULL";
	}
}
