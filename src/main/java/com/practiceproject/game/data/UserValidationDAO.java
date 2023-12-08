package com.practiceproject.game.data;

import java.sql.SQLException;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.practiceproject.game.exception.UserNotFoundException;

public class UserValidationDAO {

	private SessionFactory sf = ConnectHDB.getSessionFactory("User");

	// Check if the user is valid or not
	public boolean isUserValid(String username, String password) throws UserNotFoundException, SQLException {

		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		String query = "select username, password from User where username = :username and password = :password";
		Query q = session.createQuery(query);
		q.setParameter("username", username);
		q.setParameter("password", password);

		List<Object[]> list = q.list();

		tx.commit();
		session.close();

		if (!list.isEmpty()) {
			return true;
		} else {
			throw new UserNotFoundException("\nUser is not register yet, please check user credentials once again.");
		}
	}
}
