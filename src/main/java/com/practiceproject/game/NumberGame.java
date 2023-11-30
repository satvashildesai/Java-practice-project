package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class NumberGame {
	Logger log = (Logger) LogManager.getLogger(Login.class);
	BufferedReader bReader = App.bReader;
	Connection connection;
	Statement statement;

	// This function control the game
	void startGame(String username, String password) {
		BufferedReader bReader = App.bReader;
		Logger log = App.log;
		connection = ConnectDB.getDbConnection();

		try {
			boolean isTerminate = false;

			do {
				log.info("\nPress:\n 1)Play\n 2)Game history\n 3)EXIT\n ==> ");
				String userCoice = bReader.readLine();

				switch (userCoice) {
				// Play game
				case "1":
					playGame();
					break;

				// Display player history
				case "2":
					User user = new User(username, password);
					user.playHistory("number_game");
					break;

				// Exit game
				case "3":
					isTerminate = true;
					break;

				default:
					log.info("Wrong input!");
					break;
				}

			} while (!isTerminate);

		} catch (IOException e) {
			log.error("Something worng with I/O system");
		} catch (Exception e) {
			log.error("Exception arise");
		}
	}

	// This method have actual logic of the game
	void playGame() {

		// Generate random number
		Random random = new Random();
		int randomNum = random.nextInt(10) + 1;

		int counter = 5;
		while (true) {
			try {
				// Exit when player use all 5 chances
				if (counter == 0) {
					storeResult(0);
					log.info("\nYOU LOSE THE GAME!!!");
					break;
				}
				log.info("\nYou have " + counter + " chance");

				// Take number from user
				log.info("Enter number between 1 to 10 ==> ");
				int userNum = Integer.parseInt(bReader.readLine());

				// Check the user number and random are equal or not
				if (randomNum > userNum) {
					log.info("Your number is too small!");
				} else if (randomNum < userNum) {
					log.info("Your number is too big!");
				} else {
					storeResult(1);
					log.info("\nYOU WIN THE GAME!!!");
					break;
				}
				counter--;
			} catch (NumberFormatException e) {
				log.error("Please enter valid number between (1 to 10).");
			} catch (IOException e) {
				log.error("Something wrong with I/O system");
			} catch (Exception e) {
				log.error(e);
			}
		}

	}

	// Store win, lose status to database
	void storeResult(int gameStatus) {
		try {
			String query = "";

			if (gameStatus == 1) {
				query = "update user_history set rounds = rounds+1, win= win+1, lose=lose+0;";
			} else {
				query = "update user_history set rounds = rounds +1, win= win+0, lose=lose+1;";
			}
			statement = connection.createStatement();
			statement.executeUpdate(query);

		} catch (SQLException e) {
			log.error(e);
		}
	}
}
