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
				log.info("3) EXIT \n==> ");

				// Take game choice from user
				String userChoice = bReader.readLine();

				switch (userChoice) {
				// Number Game
				case "1":
					NumberGame numberGame = new NumberGame();
					numberGame.startGame(username, password);
					break;

				// Question Answer game
				case "2":
					QuestionAnswerGame queAnsGame = new QuestionAnswerGame();
					queAnsGame.startGame(username, password);
					break;

				// Exit from game
				case "3":
					System.exit(0);
					isTerminate = true;
					log.info("Thank you!");
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
