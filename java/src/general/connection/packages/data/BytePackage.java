package general.connection.packages.data;

import general.connection.packages.Package;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

public class BytePackage extends Package {

	private byte[] data;

	public BytePackage(byte... data) {
		super(BYTE);
		this.data = data;
	}

	public BytePackage(InputStream is) throws IOException {
		super(BYTE);
		int size = read(is);
		data = new byte[size];
		for (int i = 0; i < size; i++)
			data[i] = (byte) read(is);
		byte extra = read(is);
		if (extra != 0)
			throw new IOException(
					"BytePackage does not support nested packages");
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

	public byte[] getLoad() {
		return data;
	}

}
