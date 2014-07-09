package model.network.packages.operator;

import java.io.IOException;
import java.io.InputStream;

import model.DNDModel;
import model.network.Connection;
import model.network.packages.OperatorPackage;
import model.network.packages.Package;

public class ExitPackage extends OperatorPackage {

	public ExitPackage() {
		super(EXIT,"Exit package");
		// TODO Auto-generated constructor stub
	}

	public ExitPackage(InputStream is) throws IOException {
		super(EXIT,"Exit package");
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
	public OperatorPackage perform(DNDModel model, Connection conn) {
		return null;
	}


}
