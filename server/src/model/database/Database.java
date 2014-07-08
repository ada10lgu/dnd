package model.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import model.settings.Settings;

public class Database {

	private String server, username, password, database;
	private Connection conn;

	public Database(Settings settings) throws FileNotFoundException {
		String information = settings.getSetting("mysql_connection");
		File f = new File(information);
		if (!f.exists())
			throw new FileNotFoundException(
					"Database-connection-settings-file not found. ("
							+ information + ")");
		Scanner s = new Scanner(new File(information));
		server = s.nextLine();
		database = s.nextLine();
		username = s.nextLine();
		password = s.nextLine();
		s.close();
	}

	public void connect() throws SQLException {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://" + server + "/"
					+ database, username, password);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void close() throws SQLException {
		if (conn != null) {
			conn.close();
		}
		conn = null;
	}

	public int update(String sql, Object... o) throws SQLException {
		PreparedStatement stmt = createStatement(0, sql, o);
		return stmt.executeUpdate();
	}

	public int insert(String sql, Object... o) throws SQLException {
		PreparedStatement stmt = createStatement(
				Statement.RETURN_GENERATED_KEYS, sql, o);

		stmt.executeUpdate();

		ResultSet result = stmt.getGeneratedKeys();

		if (!result.next())
			throw new SQLException("No row id was returned from DB.");
		return result.getInt(1);
	}

	public ResultSet query(String sql, Object... o) throws SQLException {
		PreparedStatement stmt = createStatement(0, sql, o);

		ResultSet set = stmt.executeQuery();

		return set;
	}

	private PreparedStatement createStatement(int data, String sql, Object... o)
			throws SQLException {
		PreparedStatement stmt = conn.prepareStatement(sql, data);

		for (int i = 0; i < o.length; i++) {
			switch (o[i].getClass().toString()) {
			case "class java.lang.Integer":
				stmt.setInt(i + 1, (int) o[i]);
				break;
			default:
				stmt.setString(i + 1, o[i].toString());
			}
		}

		return stmt;
	}

	public void printSQLError(String message, String sql, Exception e) {
		System.err.println("----- ERROR -----");
		System.err.println(message);
		System.err.println(sql);
		e.printStackTrace();
		System.err.println("-----------------");
	}

}
