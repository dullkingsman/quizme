package com.thec1oud.android.quizme.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thec1oud.android.quizme.data.db.model.Quiz;

import java.util.List;

@Dao
public interface QuizDao {
	@Insert
	void insert(Quiz user);

	@Update
	void update(Quiz user);

	@Delete
	void delete(Quiz user);

	@Query("SELECT * FROM quizes")
	List<Quiz> getAllQuestions();
}