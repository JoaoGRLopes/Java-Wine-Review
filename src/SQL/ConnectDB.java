package src.SQL;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectDB {

	public static Connection get() {
		Connection connection = null;

		try {
			Class.forName("org.sqlite.JDBC");

			connection = DriverManager.getConnection("jdbc:sqlite:wine-data-small.sqlite");

			System.out.println("Connection Established");
		} catch (Exception e) {
			System.err.println(e.getClass().getName() + ": " + e.getMessage());
			System.exit(0);
		}

		return connection;
	}

	public static void main(String[] args) {
		get();
	}
}
