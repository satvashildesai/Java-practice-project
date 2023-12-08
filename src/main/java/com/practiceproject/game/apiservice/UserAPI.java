package com.practiceproject.game.apiservice;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;

import com.practiceproject.game.data.UserDAO;
import com.practiceproject.game.entity.User;
import com.practiceproject.game.exception.FailedRegistrationException;
import com.practiceproject.game.exception.ProfileUpdationFailedException;

public class UserAPI {

	// Validation for the user age
	public static boolean isValidAge(int age) {
		if (age >= 12 && age <= 80) {
			return true;
		} else {
			return false;
		}
	}

	// Validation for the mobile number
	public static boolean isValidMobileNo(String mobNo) {
		if (mobNo.matches("(0/91)?[7-9][0-9]{9}")) {
			return true;
		} else {
			return false;
		}
	}

	// Calling data layer to store the user data in database
	public void storeUser(User user) throws FailedRegistrationException, SQLException {
		UserDAO ud = new UserDAO();
		ud.storeUserToDB(user);
	}

	// View user profile
	public void viewProfile(String username) throws SQLException {

		UserDAO user = new UserDAO();
		List<Object[]> list = user.readUserInfo(username);

		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------");
		System.out.printf("| %-20s | %-20s | %-20s | %-25s | %-25s | %n", "Name", "Age", "Mobile No.",
				"Profile create at", "Last update at");
		System.out.println(
				"------------------------------------------------------------------------------------------------------------------------------");
		for (Object[] obj : list) {
			System.out.printf("| %-20s | %-20s | %-20s | %-25s | %-25s | %n", obj[0], obj[1], obj[2], obj[3], obj[4]);

			System.out.println(
					"------------------------------------------------------------------------------------------------------------------------------");
		}
	}

	// Method use to store string data
	public void editField(String username, String fieldName, String fieldValue)
			throws ProfileUpdationFailedException, SQLException {

		Timestamp dateTime = Timestamp.from(Instant.now());

		UserDAO user = new UserDAO();
		user.updateProfileInDB(username, fieldName, fieldValue, dateTime);
	}

	// Delete user account
	public void deleteAccount(String username) throws SQLException {

		UserDAO user = new UserDAO();
		user.removeUserFromDB(username);
	}

}
