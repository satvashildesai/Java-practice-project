package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.apache.logging.log4j.core.Logger;

public class QuestionAnswerGame {

	static BufferedReader bReader = App.bReader;
	static Logger log = App.log;
	static ArrayList<String> questions = new ArrayList<String>();
	static ArrayList<String> answers = new ArrayList<String>();
	static ArrayList<Map<Integer, String>> userHistory = new ArrayList<Map<Integer, String>>();

	// This function control the game
	static void startGame() {

		try {
			boolean isTerminate = false;

			do {
				log.info("\nPress:\n 1)Play\n 2)Player history\n 3)EXIT\n ==> ");
				String userCoice = bReader.readLine();

				switch (userCoice) {
				case "1":
					playGame();
					break;

				case "2":
					playerHistory();
					break;

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
	static void playGame() throws IOException {

		// Store the result of current round
		Map<Integer, String> result = new HashMap<Integer, String>();

		BufferedReader bFileReader1 = null, bFileReader2 = null;

		try {

			// Read questions and answers from the file and store to respective arraylist
			bFileReader1 = new BufferedReader(new FileReader("E:/game/src/main/resources/questions.txt"));
			bFileReader2 = new BufferedReader(new FileReader("E:/game/src/main/resources/answers.txt"));

			String line1 = "", line2 = "";
			while ((line1 = bFileReader1.readLine()) != null && (line2 = bFileReader2.readLine()) != null) {
				questions.add(line1);
				answers.add(line2);
			}

			// Generate random number and store unique random numbers to the randomNumList
			Random random = new Random();
			Set<Integer> randomNumList = new HashSet<Integer>();
			while (randomNumList.size() < 3) {
				randomNumList.add(random.nextInt(10));
			}

			// Display the question to user and store their result
			log.info("Select right option for the following questions:\n");
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
					log.info("Right answer!\n");
					result.put(randomNum, "Right answer " + "(Your Choice: " + userAnswer + ")");
				} else {
					log.info("Wrong answer!\n");
					result.put(randomNum, "Wrong answer " + "(Your Choice: " + userAnswer + ")");
				}
			}

			// Store result of each round to userHistory
			userHistory.add(result);

		} finally {
			// Close the file connections
			bFileReader1.close();
			bFileReader2.close();
		}

	}

	// This method display the player history of each round
	static void playerHistory() {
		int round = 0;
		for (Map<Integer, String> map : userHistory) {
			log.info("\nRound: " + (++round));
			map.forEach((k, v) -> log.info("Question: " + questions.get(k) + "\n  Result: " + v + "\n"));
		}
	}
}
