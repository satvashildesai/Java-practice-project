package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Login {
	static BufferedReader bReader = App.bReader;
	static Logger log = (Logger) LogManager.getLogger(Login.class);

	void loginToUser() {
		try {
			log.info("Enter username: ");
			String username = bReader.readLine();

			log.info("Enter password: ");
			String password = bReader.readLine();

			if (isUserValid(username, password)) {
				User user = new User(username, password);
				user.dashbord();
			} else {
				log.warn("Wrong user credentials!");
			}
		} catch (IOException e) {
			log.error(e);
		}

	}

	// Check if the user is valid or not
	boolean isUserValid(String username, String password) {
		Connection connection = ConnectDB.getDbConnection();

		try {
			String query = "select username, password from users where username = '" + username + "' and password = '"
					+ password + "';";

			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery(query);

			if (resultSet.next()) {
				return true;
			}

		} catch (SQLException e) {
			log.error(e);
		}

		return false;
	}
}
