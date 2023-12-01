package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Register {
	BufferedReader bReader = App.bReader;
	Logger log = (Logger) LogManager.getLogger(Register.class);

	Connection connection = ConnectDB.getDbConnection();
	Statement statement;
	ResultSet resultSet;

	String name;
	int age;
	long mobileNumber;
	String username;
	String password;

	void registerUser() {

		try {
			// Name of user
			this.name = Input.getName();

			// Age of user
			this.age = Input.getAge();

			// Mobile number of user
			this.mobileNumber = Input.getMobNumber();

			// username of the user
			this.username = Input.getUsername();

			// password of the user
			this.password = Input.getPassword();

			// Store user personal information to database
			storeUserData();

		} catch (IOException e) {
			log.error(e);
		}
	}

	// Store user data to database
	void storeUserData() {
		String query = "insert into users values('" + name + "', " + age + ", " + mobileNumber + ", '" + username
				+ "', '" + password + "');";

		try {
			statement = connection.createStatement();
			int status = statement.executeUpdate(query);

			if (status > 0) {
				enrollAllGame();
				log.info("Registration succefull!");
			} else {
				log.error("Failed to store data!");
			}
		} catch (SQLException e) {
			log.error(e);
		}
	}

	// Enroll the user to all the game automatically when he register
	void enrollAllGame() {
		try {
			String gameListQuery = "select game_id from game_list;";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(gameListQuery);

			while (resultSet.next()) {
				String gameHistoryQuery = "insert into user_history values('" + username + "', '"
						+ resultSet.getString(1) + "',0,0,0)";
				statement = connection.createStatement();
				statement.executeUpdate(gameHistoryQuery);
			}

		} catch (SQLException e) {
			log.error(e);
		}

	}
}
