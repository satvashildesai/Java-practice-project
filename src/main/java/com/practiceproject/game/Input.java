package com.practiceproject.game;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class Input {
	static BufferedReader bReader = App.bReader;
	static Logger log = (Logger) LogManager.getLogger(Input.class);

	static String getUsername() throws IOException {
		log.info("Enter username: ");
		return bReader.readLine();
	}

	static String getPassword() throws IOException {
		log.info("Enter password: ");
		return bReader.readLine();
	}

	static String getName() throws IOException {
		log.info("Enter your name: ");
		return bReader.readLine();
	}

	static int getAge() throws IOException {
		while (true) {
			try {
				log.info("Enter your age: ");
				int tempAge = Integer.parseInt(bReader.readLine());

				if (tempAge >= 12 && tempAge <= 80) {
					return tempAge;
				} else {
					log.warn("Only users between age 12 to 80 can register.");
				}

			} catch (NumberFormatException e) {
				log.error("Please enter valid age.");
			}
		}
	}

	static long getMobNumber() throws IOException {
		while (true) {
			try {
				log.info("Enter your mobile number: ");
				String tempMobNum = bReader.readLine();

				if (tempMobNum.length() == 10) {
					return Long.parseLong(tempMobNum);
				} else {
					log.warn("Please enter valid mobile number.\n");
				}

			} catch (NumberFormatException e) {
				log.error("Please enter valid mobile number.");
			}
		}
	}

}
