package com.thec1oud.android.quizme;

import android.app.Application;
import android.content.Intent;

import androidx.room.Room;

import com.thec1oud.android.quizme.data.api.callers.QuizCall;
import com.thec1oud.android.quizme.data.db.AppDatabase;
import com.thec1oud.android.quizme.service.tasks.network.Request;

public class QuizMeApp extends Application {
	AppDatabase appDatabase;

	@Override public void onCreate() {
		super.onCreate();

		appDatabase = Room.databaseBuilder(this, AppDatabase.class, "app-database").build();

		Intent intent = new Intent(this, MainActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		this.startActivity(intent);
	}
}
