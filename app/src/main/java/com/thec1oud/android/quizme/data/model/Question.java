package com.thec1oud.android.quizme.data.model;

import com.thec1oud.android.quizme.data.api.model.common.Base;

import java.util.ArrayList;
import java.util.List;

public class Question {
	protected int id;
	protected String prompt;
	protected String answer;
	protected List<Choice> choices;

	public Question(int id, String prompt, String answer, List<Choice> choices) {
		this.id = id;
		this.prompt = prompt;
		this.answer = answer;
		this.choices = choices;
	}

	public int getId() {
		return id;
	}

	public String getPrompt() {
		return prompt;
	}

	public String getAnswer() {
		return answer;
	}

	public List<Choice> getChoices() {
		return choices;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPrompt(String prompt) {
		this.prompt = prompt;
	}

	public void setAnswer(String prompt) {
		this.answer = prompt;
	}

	public void setChoices(List<Choice> choices) {
		this.choices = choices;
	}

	public static Question fromAPIModel(Base.Question question) {
		List<Choice> choices = new ArrayList<>();

		for (Base.Choice choice: question.choices) {
			choices.add(Choice.fromAPIModel(choice));
		}

		return new Question(
			question.id,
			question.prompt,
			question.answer,
			choices
		);
	}
}
