package com.thec1oud.android.quizme.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thec1oud.android.quizme.data.db.model.Question;

import java.util.List;

@Dao
public interface QuestionDao {
	@Insert
	void insert(Question user);

	@Update
	void update(Question user);

	@Delete
	void delete(Question user);

	@Query("SELECT * FROM questions")
	List<Question> getAllQuestions();

	@Query("SELECT * FROM questions WHERE id = :id")
	List<Question> getQuestionById(int id);

	@Query("SELECT * FROM questions WHERE id = :quizId")
	List<Question> getQuestionByQuizId(int quizId);
}