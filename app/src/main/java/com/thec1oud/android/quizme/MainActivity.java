package com.thec1oud.android.quizme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.thec1oud.android.quizme.ui.fragments.QueryFragment;
import com.thec1oud.android.quizme.ui.fragments.QueryHistoryFragment;
import com.thec1oud.android.quizme.ui.fragments.QuizFragment;
import com.thec1oud.android.quizme.ui.fragments.QuizHistoryFragment;

public class MainActivity extends AppCompatActivity {
	QueryFragment queryFragment = new QueryFragment(R.layout.fragment_query);

	public QuizFragment quizFragment = null;

	public QuizHistoryFragment quizHistoryFragment = null;

	public QueryHistoryFragment queryHistoryFragment = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		this.getSupportFragmentManager()
			.beginTransaction()
			.add(R.id.root_fragment_container, this.queryFragment)
			.addToBackStack("fragment_main")
			.commit();
	}
}