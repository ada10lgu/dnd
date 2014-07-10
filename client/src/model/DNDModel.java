package model;

import static model.general.HelpMethods.hash;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import model.network.Connection;
import model.network.packages.data.IntegerPackage;
import model.network.packages.operator.DataPackage;
import model.network.packages.operator.LoginPackage;
import model.settings.Settings;

public class DNDModel {

	private Connection conn;
	private Settings settings;

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
		byte id = conn.sendPackage(lp);
		DataPackage dp = (DataPackage) conn.getResponse(id);
		IntegerPackage ip = (IntegerPackage) dp.getPackages()[0];
		if (ip.toInt() == -1) {
			System.out.println("Login failed!");
			return false;
		} else {
			System.out.println("Login successfull!");
			System.out.println("ID: " + ip.toInt());
			return true;
		}
	}

	public void close() {
		conn.close();
		System.exit(0);
	}
}
