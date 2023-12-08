package com.practiceproject.game.data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.practiceproject.game.entity.NumberGameHistory;
import com.practiceproject.game.entity.QuestionAnswerGameHistory;

public class GameDAO {

	SessionFactory sfNumGame = ConnectHDB.getSessionFactory("NumberGameHistory");
	SessionFactory sfQueAnsGame = ConnectHDB.getSessionFactory("QuestionAnswerGameHistory");

	// Show player history
	public List<Object[]> readPlayerHistory(String username, String gameName) throws SQLException {

		if (gameName.equals("NumberGameHistory")) {
			String query = "select playedAt, roundStatus, chanceUsed from NumberGameHistory where username = :username";

			Session session = sfNumGame.openSession();
			Transaction tx = session.beginTransaction();

			Query q = session.createQuery(query);
			q.setParameter("username", username);

			List<Object[]> list = q.list();

			tx.commit();
			session.close();

			return list;
		} else if (gameName.equals("QuestionAnswerGameHistory")) {
			String query = "select playedAt, roundStatus, rightAns, wrongAns from QuestionAnswerGameHistory where username = :username";

			Session session = sfQueAnsGame.openSession();
			Transaction tx = session.beginTransaction();

			Query q = session.createQuery(query);
			q.setParameter("username", username);

			List<Object[]> list = q.list();

			tx.commit();
			session.close();
			return list;
		}

		return null;
	}

	// Clear player history
	public void clearPlayerHistory(String username, String gameName) throws SQLException {

		if (gameName.equals("NumberGameHistory")) {
			String query = "delete from NumberGameHistory where username = :username";

			Session session = sfNumGame.openSession();
			Transaction tx = session.beginTransaction();

			Query q = session.createQuery(query);
			q.setParameter("username", username);
			q.executeUpdate();

			tx.commit();
			session.close();

		} else if (gameName.equals("QuestionAnswerGameHistory")) {
			String query = "delete from QuestionAnswerGameHistory where username = :username";

			Session session = sfQueAnsGame.openSession();
			Transaction tx = session.beginTransaction();

			Query q = session.createQuery(query);
			q.setParameter("username", username);
			q.executeUpdate();

			tx.commit();
			session.close();
		}
	}

	// Read all questions and answer from database
	public ArrayList<ArrayList<String>> readQueAns() throws SQLException {
		ArrayList<String> questions = new ArrayList<String>();
		ArrayList<String> answers = new ArrayList<String>();
		ArrayList<ArrayList<String>> queAns = new ArrayList<>();

		String query = "select question, answer from Questions";

		SessionFactory sf = ConnectHDB.getSessionFactory("Questions");
		Session session = sf.openSession();
		Transaction tx = session.beginTransaction();

		Query q = session.createQuery(query);
		List<Object[]> list = q.list();

		tx.commit();
		session.close();

		for (Object[] obj : list) {
			questions.add(obj[0].toString());
			answers.add(obj[1].toString());
		}
		queAns.add(questions);
		queAns.add(answers);

		return queAns;
	}

//////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Store win, lose status to database
	public void storeNumGameResult(NumberGameHistory numGameUserHistoryObj) {

		Session session = sfNumGame.openSession();
		Transaction tx = session.beginTransaction();

		session.save(numGameUserHistoryObj);

		tx.commit();
		session.close();
	}

/////////////////////////////////////////////////////////////////////////////////////////////////////////

	// Store the win and lose status to database
	public void storeQueAnsGameResult(QuestionAnswerGameHistory queAnsGameUserHistoryObj) throws SQLException {

		Session session = sfQueAnsGame.openSession();
		Transaction tx = session.beginTransaction();

		session.save(queAnsGameUserHistoryObj);

		tx.commit();
		session.close();
	}
}
