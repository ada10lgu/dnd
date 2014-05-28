package general.connection.packages;

import java.io.IOException;
import java.io.InputStream;

public class LoginPackage extends Package {

	private StringPackage user;
	private StringPackage pass;

	public LoginPackage(String username, String hash) {
		super(LOGIN);
		user = new StringPackage(username);
		pass = new StringPackage(hash);
	}

	protected LoginPackage(InputStream is) throws IOException {
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

	public String getUsername() {
		return user.toString();
	}

	public String getPassword() {
		return pass.toString();
	}
}