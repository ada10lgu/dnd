package general.connection.packages;

import java.io.IOException;
import java.io.InputStream;

public class NullPackage extends Package {

	public NullPackage() {
		super(NULL);
	}

	protected NullPackage(InputStream is) throws IOException {
		super(NULL);
		byte data = read(is);
		if (data != 0)
			throw new IOException("Null package does not support datafield");
		byte size = read(is);
		if (size != 0)
			throw new IOException(
					"Null package does not support nested packages");
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
