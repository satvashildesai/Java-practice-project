package com.practiceproject.game.data;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.practiceproject.game.entity.GameList;
import com.practiceproject.game.entity.NumberGameHistory;
import com.practiceproject.game.entity.QuestionAnswerGameHistory;
import com.practiceproject.game.entity.Questions;
import com.practiceproject.game.entity.User;

public class ConnectHDB {
	static private SessionFactory sf1, sf2, sf3, sf4, sf5;
	static {
		Configuration con1 = new Configuration().configure().addAnnotatedClass(User.class);
		Configuration con2 = new Configuration().configure().addAnnotatedClass(Questions.class);
		Configuration con3 = new Configuration().configure().addAnnotatedClass(GameList.class);
		Configuration con4 = new Configuration().configure().addAnnotatedClass(NumberGameHistory.class);
		Configuration con5 = new Configuration().configure().addAnnotatedClass(QuestionAnswerGameHistory.class);

		sf1 = con1.buildSessionFactory();
		sf2 = con2.buildSessionFactory();
		sf3 = con3.buildSessionFactory();
		sf4 = con4.buildSessionFactory();
		sf5 = con5.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory(String className) {
		if (className.equals("User")) {
			return sf1;
		} else if (className.equals("Questions")) {
			return sf2;
		} else if (className.equals("GameList")) {
			return sf3;
		} else if (className.equals("NumberGameHistory")) {
			return sf4;
		} else if (className.equals("QuestionAnswerGameHistory")) {
			return sf5;
		}
		return null;
	}

	public static void closeSessionFactory() {
		sf1.close();
		sf2.close();
		sf3.close();
		sf4.close();
		sf5.close();
	}
}
