package com.thec1oud.android.quizme.data.db.model;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "choices", foreignKeys = @ForeignKey(entity = Question.class, parentColumns = "id", childColumns = "questionId", onDelete = ForeignKey.CASCADE))
public class Choice {
	@PrimaryKey(autoGenerate = true)
	public int id;
	public int questionId;
	public String _id;
	public String value;
}
