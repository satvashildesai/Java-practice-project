package com.practiceproject.game.data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class ConnectDB {
	static Logger log = (Logger) LogManager.getLogger(ConnectDB.class);
	private static Connection connection = null;

	// Create only one database connection at the time of JVM loads the class
	static {
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/game", "postgres", "Admin@123");
			if (connection != null) {
				// log.info("connection successful");
			} else {
				log.error("connection fail");
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Return database connection
	public static Connection getDbConnection() {
		return connection;
	}

	public static void closeConnection() throws SQLException {
		connection.close();
	}
}
