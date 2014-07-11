package model;

import static model.general.HelpMethods.hash;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import model.network.Connection;
import model.network.packages.OperatorPackage;
import model.network.packages.data.IntegerPackage;
import model.network.packages.operator.DataPackage;
import model.network.packages.operator.LoginPackage;
import model.settings.Settings;

public class DNDModel {

	private Connection conn;
	private Settings settings;

	private User user;

	public DNDModel(Settings settings) throws UnknownHostException, IOException {
		this.settings = settings;
		Socket s = connectToServer();
		conn = new Connection(this, s);
	}

	private Socket connectToServer() throws UnknownHostException, IOException {
		String address = settings.getSetting("server_address");
		String portString = settings.getSetting("server_port");
		int port = -1;
		try {
			port = Integer.parseInt(portString);
		} catch (NumberFormatException e) {

		}
		return new Socket(address, port);
	}

	public boolean login(String user, String pass) {
		String hash = hash(pass);
		LoginPackage lp = new LoginPackage(user, hash);
		DataPackage dp = (DataPackage) sendAndWait(lp);
		IntegerPackage ip = (IntegerPackage) dp.getPackages()[0];

		int id = ip.toInt();
		if (id == -1) {
			return false;
		} else {
			this.user = new User(this, id);
			return true;
		}
	}

	public void close() {
		conn.close();
		System.exit(0);
	}

	public synchronized OperatorPackage sendAndWait(OperatorPackage op) {
		byte id = conn.sendPackage(op);
		return conn.getResponse(id);
	}

	public User getUser() {
		return user;
	}

	public void logout() {

	}


}
