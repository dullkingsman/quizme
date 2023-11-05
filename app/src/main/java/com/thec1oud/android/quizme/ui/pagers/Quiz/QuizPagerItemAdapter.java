package com.thec1oud.android.quizme.ui.pagers.Quiz;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.thec1oud.android.quizme.R;
import com.thec1oud.android.quizme.data.model.Choice;
import com.thec1oud.android.quizme.data.model.Question;
import com.thec1oud.android.quizme.utils.Utils;

import java.util.List;
import java.util.Objects;

public class QuizPagerItemAdapter extends RecyclerView.Adapter<QuizPagerItemViewHolder> {
	public static enum QuizMode {
		HISTORY,
		TEST
	}

	@FunctionalInterface public interface PagingCallback {
		void onPageChange(int position, boolean direction);
	}

	@FunctionalInterface public interface ValidationCallback {
		boolean onValidateAnswer(int questionId, String choiceId);
	}

	List<Question> questions;

	private PagingCallback pagingCallback;

	private ValidationCallback validationCallback;

	private int numberOfAnsweredQuestions = 0;

	public QuizPagerItemAdapter(List<Question> questions, QuizMode mode, PagingCallback pagingCallback, ValidationCallback validationCallback) {
		this.questions = questions;
		this.pagingCallback = pagingCallback;
		this.validationCallback = validationCallback;
	}

	@NonNull
	@Override
	public QuizPagerItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		return new QuizPagerItemViewHolder(
			LayoutInflater.from(parent.getContext())
				.inflate(R.layout.view_pager_item_quiz_question, parent, false)
		);
	}

	@Override
	public void onBindViewHolder(@NonNull QuizPagerItemViewHolder holder, int position) {
		Question question = questions.get(position);

		holder.prompt.setText(question.getPrompt());

		for(int i = 0; i < 4; i++) {
			String id = question.getChoices().get(i).getId();
			String value = question.getChoices().get(i).getValue();

			holder.choices[i].setText(value);

			holder.choices[i].setOnClickListener(view -> {
				if (!holder.getAnswerHasBeenChecked()) {
					holder.setGivenAnswer(id);

					this.updateNextButtonUi(holder, 1);
				}
			});
		}

		this.updateBackButtonUi(holder, position > 0);

		this.updateNextButtonUi(
			holder,
			holder.getGivenAnswer() == null
				? 0
				: position == this.questions.size() - 1 &&
					holder.getAnswerHasBeenChecked() &&
					this.numberOfAnsweredQuestions != this.questions.size()
					? 4: position == this.questions.size() - 1 &&
					holder.getAnswerHasBeenChecked()
					? 3
					: holder.getAnswerHasBeenChecked()
					? 2
					: 1
		);

		holder.navActions[0].setOnClickListener(view -> {
			this.pagingCallback.onPageChange(position, false);
		});

		holder.navActions[1].setOnClickListener(view -> {
			if(holder.getGivenAnswer() != null) {
				if (holder.getAnswerHasBeenChecked()) this.pagingCallback.onPageChange(position, true);
				else {
					this.setChoiceBackgrounds(holder, position);

					holder.setAnswerHasBeenChecked(true);

					this.validationCallback.onValidateAnswer(position, holder.getGivenAnswer());

					this.updateNextButtonUi(holder, this.questions.size() - 1 == position ? 3 : 2);

					for(int j = 0; j < 4; j++) holder.choices[j].setClickable(false);
				}
			}
		});
	}

	@Override
	public int getItemCount() {
		return this.questions.size();
	}

	private void updateBackButtonUi(@NonNull QuizPagerItemViewHolder holder, boolean active) {
		holder.navActions[0].setVisibility(active? View.VISIBLE: View.INVISIBLE);
		holder.navActions[0].setClickable(active);
		holder.navActions[0].setFocusable(active);
	}

	private void updateNextButtonUi(@NonNull QuizPagerItemViewHolder holder, int state) {
		Context context = holder.itemView.getContext();

		int[] colors = new int[] {
				Utils.getColorFromThemeAttribute(context, R.attr.QZColorSecondaryBackground),
				Utils.getColorFromThemeAttribute(context, R.attr.QZColorSurface),
				Utils.getColorFromThemeAttribute(context, R.attr.QZColorPrimary),
				Utils.getColorFromThemeAttribute(context, R.attr.QZColorOnPrimary),
				Utils.getColorFromThemeAttribute(context, R.attr.QZColorSecondary),
				Utils.getColorFromThemeAttribute(context, R.attr.QZColorOnSecondary),
				Utils.getColorFromThemeAttribute(context, R.attr.QZColorInfo)
		};

		holder.navActions[1].setBackgroundTintList(ColorStateList.valueOf(
			state == 3? colors[2]: state == 2? colors[6]: state == 1? colors[4]: colors[0]
		));

		holder.navActions[1].setTextColor(
			state == 3? colors[3]: state == 2 || state == 1? colors[5]: colors[1]
		);

		holder.navActions[1].setText(state == 3? "Finish": state == 2? "Next": "Check");

		holder.navActions[1].setClickable(state != 0);

		holder.navActions[1].setFocusable(state != 0);
	}

	private void setChoiceBackgrounds(@NonNull QuizPagerItemViewHolder holder, int questionId) {
		if (holder.getGivenAnswer() != null) {
			Context context = holder.itemView.getContext();

			int givenAnswerPosition = -1;
			int correctAnswerPosition = -1;

			int counter = 0;

			for(Choice choice : this.questions.get(questionId).getChoices()) {
				if (Objects.equals(choice.getId(), holder.getGivenAnswer()))
					givenAnswerPosition = counter;

				if (Objects.equals(choice.getId(), this.questions.get(questionId).getAnswer()))
					correctAnswerPosition = counter;

				if(counter == givenAnswerPosition || counter == correctAnswerPosition)
					holder.choices[counter].setBackground(
						counter == correctAnswerPosition
							? AppCompatResources.getDrawable(context, R.drawable.choice_success)
							: AppCompatResources.getDrawable(context, R.drawable.choice_error)
					);

				counter += 1;
			}
		}
	}
}
