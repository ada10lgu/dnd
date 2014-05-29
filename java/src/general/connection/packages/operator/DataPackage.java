package general.connection.packages.operator;

import general.GenericModel;
import general.connection.connection.Connection;
import general.connection.packages.OperatorPackage;
import general.connection.packages.Package;

import java.io.IOException;
import java.io.InputStream;

public class DataPackage extends OperatorPackage {

	private Package[] packages;
	
	public DataPackage(Package... p) {
		super(DATA);
		packages = p;
	}


	public DataPackage(InputStream is) throws IOException {
		super(DATA);
		byte data = read(is);
		if (data != 0)
			throw new IOException("DataPackage does not support datafield");
		byte size = read(is);
		packages = new Package[size];
		for (int i = 0; i < size; i++)
			packages[i] = generateFromStream(is);
	}


	@Override
	public OperatorPackage perform(GenericModel model,Connection c) {
		return null;
	}

	@Override
	protected byte[] getData() {
		return new byte[0];
	}

	@Override
	protected Package[] getPackages() {
		return packages;
	}

}
