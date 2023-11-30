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
			log.info("Enter your name: ");
			name = bReader.readLine();

			// Age of user
			while (true) {
				try {
					log.info("Enter your age: ");
					int tempAge = Integer.parseInt(bReader.readLine());

					if (tempAge >= 12 && tempAge <= 80) {
						this.age = tempAge;
						break;
					} else {
						log.warn("Only users between age 12 to 80 can register.");
					}

				} catch (IOException e) {
					log.error(e);
				} catch (NumberFormatException e) {
					log.error("Please enter valid age.");
				}
			}

			// Mobile number of user
			while (true) {
				try {
					log.info("Enter your mobile number: ");
					String tempMobNum = bReader.readLine();

					if (tempMobNum.length() == 10) {
						this.mobileNumber = Long.parseLong(tempMobNum);
						break;
					} else {
						log.warn("Please enter valid mobile number.\n");
					}

				} catch (IOException e) {
					log.error(e);
				} catch (NumberFormatException e) {
					log.error("Please enter valid mobile number.");
				}
			}

			// username of the user
			log.info("Enter username: ");
			username = bReader.readLine();

			// password of the user
			log.info("Enter password: ");
			password = bReader.readLine();

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
				log.info("Data store succefully!");
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
