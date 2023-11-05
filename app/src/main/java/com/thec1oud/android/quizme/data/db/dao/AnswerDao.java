package com.thec1oud.android.quizme.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thec1oud.android.quizme.data.db.model.Answer;

import java.util.List;

@Dao
public interface AnswerDao {
	@Insert
	void insert(Answer user);

	@Update
	void update(Answer user);

	@Delete
	void delete(Answer user);

	@Query("SELECT * FROM answers")
	List<Answer> getAllAnswers();

	@Query("SELECT * FROM answers WHERE id = :id")
	List<Answer> getAnswerById(int id);

	@Query("SELECT * FROM answers WHERE id = :questionId")
	List<Answer> getAnswerByQuestionId(int questionId);
}