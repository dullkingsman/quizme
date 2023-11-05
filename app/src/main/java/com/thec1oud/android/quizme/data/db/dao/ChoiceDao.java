package com.thec1oud.android.quizme.data.db.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.thec1oud.android.quizme.data.db.model.Choice;

import java.util.List;

@Dao
public interface ChoiceDao {
	@Insert
	void insert(Choice user);

	@Update
	void update(Choice user);

	@Delete
	void delete(Choice user);

	@Query("SELECT * FROM choices")
	List<Choice> getAllChoices();

	@Query("SELECT * FROM choices WHERE id = :id")
	List<Choice> getChoiceById(int id);

	@Query("SELECT * FROM choices WHERE id = :questionId")
	List<Choice> getChoiceByQuestionId(int questionId);
}