package com.thec1oud.android.quizme.ui.fragments;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.viewpager2.widget.ViewPager2;

//import com.thec1oud.android.quizme.MainActivity;
import com.thec1oud.android.quizme.R;
import com.thec1oud.android.quizme.core.DraggableFragment;
import com.thec1oud.android.quizme.data.model.Quiz;
import com.thec1oud.android.quizme.ui.pagers.Quiz.QuizPagerItemAdapter;

public class QuizFragment extends DraggableFragment {
	ImageButton backButton;

	TextView quizPagerPositionIndicator;

//	ImageButton quizInteractionHistoryButton;

	ViewPager2 quizPager;

	private Quiz quiz;

	private QuizPagerItemAdapter.QuizMode mode;

	private int numberOfAnsweredQuestions = 0;

	private boolean viewPagerIsSwiping = false;

	private boolean shouldNotifyDataSetChanged = false;

	private AlertDialog.Builder builder;

	private ViewPager2.OnPageChangeCallback pageChangeCallback = new ViewPager2.OnPageChangeCallback() {
		@Override
		public void onPageSelected(int position) {
			super.onPageSelected(position);

			quizPagerPositionIndicator.setText((position + 1) + " / "  + quiz.getQuestions().size());
		}
	};

	public QuizFragment(int contentLayoutId, Quiz quiz, QuizPagerItemAdapter.QuizMode mode) {
		super(contentLayoutId);

		this.quiz = quiz;

		this.mode = mode;
	}

	@Override
	public void onResume() {
		super.onResume();

		if(this.shouldNotifyDataSetChanged && this.quizPager != null) {
			this.quizPager.getAdapter().notifyDataSetChanged();
			this.quizPager.requestLayout();

			this.shouldNotifyDataSetChanged = false;
		}
	}

	@Override public void initViews(View fragment) {
		super.initViews(fragment);

		this.backButton = fragment.findViewById(R.id.quiz_fragment_back_button);

		this.quizPagerPositionIndicator = fragment.findViewById(R.id.quiz_pager_position_indicator);

		this.quizPagerPositionIndicator.setText("1 / " + this.quiz.getQuestions().size());

//		this.quizInteractionHistoryButton = fragment.findViewById(R.id.quiz_interaction_history_button);

		this.quizPager = fragment.findViewById(R.id.quiz_pager);

		this.quizPager.setOffscreenPageLimit(2);

		this.quizPager.setAdapter(
			new QuizPagerItemAdapter(
				this.quiz.getQuestions(),
				this.mode,
				(position, direction) -> {
					if (position == this.quiz.getQuestions().size() - 1 && direction) this.handleExit();
					else this.quizPager.setCurrentItem(position + (direction? 1: -1), true);
				},
				(questionId, choiceId) -> {
					this.numberOfAnsweredQuestions += 1;
					return true;
				}
			)
		);

		builder = new AlertDialog.Builder(requireActivity());
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override public void initListenersAndObservers() {
		super.initListenersAndObservers();

		this.backButton.setOnClickListener(view -> this.handleExit());

//		this.quizInteractionHistoryButton.setOnClickListener(v -> {
//			MainActivity activity = ((MainActivity) this.requireActivity());
//
//			if (activity.quizHistoryFragment == null)
//				activity.quizHistoryFragment = new QuizHistoryFragment(R.layout.fragment_quiz_history);
//
//			this.requireActivity()
//					.getSupportFragmentManager()
//					.beginTransaction()
//					.setCustomAnimations(R.anim.surface_enter_animation, 0)
//					.add(R.id.root_fragment_container, activity.quizHistoryFragment)
//					.addToBackStack("fragment_quiz_history")
//					.commit();
//		});

		this.quizPager.registerOnPageChangeCallback(this.pageChangeCallback);

		this.quizPager.getChildAt(0).setOnTouchListener((view, event) -> {
			boolean isOnFirstPage = this.quizPager.getCurrentItem() == 0;
			boolean isSwipeRight = event.getRawX() > this.startPoint.x + 100;

			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					this.handleDown(event);

					this.viewPagerIsSwiping = false;

					break;
				case MotionEvent.ACTION_MOVE:
					Log.i("WELL", "currentX: " + event.getRawX() + "		" + "startX: " + this.startPoint.x);
					Log.i("WELL", "ViewPagerSwiping: " + this.viewPagerIsSwiping + "		" + "IsSwipeRight: " + isSwipeRight + "		" + "IsOnFirstPage: " + isOnFirstPage);

					if (isOnFirstPage && (isSwipeRight || this.isDragging)) {
						this.handleMove(event);
						return true;
					} else if(!this.isDragging && !isSwipeRight) this.viewPagerIsSwiping = true;

					break;
				case MotionEvent.ACTION_UP:
					this.viewPagerIsSwiping = false;

					if (isOnFirstPage && (isSwipeRight || this.isDragging)) {
						this.handleUp(event);
						return true;
					}

					break;
				default: this.viewPagerIsSwiping = false;
			}

			return false;
		});
	}

	@Override
	protected void handleBackButtonPressed() {
		super.handleBackButtonPressed();

		this.numberOfAnsweredQuestions = 0;

		this.quizPager.setCurrentItem(0, true);

		this.quizPager.unregisterOnPageChangeCallback(this.pageChangeCallback);
	}

	private void handleExit() {
		if (this.numberOfAnsweredQuestions == this.quiz.getQuestions().size() || this.numberOfAnsweredQuestions == 0) {
			this.handleBackButtonPressed();
		} else if(this.numberOfAnsweredQuestions > 0)  {
			builder.setTitle("Warning");
			builder.setMessage("You have not yet completed this quiz. Are you sure you want to get exit?");
			builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());
			builder.setPositiveButton("Exit", (dialog, which) -> {
				dialog.dismiss();
				this.handleBackButtonPressed();
			});

			AlertDialog dialog = builder.create();
			dialog.show();
		}
	}

	public Quiz getQuiz() {
		return quiz;
	}

	public void setShouldNotifyDataSetChanged(boolean shouldNotifyDataSetChanged) {
		this.shouldNotifyDataSetChanged = shouldNotifyDataSetChanged;
	}

	public void setQuiz(Quiz quiz) {
		this.quiz = quiz;
	}
}

