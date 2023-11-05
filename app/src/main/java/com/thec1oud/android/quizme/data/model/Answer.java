package com.thec1oud.android.quizme.data.model;

public class Answer {
	protected int questionId;
	protected String answerId;

	public Answer(
		int questionId,
		String answerId
	) {
		this.questionId = questionId;
		this.answerId = answerId;
	}

	public int getQuestionId() {
		return questionId;
	}

	public String getAnswerId() {
		return answerId;
	}

	public void setQuestionId(int questionId) {
		this.questionId = questionId;
	}

	public void setAnswerId(String answerId) {
		this.answerId = answerId;
	}
}

