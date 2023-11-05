package com.thec1oud.android.quizme.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "answers", foreignKeys = @ForeignKey(entity = Question.class, parentColumns = "id", childColumns = "questionId"))
public class Answer {
	@PrimaryKey @NonNull
	public String id;
	public int questionId;

	public Answer() {
		id = "";
	}
}

