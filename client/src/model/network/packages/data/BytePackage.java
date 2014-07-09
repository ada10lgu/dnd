package model.network.packages.data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import model.network.packages.Package;


public class BytePackage extends Package {

	private byte[] data;

	public BytePackage(byte... data) {
		super(BYTE,"BytePackage");
		this.data = data;
	}

	public BytePackage(InputStream is) throws IOException {
		super(BYTE,"Bytepackage");
		data = readData(is, -1);
		readPackages(is, 0);
	}

	@Override
	protected byte[] getData() {
		return data;
	}

	@Override
	protected Package[] getPackages() {
		return new Package[0];
	}
	
	@Override
	public String toString() {
		return Arrays.toString(data);
	}

	public byte[] toByte() {
		return data;
	}

}
