package general.connection.packages.operator;

import general.GenericModel;
import general.connection.connection.Connection;
import general.connection.packages.OperatorPackage;
import general.connection.packages.Package;
import general.connection.packages.data.IntegerPackage;
import general.connection.packages.data.StringPackage;

import java.io.IOException;
import java.io.InputStream;

public class LoginPackage extends OperatorPackage {

	private StringPackage user;
	private StringPackage pass;

	public LoginPackage(String username, String hash) {
		super(LOGIN);
		user = new StringPackage(username);
		pass = new StringPackage(hash);
	}

	public LoginPackage(InputStream is) throws IOException {
		super(LOGIN);
		byte data = read(is);
		if (data != 0)
			throw new IOException("LoginPackage does not support datafield");
		byte size = read(is);
		if (size != 2)
			throw new IOException("LoginPackage demands 2 packages");
		user = (StringPackage) generateFromStream(is);
		pass = (StringPackage) generateFromStream(is);
	}

	@Override
	protected byte[] getData() {
		return new byte[0];
	}

	@Override
	protected Package[] getPackages() {
		return new Package[]{user,pass};
	}
	
	@Override
	public String toString() {
		return "Login: " + user.toString() + ", " + pass.toString();
	}


	@Override
	public OperatorPackage perform(GenericModel model,Connection c) {
		int id = model.login(user.toString(),pass.toString(),c);
		
		IntegerPackage ip = new IntegerPackage(id);
		return new DataPackage(ip);		
	}
}