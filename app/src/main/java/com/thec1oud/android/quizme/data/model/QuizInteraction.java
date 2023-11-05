package com.thec1oud.android.quizme.data.model;

import java.util.List;

public class QuizInteraction {
	int timestamp;
	String quizId;
	List<Answer> answers;

	public QuizInteraction(int timestamp, String quizId, List<Answer> answers) {
		this.timestamp = timestamp;
		this.quizId = quizId;
		this.answers = answers;
	}

	public int getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(int timestamp) {
		this.timestamp = timestamp;
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}

	public List<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(List<Answer> answers) {
		this.answers = answers;
	}
}
