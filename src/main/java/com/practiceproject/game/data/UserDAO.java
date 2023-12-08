package com.practiceproject.game.data;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.practiceproject.game.entity.User;
import com.practiceproject.game.exception.FailedRegistrationException;
import com.practiceproject.game.exception.ProfileUpdationFailedException;

public class UserDAO {
	static Logger log = (Logger) LogManager.getLogger(UserDAO.class);

	private SessionFactory sfUser = ConnectHDB.getSessionFactory("User");
	private SessionFactory sfNumGame = ConnectHDB.getSessionFactory("NumberGameHistory");
	private SessionFactory sfQueAnsGame = ConnectHDB.getSessionFactory("QuestionAnswerGameHistory");

	// Store user data to database
	public void storeUserToDB(User user) throws FailedRegistrationException, SQLException {

		Session session = sfUser.openSession();
		Transaction tx = session.beginTransaction();

		session.save(user);

		tx.commit();
		session.close();
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// View user profile
	public List<Object[]> readUserInfo(String username) throws SQLException {
		String query = "select name, age, mobileNumber, createdAt, updatedAt from User where username = :username";

		Session session = sfUser.openSession();
		Transaction tx = session.beginTransaction();

		Query q = session.createQuery(query);
		q.setParameter("username", username);

		List<Object[]> list = q.list();

		tx.commit();
		session.close();

		return list;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// update user profile as per their commands
	public void updateProfileInDB(String username, String fieldName, String fieldValue, Timestamp dateTime)
			throws ProfileUpdationFailedException, SQLException {

		Session session = sfUser.openSession();
		Transaction tx = session.beginTransaction();

		String query = "update User set " + fieldName + " = '" + fieldValue
				+ "', updated_at = :dateTime where username = :username";
		Query q = session.createQuery(query);

		q.setParameter("dateTime", dateTime);
		q.setParameter("username", username);

		int status = q.executeUpdate();
		if (status <= 0) {
			throw new ProfileUpdationFailedException("Failed to update user profile, please go back and try again.");
		}
		tx.commit();
		session.close();
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Delete user account
	public void removeUserFromDB(String username) throws SQLException {

		String deleteNumberGameHistory = "delete from NumberGameHistory where username = :username";
		String deleteQueAnsHistory = "delete from QuestionAnswerGameHistory where username = :username";
		String deleteUser = "delete from User where username = :username";

//		Delete user history from number game
		Session session1 = sfNumGame.openSession();
		Transaction tx = session1.beginTransaction();

		Query q1 = session1.createQuery(deleteNumberGameHistory);
		q1.setParameter("username", username);
		q1.executeUpdate();

		tx.commit();
		session1.close();

//		Delete user histroy from question answer game
		Session session2 = sfQueAnsGame.openSession();
		tx = session2.beginTransaction();

		Query q2 = session2.createQuery(deleteQueAnsHistory);
		q2.setParameter("username", username);
		q2.executeUpdate();

		tx.commit();
		session2.close();

//		Remove user from database
		Session session3 = sfUser.openSession();
		tx = session3.beginTransaction();

		Query q3 = session3.createQuery(deleteUser);
		q3.setParameter("username", username);
		q3.executeUpdate();

		tx.commit();
		session3.close();

	}

///////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
