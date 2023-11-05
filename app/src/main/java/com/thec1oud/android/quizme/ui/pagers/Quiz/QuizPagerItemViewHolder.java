package com.thec1oud.android.quizme.ui.pagers.Quiz;

import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.thec1oud.android.quizme.R;

public class QuizPagerItemViewHolder extends RecyclerView.ViewHolder {
	TextView prompt;
	RadioGroup choicesContainer;
	TextView[] choices;
	Button[] navActions;

	private String givenAnswer = null;

	private boolean answerHasBeenChecked = false;

	public QuizPagerItemViewHolder(@NonNull View itemView) {
		super(itemView);

		this.prompt = itemView.findViewById(R.id.prompt);

		this.choicesContainer = itemView.findViewById(R.id.choices);

		this.choices = new TextView[]{
			itemView.findViewById(R.id.choice_a),
			itemView.findViewById(R.id.choice_b),
			itemView.findViewById(R.id.choice_c),
			itemView.findViewById(R.id.choice_d)
		};

		this.navActions = new Button[] {
			itemView.findViewById(R.id.back_button),
			itemView.findViewById(R.id.next_button)
		};
	}

	public String getGivenAnswer() {
		return givenAnswer;
	}

	public void setGivenAnswer(String givenAnswer) {
		this.givenAnswer = givenAnswer;
	}

	public boolean getAnswerHasBeenChecked() {
		return answerHasBeenChecked;
	}

	public void setAnswerHasBeenChecked(boolean answerHasBeenChecked) {
		this.answerHasBeenChecked = answerHasBeenChecked;
	}
}
