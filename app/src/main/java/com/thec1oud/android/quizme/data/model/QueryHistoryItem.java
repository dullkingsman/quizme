package com.thec1oud.android.quizme.data.model;

public class QueryHistoryItem {
	String quizId;

	public QueryHistoryItem(String quiz) {
		this.quizId = quiz;
	}

	public String getQuizId() {
		return quizId;
	}

	public void setQuizId(String quizId) {
		this.quizId = quizId;
	}
}
