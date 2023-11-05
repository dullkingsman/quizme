package com.thec1oud.android.quizme.data.db.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "questions", foreignKeys = @ForeignKey(entity = Quiz.class, parentColumns = "id", childColumns = "quizId", onDelete = ForeignKey.CASCADE))
public class Question {
	@PrimaryKey(autoGenerate = true)
	public int id;
	public int quizId;
	public int _id;
	public String prompt;
	public String answer;
}
