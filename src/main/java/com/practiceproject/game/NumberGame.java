package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Random;
import org.apache.logging.log4j.core.Logger;

public class NumberGame {

	// This function control the game
	static void startGame() {

		BufferedReader bReader = App.bReader;
		Logger log = App.log;

		try {
			boolean isTerminate = false;

			do {
				log.info("\nPress:\n 1)Play\n 2)EXIT\n ==> ");
				String userCoice = bReader.readLine();

				switch (userCoice) {
				// Play game
				case "1":
					playGame();
					break;

				// Exit game
				case "2":
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
	static void playGame() {
		BufferedReader bReader = App.bReader;
		Logger log = App.log;

		// Generate random number
		Random random = new Random();
		int randomNum = random.nextInt(10) + 1;
		while (true) {
			try {

				// Take number from user
				log.info("\nEnter number between 1 to 10 ==> ");
				int userNum = Integer.parseInt(bReader.readLine());

				// Check the user number and random are equal or not
				if (randomNum > userNum) {
					log.info("Your number is too small!");
				} else if (randomNum < userNum) {
					log.info("Your number is too big!");
				} else {
					log.info("\nYOU WIN THE GAME!!!");
					break;
				}

			} catch (NumberFormatException e) {
				log.info("Please enter valid number between (1 to 10).");
			} catch (IOException e) {
				log.error("Something wrong with I/O system");
			} catch (Exception e) {
				log.error("Exception arise");
			}
		}

	}
}
