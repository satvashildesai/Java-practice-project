package com.practiceproject.game.apiservice;

import java.io.IOException;
import java.sql.SQLException;

public interface Game {
	void playGame(String username) throws IOException, SQLException;

	void clearPlayerHistory(String username, String gameName) throws SQLException;

	void showPlayerHistory(String username, String gameName) throws SQLException;
}
