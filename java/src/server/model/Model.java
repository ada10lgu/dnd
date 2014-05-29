package server.model;

import general.GenericModel;
import general.connection.connection.Connection;
import general.write.Writer;

import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import server.model.database.Database;

public class Model extends Writer implements GenericModel {

	private final static String SQL_CONNECTION = "sql/databaseconnection.con";

	private Database db;

	public Model() {
		super("Model");
		try {
			db = new Database(new File(SQL_CONNECTION));
			db.connect();
			write("Connected!");
		} catch (IOException e1) {
			System.err
					.println("Could not read config file for database connection");
			e1.printStackTrace();
			System.exit(1);
		} catch (SQLException e2) {
			System.err.println("Could not establish databse-connection");
			e2.printStackTrace();
			System.exit(1);
		}

	}
	public int login(String username, String hash,Connection c) {
		String sql = "SELECT id FROM users WHERE username = ? AND password = ?";
		try {
			ResultSet res = db.query(sql, username, hash);

			if (res.next()) {
				int id = res.getInt("id");
				c.write("Login successfull");
				c.write("id="+id);
				return id;
			}

		} catch (SQLException e) {
			printSQLError("Could not log in user...", sql, e);
		}
		c.write("Login failed");
		return -1;
	}

	private void printSQLError(String message, String sql, Exception e) {
		write("Error: " + message);
		write("Look in the log for more details!");
		System.err.println("----- ERROR -----");
		System.err.println(message);
		System.err.println(sql);
		e.printStackTrace();
		System.err.println("-----------------");
	}

	@Override
	public void exit(Connection c) {
		throw new UnsupportedOperationException();
		
	}
}
