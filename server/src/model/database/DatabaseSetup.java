package model.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;

import model.settings.Settings;

public class DatabaseSetup {

	public static void main(String[] args) throws SQLException, IOException {
		Scanner scan = new Scanner(System.in);
		Settings settings = new Settings();

		System.out.println("Skriv y om du vill nolla databasen");
		String word = scan.next();

		if (word.equals("y"))
			try {
				new DatabaseSetup(settings);
			} catch (FileNotFoundException e) {
				System.err.println("Kunde inte läsa filen....");
			}
		else
			System.out.println("nollar den INTE");
		scan.close();
	}

	public DatabaseSetup(Settings settings) throws FileNotFoundException {
		ArrayList<String> querys = readQuerys(new File(
				settings.getSetting("mysql_table_creation")));

		System.out.printf("%d querys read\n", querys.size());

		System.out.print("Connecting....");
		Database db = new Database(settings);
		try {
			db.connect();
			System.out.println("done!");
		} catch (SQLException e) {
			System.err
					.println("Could not open connection to db... terminating system");
			System.exit(1);
		}
		for (String s : querys) {

			try {
				db.update(s);
			} catch (SQLException e) {
				System.err.println("Could not parse query...");
				System.err.println(s);
				System.err.println(e.getMessage());
			}
		}
		try {
			System.out.println("Closing connection....");
			db.close();
		} catch (SQLException e) {
			System.err
					.println("Could not close connection to db... terminating system");
			System.exit(1);
		}
		System.out.println("Database is now clean");
	}

	private ArrayList<String> readQuerys(File file)
			throws FileNotFoundException {
		ArrayList<String> querys = new ArrayList<>();
		Scanner s = new Scanner(file);
		StringBuilder sb = new StringBuilder();

		while (s.hasNext()) {
			String row = s.nextLine();
			if (!row.startsWith("#")) {
				sb.append(row);
				if (row.endsWith(";")) {
					querys.add(sb.toString());
					sb = new StringBuilder();
				}
			}
		}
		s.close();
		return querys;
	}

}
