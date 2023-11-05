package com.thec1oud.android.quizme.data.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.thec1oud.android.quizme.data.db.dao.AnswerDao;
import com.thec1oud.android.quizme.data.db.dao.ChoiceDao;
import com.thec1oud.android.quizme.data.db.dao.QuestionDao;
import com.thec1oud.android.quizme.data.db.dao.QuizDao;
import com.thec1oud.android.quizme.data.db.model.Answer;
import com.thec1oud.android.quizme.data.db.model.Choice;
import com.thec1oud.android.quizme.data.db.model.Question;
import com.thec1oud.android.quizme.data.db.model.Quiz;

@Database(entities = {Answer.class, Choice.class, Question.class, Quiz.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
	public abstract AnswerDao answerDao();
	public abstract ChoiceDao choiceDao();
	public abstract QuestionDao questionDao();
	public abstract QuizDao quizDao();
}