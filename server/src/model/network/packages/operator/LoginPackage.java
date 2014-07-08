package model.network.packages.operator;

import java.io.IOException;
import java.io.InputStream;

import model.DNDServerModel;
import model.network.Connection;
import model.network.packages.OperatorPackage;
import model.network.packages.Package;
import model.network.packages.data.IntegerPackage;
import model.network.packages.data.StringPackage;

public class LoginPackage extends OperatorPackage {

	private StringPackage user;
	private StringPackage pass;

	public LoginPackage(String username, String hash) {
		super(LOGIN, "Login package");
		user = new StringPackage(username);
		pass = new StringPackage(hash);
	}

	public LoginPackage(InputStream is) throws IOException {
		super(LOGIN, "Login pacakge");
		readData(is, 0);
		Package[] packages = readPackages(is, 2);
		user = (StringPackage) packages[0];
		pass = (StringPackage) packages[1];
	}

	@Override
	protected byte[] getData() {
		return new byte[0];
	}

	@Override
	protected Package[] getPackages() {
		return new Package[] { user, pass };
	}

	@Override
	public String toString() {
		return "Login: " + user.toString() + ", " + pass.toString();
	}

	@Override
	public OperatorPackage perform(DNDServerModel model, Connection conn) {
		int id = model.login(user.toString(), pass.toString());
		return new DataPackage(new IntegerPackage(id));
	}
}