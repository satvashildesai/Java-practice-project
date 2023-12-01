package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.core.Logger;

public class QuestionAnswerGame {

	BufferedReader bReader = App.bReader;
	Logger log = App.log;

	Connection connection = ConnectDB.getDbConnection();
	Statement statement;
	ResultSet resultSet;

	ArrayList<String> questions = new ArrayList<String>();
	ArrayList<String> answers = new ArrayList<String>();

	String username;

	// This function control the game
	void startGame(String username, String password) {
		this.username = username;
		try {
			// Fetch all questions when the question-answer-game starts
			fetchAllQuestions();

			boolean isTerminate = false;

			do {
				log.info("\nPress:\n 1)Play\n 2)Player history\n 3)Back\n ==> ");
				String userCoice = bReader.readLine();

				switch (userCoice) {
				// Play game
				case "1":
					playGame();
					break;

				// Display game history of player
				case "2":
					User user = new User(username, password);
					user.playHistory("question_answer_game");
					break;

				// Back to dashbord
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
		}
	}

	// This method have actual logic of the game
	void playGame() throws IOException {

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
		storeResult(rightAnswerCount);
	}

	// Store the win and lose status to database
	void storeResult(int rightAnswer) {
		try {
			String query = "";
			statement = connection.createStatement();
			if (rightAnswer >= 3) {
				query = "update user_history set rounds = rounds+1, win= win+1, lose=lose+0 where username = '"
						+ username + "' and game_id = 'question_answer_game' ;";
				statement.executeUpdate(query);
			} else {
				query = "update user_history set rounds = rounds+1, win= win+0, lose=lose+1 where username = '"
						+ username + "' and game_id = 'question_answer_game' ;";
				statement.executeUpdate(query);
			}

		} catch (SQLException e) {
			log.error(e);
		}
	}

	// Fetch all questions
	void fetchAllQuestions() {
		try {
			String query = "select question, answer from questions;";
			statement = connection.createStatement();
			resultSet = statement.executeQuery(query);

			while (resultSet.next()) {
				questions.add(resultSet.getString(1));
				answers.add(resultSet.getString(2));
			}

		} catch (SQLException e) {
			log.error(e);
		}

	}
}
