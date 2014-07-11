package model.network.packages.operator;

import java.io.IOException;
import java.io.InputStream;

import model.DNDModel;
import model.network.Connection;
import model.network.packages.OperatorPackage;
import model.network.packages.Package;
import model.network.packages.data.IntegerPackage;

public class UserRequest extends OperatorPackage {

	int who;
	
	public UserRequest(int who) {
		super(USER_REQUEST, "User request package");
		this.who = who;
	}

	public UserRequest(InputStream is) throws IOException {
		super(USER_REQUEST, "User request package");
		readData(is, 0);
		IntegerPackage ip = (IntegerPackage) readPackages(is, 1)[0];
		who = ip.toInt();
	}

	@Override
	public OperatorPackage perform(DNDModel model, Connection conn) {
		throw new UnsupportedOperationException();
	}

	@Override
	protected byte[] getData() {
		return new byte[0];
	}

	@Override
	protected Package[] getPackages() {
		return new Package[]{new IntegerPackage(who)};
	}

	
}
