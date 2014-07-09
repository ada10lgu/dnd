package model.network.packages.operator;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;

import model.DNDModel;
import model.network.Connection;
import model.network.packages.OperatorPackage;
import model.network.packages.Package;

public class DataPackage extends OperatorPackage {

	private Package[] packages;

	public DataPackage(Package... p) {
		super(DATA, "Data package");
		packages = p;
	}

	public DataPackage(InputStream is) throws IOException {
		super(DATA, "Data package");
		readData(is, 0);
		packages = readPackages(is, -1);
	}

	@Override
	protected byte[] getData() {
		return new byte[0];
	}

	@Override
	protected Package[] getPackages() {
		return packages;
	}

	@Override
	public OperatorPackage perform(DNDModel model, Connection conn) {
		return null;
	}
	
	@Override
	public String toString() {
		return "Data " + Arrays.toString(packages);
	}

}
