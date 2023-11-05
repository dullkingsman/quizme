package com.thec1oud.android.quizme.data.model;

import com.thec1oud.android.quizme.data.api.model.common.Base;

public class Choice {
	protected String id;
	protected String value;

	public Choice(String id, String value) {
		this.id = id;
		this.value = value;
	}

	public String getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public static Choice fromAPIModel(Base.Choice choice) {
		return new Choice(choice.id, choice.value);
	}
}
