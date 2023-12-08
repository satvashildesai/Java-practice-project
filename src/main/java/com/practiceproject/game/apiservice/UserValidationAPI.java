package com.practiceproject.game.apiservice;

import java.sql.SQLException;

import com.practiceproject.game.data.UserValidationDAO;
import com.practiceproject.game.exception.UserNotFoundException;
import com.practiceproject.game.view.Home;

public class UserValidationAPI {
	public void login(String username, String password) throws UserNotFoundException, SQLException {
		UserValidationDAO userDAO = new UserValidationDAO();

		if (userDAO.isUserValid(username, password)) {
			Home user = new Home(username, password);
			user.dashbord();
		} else {
			throw new UserNotFoundException("May user not registered yet, please check user credentials.");
		}
	}
}
