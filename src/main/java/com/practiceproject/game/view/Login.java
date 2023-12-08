package com.practiceproject.game.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.practiceproject.game.App;
import com.practiceproject.game.apiservice.UserValidationAPI;
import com.practiceproject.game.exception.UserNotFoundException;

public class Login {
	static BufferedReader bReader = App.bReader;
	static Logger log = (Logger) LogManager.getLogger(Login.class);

	public void loginToUser() {
		try {
			log.info("Enter username: ");
			String username = bReader.readLine();

			log.info("Enter password: ");
			String password = bReader.readLine();

			UserValidationAPI user = new UserValidationAPI();
			user.login(username, password);

		} catch (IOException e) {
			log.error("Somethis wrong with I/O system.");
		} catch (UserNotFoundException e) {
			log.error(e);
		} catch (SQLException e) {
			log.error(e);
		}
	}
}
