package com.thec1oud.android.quizme.data.api.model.common;

public class Base {
	public static class Quiz {
		public String id;
		public Question[] questions;

		public Quiz() {  }
	}

	public static class Question {
		public int id;
		public String prompt;
		public String answer;
		public Choice[] choices;

		public Question() {  }
	}

	public static class Choice {
		public String id;
		public String value;

		public Choice() {  }
	}
}
