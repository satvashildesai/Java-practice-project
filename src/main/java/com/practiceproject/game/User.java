package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class User {
	BufferedReader bReader = App.bReader;
	Logger log = (Logger) LogManager.getLogger(User.class);

	Connection connection = ConnectDB.getDbConnection();
	Statement statement;
	ResultSet resultSet;

	String username;
	String password;

	User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	void dashbord() {
		boolean isTerminate = false;

		try {
			do {
				log.info("\n\nWhich game you want to play?");
				log.info("1) Number game");
				log.info("2) Questions Answers game");
				log.info("\nProfile:");
				log.info("3) Edit profile");
				log.info("4) Delete account");
				log.info("\n5) EXIT \n==> ");

				// Take game choice from user
				String userChoice = bReader.readLine();

				switch (userChoice) {
				// Number Game
				case "1":
					NumberGame numberGame = new NumberGame();
					numberGame.startGame(username, password);
					break;

				// Question Answer game1

				case "2":
					QuestionAnswerGame queAnsGame = new QuestionAnswerGame();
					queAnsGame.startGame(username, password);
					break;

				case "3":
					editProfile(username);
					break;

				case "4":
					deleteAccount(username);
					App.startApp();
					System.exit(0);
					break;

				// Exit from game
				case "5":
					log.info("Thank you!!");
					System.exit(0);
					break;

				default:
					log.info("Wrong input!");
					break;
				}
			} while (!isTerminate);
		} catch (IOException e) {
			log.error("Something wrong with I/O system!");
		} catch (Exception e) {
			log.error("Unwanted exception arise");
		}
	}

	// Edit profile
	void editProfile(String username) throws IOException {
		boolean isTerminate = false;
		do {
			log.info("\nSelect to edit:");
			log.info("1) Name");
			log.info("2) Age");
			log.info("3) Mobile number");
			log.info("4) Cancle\n==>");

			String userChoice = bReader.readLine();
			String queryToUpdateProfile = "";
			switch (userChoice) {
			case "1":
				String name = Input.getName();
				queryToUpdateProfile = "update users set name = '" + name + "' where username = '" + username + "';";
				updateProfileInDB(queryToUpdateProfile);
				break;

			case "2":
				int age = Input.getAge();
				queryToUpdateProfile = "update users set age = '" + age + "' where username = '" + username + "';";
				updateProfileInDB(queryToUpdateProfile);
				break;

			case "3":
				long mobileNumber = Input.getMobNumber();
				queryToUpdateProfile = "update users set mobile = '" + mobileNumber + "' where username = '" + username
						+ "';";
				updateProfileInDB(queryToUpdateProfile);
				break;

			case "4":
				isTerminate = true;
				break;

			default:
				break;
			}
		} while (!isTerminate);

	}

	// Delete account
	void deleteAccount(String username) {
		try {
			String queryDeleteFromUserHistory = "delete from user_history where username = '" + username + "';";
			String queryDeleteuser = "delete from users where username = '" + username + "';";

			statement = connection.createStatement();
			int status = statement.executeUpdate(queryDeleteFromUserHistory);
			if (status > 0) {
				statement = connection.createStatement();
				statement.executeUpdate(queryDeleteuser);
			}

		} catch (SQLException e) {
			log.error(e);
		}
	}

	void updateProfileInDB(String queryToUpdateProfile) {
		try {
			statement = connection.createStatement();
			statement.executeUpdate(queryToUpdateProfile);
		} catch (SQLException e) {
			log.error(e);
		}

	}

	// Display player's playing history according to game
	void playHistory(String gameId) {
		try {
			String query = "select * from user_history where game_id = '" + gameId + "' and username = '" + username
					+ "';";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			System.out.println(
					"------------------------------------------------------------------------------------------------");
			System.out.printf("| %-10s | %-23s | %-20s | %-17s | %-10s | %n", "User", "Game", "Rounds play", "Win",
					"Lose");
			System.out.println(
					"------------------------------------------------------------------------------------------------");

			while (resultSet.next()) {
				System.out.printf("| %-10s | %-23s | %-20s | %-17s | %-10s | %n", resultSet.getString(1),
						resultSet.getString(2), resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));

				System.out.println(
						"------------------------------------------------------------------------------------------------");
			}

		} catch (SQLException e) {
			log.error(e);
		}
	}
}
