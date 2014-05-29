package general.connection.packages.operator;

import general.GenericModel;
import general.connection.connection.Connection;
import general.connection.packages.OperatorPackage;
import general.connection.packages.Package;

import java.io.IOException;
import java.io.InputStream;

public class ExitPackage extends OperatorPackage {

	public ExitPackage() {
		super(EXIT);
		// TODO Auto-generated constructor stub
	}

	public ExitPackage(InputStream is) throws IOException {
		super(EXIT);
		byte data = read(is);
		if (data != 0)
			throw new IOException("ExitPackage does not support datafield");
		byte size = read(is);
		if (size != 0)
			throw new IOException("ExitPackage does not support nested packages");
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
	public OperatorPackage perform(GenericModel model,Connection c) {
		model.exit(c);
		return null;
	}

}
