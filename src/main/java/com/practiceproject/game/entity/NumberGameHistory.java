package com.practiceproject.game.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "number_game_history")
public class NumberGameHistory implements Serializable {

	@Id
	private String username;
	@Id
	@Column(name = "played_at")
	private Timestamp playedAt;

	@Column(name = "round_status")
	private String roundStatus;
	@Column(name = "chance_used")
	private int chanceUsed;

	public NumberGameHistory() {
	}

	public NumberGameHistory(String username, Timestamp playedAt, String roundStatus, int chanceUsed) {
		this.username = username;
		this.playedAt = playedAt;
		this.roundStatus = roundStatus;
		this.chanceUsed = chanceUsed;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Timestamp getPlayedAt() {
		return playedAt;
	}

	public void setPlayedAt(Timestamp playedAt) {
		this.playedAt = playedAt;
	}

	public String getRoundStatus() {
		return roundStatus;
	}

	public void setRoundStatus(String roundStatus) {
		this.roundStatus = roundStatus;
	}

	public int getChanceUsed() {
		return chanceUsed;
	}

	public void setChanceUsed(int chanceUsed) {
		this.chanceUsed = chanceUsed;
	}
}
