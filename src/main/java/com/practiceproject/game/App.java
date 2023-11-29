package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.apache.logging.log4j.core.Logger;
import org.apache.logging.log4j.LogManager;

public class App {
	static BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));

	static Logger log = (Logger) LogManager.getLogger(App.class);

	public static void main(String[] args) {

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
					NumberGame.startGame();
					break;

				// Question Answer game
				case "2":
					QuestionAnswerGame.startGame();
					break;

				// Exit from game
				case "3":
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
}
