package com.practiceproject.game.apiservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.practiceproject.game.data.GameDAO;
import com.practiceproject.game.entity.QuestionAnswerGameHistory;

public class QuestionAnswerGameAPI implements Game {
	BufferedReader bReader = new BufferedReader(new InputStreamReader(System.in));
	Logger log = (Logger) LogManager.getLogger(QuestionAnswerGameAPI.class);

	ArrayList<String> questions = new ArrayList<String>();
	ArrayList<String> answers = new ArrayList<String>();

	// This method have actual logic of the game
	public void playGame(String username) throws IOException, SQLException {

		GameDAO queAnsGame = new GameDAO();

		int rightAnswerCount = 0;

		// Generate random number and store unique random numbers to the randomNumList
		Random random = new Random();
		Set<Integer> randomNumList = new HashSet<Integer>();
		while (randomNumList.size() < 5) {
			randomNumList.add(random.nextInt(10));
		}

		// Display the question to user and store their result
		log.info(
				"Select right option of the following 5 questions:\nIf you give more than 3 right answer then you win else you lose the game\n");
		for (int randomNum : randomNumList) {

			// Take input from user
			boolean isValidInput = false;
			String userAnswer = "";
			do {
				log.info(questions.get(randomNum));
				System.out.print("==> ");
				userAnswer = bReader.readLine();

				isValidInput = (userAnswer.equals("1") || userAnswer.equals("2"));
				if (!isValidInput) {
					log.warn("Please enter valid option (1 or 2)");
				}
			} while (!isValidInput);

			// Validate the user answer
			if (answers.get(randomNum).equals(userAnswer)) {
				rightAnswerCount++;
				log.info("Right answer!\n");
			} else {
				log.info("Wrong answer!\n");
			}
		}

		if (rightAnswerCount >= 3) {
			queAnsGame.storeQueAnsGameResult(new QuestionAnswerGameHistory(username, Timestamp.from(Instant.now()),
					"WIN", rightAnswerCount, 5 - rightAnswerCount));
		} else {
			queAnsGame.storeQueAnsGameResult(new QuestionAnswerGameHistory(username, Timestamp.from(Instant.now()),
					"LOSE", rightAnswerCount, 5 - rightAnswerCount));
		}
	}

	// Show player history
	public void showPlayerHistory(String username, String gameName) throws SQLException {

		GameDAO queAnsGameDAO = new GameDAO();
		List<Object[]> list = queAnsGameDAO.readPlayerHistory(username, gameName);

		System.out.println(
				"-------------------------------------------------------------------------------------------------------");
		System.out.printf("| %-30s | %-20s | %-20s | %-20s | %n", "Time of round", "Lose/Win", "Right answer",
				"Wrong answer");
		System.out.println(
				"-------------------------------------------------------------------------------------------------------");
		for (Object[] obj : list) {
			System.out.printf("| %-30s | %-20s | %-20s | %-20s | %n", obj[0], obj[1], obj[2], obj[3]);

			System.out.println(
					"-------------------------------------------------------------------------------------------------------");
		}
	}

	// Clear player history
	public void clearPlayerHistory(String username, String gameName) throws SQLException {
		GameDAO queAnsGameDAO = new GameDAO();
		queAnsGameDAO.clearPlayerHistory(username, gameName);
	}

	// Fetch all questions and answers when the question-answer-game starts
	public void fetchAllQuestions() throws SQLException {

		GameDAO queAnsGameDAO = new GameDAO();
		ArrayList<ArrayList<String>> queAns = queAnsGameDAO.readQueAns();

		questions = queAns.get(0);
		answers = queAns.get(1);

	}

}
