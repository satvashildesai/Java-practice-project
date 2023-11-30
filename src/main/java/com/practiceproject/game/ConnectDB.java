package com.practiceproject.game;

import java.sql.Connection;
import java.sql.DriverManager;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class ConnectDB {
	static Logger log = (Logger) LogManager.getLogger(ConnectDB.class);
	static Connection connection = null;

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
	static Connection getDbConnection() {
		return connection;
	}
}
