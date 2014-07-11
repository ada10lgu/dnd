package model.network.packages.data;

import model.network.packages.Package;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class IntegerPackage extends Package {
	private Integer i;

	public IntegerPackage(int i) {
		super(INT, "Integer package");
		this.i = i;
	}

	public IntegerPackage(InputStream is) throws IOException {
		super(INT, "Integer package");

		byte[] value = readData(is, 4);
		readPackages(is, 0);
		ByteBuffer bb = ByteBuffer.wrap(value);
		i = bb.getInt();
	}

	@Override
	protected byte[] getData() {
		return ByteBuffer.allocate(4).putInt(i).array();
	}

	@Override
	protected Package[] getPackages() {
		return new Package[0];
	}
	
	@Override
	public String toString() {
		return i.toString();
	}

	public int toInt() {
		return i;
	}

}
