package com.practiceproject.game.apiservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Random;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.practiceproject.game.data.GameDAO;
import com.practiceproject.game.entity.NumberGameHistory;

public class NumberGameAPI implements Game {
	BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
	Logger log = (Logger) LogManager.getLogger(NumberGameAPI.class);

	// This method have actual logic of the game
	public void playGame(String username) throws IOException, SQLException {

		// Generate random number
		Random random = new Random();
		int randomNum = random.nextInt(10) + 1;

		GameDAO numGameDAO = new GameDAO();

		int counter = 5;
		while (true) {
			try {
				// Exit when player use all 5 chances
				if (counter == 0) {
					numGameDAO.storeNumGameResult(
							new NumberGameHistory(username, Timestamp.from(Instant.now()), "LOSE", 5));
					log.info("\nYOU LOSE THE GAME!!!");
					break;
				}
				log.info("\nYou have " + counter + " chance to guess the right number between 1 to 10");

				// Take number from user
				log.info("Enter number between 1 to 10 ==> ");
				int userNum = Integer.parseInt(bReader.readLine());

				// Check the user number and random are equal or not
				if (randomNum > userNum) {
					log.info("Your number is too small!");
				} else if (randomNum < userNum) {
					log.info("Your number is too big!");
				} else {
					numGameDAO.storeNumGameResult(
							new NumberGameHistory(username, Timestamp.from(Instant.now()), "WIN", 6 - counter));
					log.info("\nYOU WIN THE GAME!!!");
					break;
				}
				counter--;
			} catch (NumberFormatException e) {
				log.error("Please enter valid number between (1 to 10).");
			}
		}
	}

	// Show player history
	public void showPlayerHistory(String username, String gameName) throws SQLException {

		GameDAO numberGameDAO = new GameDAO();
		List<Object[]> list = numberGameDAO.readPlayerHistory(username, gameName);

		System.out.println("--------------------------------------------------------------------------------");
		System.out.printf("| %-30s | %-20s | %-20s | %n", "Time of round", "Lose/Win", "Chance used");
		System.out.println("--------------------------------------------------------------------------------");

		for (Object[] obj : list) {
			System.out.printf("| %-30s | %-20s | %-20s | %n", obj[0], obj[1], obj[2]);

			System.out.println("--------------------------------------------------------------------------------");
		}
	}

	// Clear player history
	public void clearPlayerHistory(String username, String gameName) throws SQLException {

		GameDAO numberGameDAO = new GameDAO();
		numberGameDAO.clearPlayerHistory(username, gameName);
	}
}
