package com.thec1oud.android.quizme.data.model;

import com.thec1oud.android.quizme.data.api.model.common.Base;

import java.util.ArrayList;
import java.util.List;

public class Quiz {
	protected String id;
	protected boolean attempted;
	protected boolean completed;
	protected List<Question> questions;

	public Quiz(
		String id,
		List<Question> questions
	) {
		this.id = id;
		this.attempted = false;
		this.completed = false;
		this.questions = questions;
	}

	public Quiz(
		String id,
		List<Question> questions,
		boolean attempted,
		boolean completed
	) {
		this.id = id;
		this.attempted = attempted;
		this.completed = completed;
		this.questions = questions;
	}

	public String getId() {
		return id;
	}

	public boolean isAttempted() {
		return attempted;
	}

	public boolean isCompleted() {
		return completed;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setAttempted(boolean attempted) {
		this.attempted = attempted;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

	public void setQuestions(List<Question> questions) {
		this.questions = questions;
	}

	public static Quiz fromAPIModel(Base.Quiz quiz) {
		List<Question> questions = new ArrayList<>();

		for(Base.Question question: quiz.questions)
			questions.add(Question.fromAPIModel(question));

		return new Quiz(quiz.id, questions);
	}
}
