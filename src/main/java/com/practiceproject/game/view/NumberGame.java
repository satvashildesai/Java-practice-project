package com.practiceproject.game.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.practiceproject.game.App;
import com.practiceproject.game.apiservice.NumberGameAPI;

public class NumberGame {
	Logger log = (Logger) LogManager.getLogger(Login.class);
	BufferedReader bReader = App.bReader;

	String username;
	String roundStatus, playedChance;

	// This function control the game
	public void openGame(String username) {

		this.username = username;
		NumberGameAPI numGame = new NumberGameAPI();

		try {
			boolean isTerminate = false;

			do {
				log.info("\nPress:\n 1)Play\n 2)Game history\n 3)Clear history\n 4)Back \n==> ");
				String userCoice = bReader.readLine();

				switch (userCoice) {
				// Play game
				case "1":
					numGame.playGame(username);
					break;

				// Display player history
				case "2":
					numGame.showPlayerHistory(username, "NumberGameHistory");
					break;

				// Clear game history of the user
				case "3":
					numGame.clearPlayerHistory(username, "NumberGameHistory");
					break;

				// Back to dashbord
				case "4":
					isTerminate = true;
					break;

				default:
					log.info("Wrong input!");
					break;
				}

			} while (!isTerminate);

		} catch (IOException e) {
			log.error("Something worng with I/O system");
		} catch (SQLException e) {
			log.error("Faild to read data from database.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
