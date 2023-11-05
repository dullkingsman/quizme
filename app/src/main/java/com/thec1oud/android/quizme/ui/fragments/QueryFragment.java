package com.thec1oud.android.quizme.ui.fragments;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.thec1oud.android.quizme.BuildConfig;
import com.thec1oud.android.quizme.MainActivity;
import com.thec1oud.android.quizme.R;
import com.thec1oud.android.quizme.core.BaseFragment;
import com.thec1oud.android.quizme.data.api.callers.QuizCall;
import com.thec1oud.android.quizme.data.model.Choice;
import com.thec1oud.android.quizme.data.model.Question;
import com.thec1oud.android.quizme.data.model.Quiz;
import com.thec1oud.android.quizme.service.tasks.network.Request;
import com.thec1oud.android.quizme.ui.pagers.Quiz.QuizPagerItemAdapter;
import com.thec1oud.android.quizme.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QueryFragment extends BaseFragment {
	EditText quizQueryInput;
	ImageButton quizQueryButton;
	ImageButton queryHistoryButton;
	TextView queryCharacterCountText;
	ProgressBar quizQueryProgress;

	private boolean requestingQuiz = false;

	public QueryFragment(int contentLayoutId) {
		super(contentLayoutId);
	}

	@Override public void initViews(View fragment) {
		this.quizQueryInput = fragment.findViewById(R.id.quiz_query_input);

		this.quizQueryButton = fragment.findViewById(R.id.quiz_query_button);

		this.queryHistoryButton = fragment.findViewById(R.id.query_history_button);

		this.queryCharacterCountText = fragment.findViewById(R.id.query_character_count_text);

		this.quizQueryProgress = fragment.findViewById(R.id.quiz_query_progress);
	}

	@Override
	public void initAnimations() { }

	@Override public void initListenersAndObservers() {
		this.quizQueryInput.addTextChangedListener(new TextWatcher() {
			@Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

			@Override public void onTextChanged(CharSequence s, int start, int before, int count) {
				int[] colors = {
					Utils.getColorFromThemeAttribute(requireActivity(), R.attr.QZColorSurface),
					Utils.getColorFromThemeAttribute(requireActivity(), R.attr.QZColorAccent),
					Utils.getColorFromThemeAttribute(requireActivity(), R.attr.QZColorSecondary),
				};

				if (s.length() > 0 && s.length() <= 250) {
					quizQueryButton.setColorFilter(colors[2]);

					if(queryCharacterCountText.getVisibility() == View.INVISIBLE)
						queryCharacterCountText.setVisibility(View.VISIBLE);

					queryCharacterCountText.setTextColor(s.length() == 250 ? colors[1] : colors[0]);

					queryCharacterCountText.setAlpha(s.length() == 250 ? 0.4f: 1f);
				} else if (s.length() == 0) {
					quizQueryButton.setColorFilter(colors[0]);

					if (queryCharacterCountText.getVisibility() == View.VISIBLE)
						queryCharacterCountText.setVisibility(View.INVISIBLE);
				}

				quizQueryButton.setClickable(s.length() > 0 && !requestingQuiz);
				quizQueryButton.setFocusable(s.length() > 0 && !requestingQuiz);

				queryCharacterCountText.setText(s.length() + " / 250");
			}

			@Override public void afterTextChanged(Editable s) {}
		});

		this.quizQueryButton.setOnClickListener(view -> {
			this.quizQueryInput.clearFocus();

			this.hideSoftInput(this.quizQueryInput.getWindowToken(), 0);

			MainActivity activity = ((MainActivity) this.requireActivity());

//			List<Question> questions = new ArrayList<>();
//
//			ArrayList<Choice> choices = new ArrayList<>();
//
//			choices.add(new Choice("A", "I am a theatre kid."));
//			choices.add(new Choice("B", "I work for marvel."));
//			choices.add(new Choice("C", "I am french"));
//			choices.add(new Choice("D", "I am hiding in plain sight") );
//
//			Question question = new Question(
//				0,
//				"What is the purpose of that flamboyant scarf?",
//				"C",
//				choices
//			);
//
//			questions.add(question);
//			questions.add(question);
//			questions.add(question);
//
//			Quiz quiz = new Quiz("some-quiz", questions);

			try {
				this.toggleRequestingQueryUi(true);

				QuizCall.getQuiz.execute(
					new Request(BuildConfig.API_CALL_BASE_ULR + "/20Questions?topic=" + URLEncoder.encode(this.quizQueryInput.getText().toString().trim(), "UTF-8")),
					data -> {
						Log.i("NETWORK_CALL", "quiz_id: " + data.id);

						Quiz quiz = Quiz.fromAPIModel(data);

						if (activity.quizFragment == null)
							activity.quizFragment = new QuizFragment(
								R.layout.fragment_quiz,
								quiz,
								QuizPagerItemAdapter.QuizMode.TEST
							);
						else if (!Objects.equals(activity.quizFragment.getQuiz().getId(), quiz.getId())) {
							activity.quizFragment.setShouldNotifyDataSetChanged(true);
							activity.quizFragment.setQuiz(quiz);
						}

						this.requireActivity()
								.getSupportFragmentManager()
								.beginTransaction()
								.setCustomAnimations(R.anim.surface_enter_animation, 0)
								.add(R.id.root_fragment_container, activity.quizFragment)
								.addToBackStack("fragment_quiz")
								.commit();
					},
					(error, errorIsLocal) -> {
						Log.i("NETWORK_CALL", "shit");

						Toast.makeText(
							this.requireActivity(),
							errorIsLocal
								? "Could not complete your request! Try again."
								: error.message,
							Toast.LENGTH_SHORT
						).show();
					},
					() -> {
						Log.i("NETWORK_CALL", "it's done");

						this.toggleRequestingQueryUi(false);
					}
				);
			} catch (UnsupportedEncodingException e) {
				Toast.makeText(
					this.requireActivity(),
					"Could not parse your text!",
					Toast.LENGTH_SHORT
				).show();
			}
		});

		this.queryHistoryButton.setOnClickListener(view -> {
			MainActivity activity = ((MainActivity) this.requireActivity());

			if (activity.queryHistoryFragment == null)
				activity.queryHistoryFragment = new QueryHistoryFragment(R.layout.fragment_query_history);

			this.quizQueryInput.clearFocus();

			this.hideSoftInput(this.quizQueryInput.getWindowToken(), 0);

			this.requireActivity()
				.getSupportFragmentManager()
				.beginTransaction()
				.setCustomAnimations(R.anim.surface_enter_animation, 0)
				.add(R.id.root_fragment_container, activity.queryHistoryFragment)
				.addToBackStack("fragment_query_history")
				.commit();
		});
	}

	private void toggleRequestingQueryUi(boolean enable) {
		this.requestingQuiz = enable;

		this.quizQueryProgress.setIndeterminate(enable);
		this.quizQueryProgress.setVisibility(enable? View.VISIBLE: View.GONE);

		this.quizQueryButton.setVisibility(!enable? View.VISIBLE: View.INVISIBLE);
		this.quizQueryButton.setClickable(!enable);
		this.quizQueryButton.setFocusable(!enable);
	}
}
