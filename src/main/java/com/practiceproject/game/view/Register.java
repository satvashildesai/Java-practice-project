package com.practiceproject.game.view;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.practiceproject.game.App;
import com.practiceproject.game.apiservice.UserAPI;
import com.practiceproject.game.entity.User;
import com.practiceproject.game.exception.FailedRegistrationException;

public class Register {
	BufferedReader bReader = App.bReader;
	Logger log = (Logger) LogManager.getLogger(Register.class);

	public void registerUser() {
		try {
			// Create new user
			User user = new User();
			UserAPI userAPI = new UserAPI();

			// Name of user
			log.info("Enter name: ");
			String name = bReader.readLine();
			user.setName(name);

			// Age of user
			int age = 0;
			do {
				try {
					log.info("Enter age: ");
					age = Integer.parseInt(bReader.readLine());

					if (UserAPI.isValidAge(age)) {
						user.setAge(age);
						break;
					} else {
						log.info("Only users between 12 to 80 are allowed.");
					}
				} catch (NumberFormatException e) {
					log.info("Please enter valid age.");
				}

			} while (true);

			// Mobile number of user
			String mobileNumber = "";
			do {
				log.info("Enter mobile number: ");
				mobileNumber = bReader.readLine();
				if (UserAPI.isValidMobileNo(mobileNumber)) {
					user.setMobileNumber(mobileNumber);
					break;
				} else {
					log.info("Please enter valid mobile number.");
				}
			} while (true);

			// username of the user
			log.info("Enter username: ");
			String username = bReader.readLine();
			user.setUsername(username);

			// password of the user
			log.info("Enter password: ");
			String password = bReader.readLine();
			user.setPassword(password);

			Timestamp timestamp = Timestamp.from(Instant.now());
			user.setCreatedAt(timestamp);
			user.setUpdatedAt(timestamp);

			// Store user personal information to database
			userAPI.storeUser(user);
		} catch (IOException e) {
			log.error("Something wrong with I/O system");
		} catch (FailedRegistrationException e) {
			log.error(e);
		} catch (SQLException e) {
			log.error(e);
		}
	}

}
