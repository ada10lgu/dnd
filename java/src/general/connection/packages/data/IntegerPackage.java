package general.connection.packages.data;
import general.connection.packages.Package;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;


public class IntegerPackage extends Package {

	private Integer i;
	
	public IntegerPackage(int i) {
		super(INT);
		this.i = i;
	}

	public IntegerPackage(InputStream is) throws IOException {
		super(INT);
		int size = read(is);
		if (size != 4)
			throw new IOException("IntegerPackage has illegal datalenght");
		byte[] value = new byte[size];
		for (int i = 0; i < size; i++)
			value[i] = (byte) read(is);
		byte extra = read(is);
		if (extra != 0)
			throw new IOException("IntegerPackage does not support nested packages");
		
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

}
