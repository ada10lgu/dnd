package model;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import model.database.Database;
import model.network.Server;
import model.network.packages.data.StringPackage;
import model.network.packages.operator.DataPackage;
import model.settings.Settings;

public class DNDServerModel {

	private Database db;

	public DNDServerModel(Settings settings) throws IOException, SQLException {
		db = new Database(settings);
		db.connect();

		int port = -1;
		try {
			String p = settings.getSetting("server_port");
			port = Integer.parseInt(p);
		} catch (NumberFormatException e) {
		}
		new Server(this, port);
	}

	public int login(String username, String hash) {
		String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
		try {
			ResultSet res = db.query(sql, username, hash);
			if (res.next()) {
				int id = res.getInt("id");
				return id;
			}
		} catch (SQLException e) {
			db.printSQLError("Could not log in user...", sql, e);
		}
		return -1;
	}

	public DataPackage getUser(int who) {
		String username = "";
		String name = "";
		String email = "";
		
		try {
			ResultSet rs = db.query("SELECT username, name,email FROM users WHERE id = ? LIMIT 1;",who);
			if (rs.next()) {
				username = rs.getString("username");
				name = rs.getString("name");
				email = rs.getString("email");
				
			}
				
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
		StringPackage spUser = new StringPackage(username);
		StringPackage spName = new StringPackage(name);
		StringPackage spEmail = new StringPackage(email);
		
		
		return new DataPackage(spUser,spName,spEmail);
	}

}
