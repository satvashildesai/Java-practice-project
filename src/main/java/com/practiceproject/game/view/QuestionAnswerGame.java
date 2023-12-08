package com.practiceproject.game.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.practiceproject.game.App;
import com.practiceproject.game.apiservice.QuestionAnswerGameAPI;

public class QuestionAnswerGame {

	BufferedReader bReader = App.bReader;
	Logger log = (Logger) LogManager.getLogger(QuestionAnswerGame.class);

	String username;

	// This function control the game
	public void openGame(String username) {
		this.username = username;
		QuestionAnswerGameAPI queAnsGame = new QuestionAnswerGameAPI();

		try {
			queAnsGame.fetchAllQuestions();
			boolean isTerminate = false;

			do {
				log.info("\nPress:\n 1)Play\n 2)Player history\n 3)Clear history\n 4)Back \n==> ");
				String userCoice = bReader.readLine();

				switch (userCoice) {
				// Play game
				case "1":
					queAnsGame.playGame(username);
					break;

				// Display game history of player
				case "2":
					queAnsGame.showPlayerHistory(username, "QuestionAnswerGameHistory");
					break;

				case "3":
					queAnsGame.clearPlayerHistory(username, "QuestionAnswerGameHistory");
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
			log.error(e);
		}
	}

}
