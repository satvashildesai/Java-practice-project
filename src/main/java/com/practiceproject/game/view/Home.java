package com.practiceproject.game.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.practiceproject.game.App;
import com.practiceproject.game.apiservice.UserAPI;
import com.practiceproject.game.data.ConnectDB;
import com.practiceproject.game.exception.ProfileUpdationFailedException;

public class Home {
	BufferedReader bReader = App.bReader;
	Logger log = (Logger) LogManager.getLogger(Home.class);

	String username, password;

	public Home(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public void dashbord() {
		boolean isTerminate = false;
		UserAPI user = new UserAPI();
		try {
			do {
				log.info("\n\nWhich game you want to play?");
				log.info("1) Number game");
				log.info("2) Questions Answers game");
				log.info("\nProfile:");
				log.info("3) View profile");
				log.info("4) Edit profile");
				log.info("5) Delete account");
				log.info("\n6) EXIT \n==> ");

				// Take game choice from user
				String userChoice = bReader.readLine();

				switch (userChoice) {
				// Number Game
				case "1":
					NumberGame numberGame = new NumberGame();
					numberGame.openGame(username);
					break;

				// Question Answer game
				case "2":
					QuestionAnswerGame queAnsGame = new QuestionAnswerGame();
					queAnsGame.openGame(username);
					break;

				// View profile
				case "3":
					user.viewProfile(username);
					break;

				// Edit profile
				case "4":
					editProfile(username);
					break;

				// Delete account
				case "5":
					user.deleteAccount(username);

					log.info("\nAccount deleted!");
					App.startApp();
					System.exit(0);
					break;

				// Exit from game
				case "6":
					log.info("Thank you!!");
					ConnectDB.closeConnection();
					System.exit(0);
					break;

				default:
					log.info("Wrong input!");
					break;
				}
			} while (!isTerminate);
		} catch (IOException e) {
			log.error("Something wrong with I/O system!");
		} catch (SQLException e) {
			log.error("Failed to read user profile.");
		} catch (ProfileUpdationFailedException e) {
			log.error(e);
		}
	}

	// Edit profile
	void editProfile(String username) throws IOException, SQLException, ProfileUpdationFailedException {

		UserAPI userAPI = new UserAPI();

		boolean isTerminate = false;
		do {
			log.info("\nSelect to edit:");
			log.info("1) Name");
			log.info("2) Age");
			log.info("3) Mobile number");
			log.info("4) Cancel");

			String userChoice = bReader.readLine();
			switch (userChoice) {
			case "1":

				log.info("Enter name: ");
				String name = bReader.readLine();
				userAPI.editField(username, "name", name);
				break;

			case "2":

				int age = 0;
				do {
					try {
						log.info("Enter age: ");
						age = Integer.parseInt(bReader.readLine());

						if (UserAPI.isValidAge(age)) {
							break;
						} else {
							log.info("Only users between 12 to 80 are allowed.");
						}
					} catch (NumberFormatException e) {
						log.info("Please enter valid age.");
					}

				} while (true);
				userAPI.editField(username, "age", Integer.toString(age));
				break;

			case "3":

				String mobileNumber = "";
				do {
					log.info("Enter mobile number: ");
					mobileNumber = bReader.readLine();

					if (UserAPI.isValidMobileNo(mobileNumber)) {
						break;
					} else {
						log.info("Please enter valid mobile number.");
					}
				} while (true);
				userAPI.editField(username, "mobile", mobileNumber);
				break;

			case "4":
				isTerminate = true;
				break;

			default:
				break;
			}
		} while (!isTerminate);
	}
}
