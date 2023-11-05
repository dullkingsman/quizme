package com.thec1oud.android.quizme.data.db.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.thec1oud.android.quizme.data.model.Question;

import java.util.List;

@Entity(tableName = "quizes")
public class Quiz {
	@PrimaryKey @NonNull
	public String id;
	public String query;
	public boolean attempted = false;
	public boolean completed = false;

	public Quiz() {
		id = "";
	}
}
