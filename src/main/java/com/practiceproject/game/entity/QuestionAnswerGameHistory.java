package com.practiceproject.game.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "que_ans_game_history")
public class QuestionAnswerGameHistory implements Serializable {

	@Id
	private String username;

	@Id
	@Column(name = "played_at")
	private Timestamp playedAt;

	@Column(name = "round_status")
	private String roundStatus;
	@Column(name = "right_ans")
	private int rightAns;
	@Column(name = "wrong_ans")
	private int wrongAns;

	public QuestionAnswerGameHistory() {
	}

	public QuestionAnswerGameHistory(String username, Timestamp playedAt, String roundStatus, int rightAns,
			int wrongAns) {
		this.username = username;
		this.playedAt = playedAt;
		this.roundStatus = roundStatus;
		this.rightAns = rightAns;
		this.wrongAns = wrongAns;
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

	public int getRightAns() {
		return rightAns;
	}

	public void setRightAns(int rightAns) {
		this.rightAns = rightAns;
	}

	public int getWrongAns() {
		return wrongAns;
	}

	public void setWrongAns(int wrongAns) {
		this.wrongAns = wrongAns;
	}

}
