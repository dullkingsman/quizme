package com.thec1oud.android.quizme.core;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.thec1oud.android.quizme.R;
import com.thec1oud.android.quizme.utils.Utils;

public abstract class DraggableFragment extends BaseFragment {
	@FunctionalInterface public interface AnimationCallback {
		void onAnimationEnd();
	}

	protected final float MIN_DRAG_ANGLE = 30;

	protected View draggableView;

	protected OnBackPressedCallback onBackPressedCallback;

	protected PointF startPoint = new PointF(0, 0);

	protected boolean isDragging = false;
	protected boolean firstDrag = false;

	protected boolean isAnimating = false;

	protected float backstackThreshold = 0.4f;


	public DraggableFragment(int contentLayoutId) {
		super(contentLayoutId);
	}

	public DraggableFragment(int contentLayoutId, float backstackThreshold) {
		this(contentLayoutId);

		this.backstackThreshold = backstackThreshold;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		this.onBackPressedCallback = new OnBackPressedCallback(true) {
			@Override public void handleOnBackPressed() {
				handleBackButtonPressed();
			}
		};

		requireActivity()
				.getOnBackPressedDispatcher()
				.addCallback(requireActivity(), this.onBackPressedCallback);
	}

	@Override public void onResume() {
		super.onResume();

		if (onBackPressedCallback != null && !onBackPressedCallback.isEnabled())
			onBackPressedCallback.setEnabled(true);
	}

	@Override public void onPause() {
		super.onPause();

		if (onBackPressedCallback != null && onBackPressedCallback.isEnabled())
			onBackPressedCallback.setEnabled(false);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();

		this.onBackPressedCallback.remove();
	}

	@Override public void initViews(View fragment) {
		this.draggableView = fragment.findViewById(R.id.draggable_view);
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override public void initAnimations() {
		this.draggableView.setOnTouchListener((View.OnTouchListener) (view, event) -> {
			if(this.isAnimating) return true;

			switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN: this.handleDown(event); break;
				case MotionEvent.ACTION_MOVE: this.handleMove(event); break;
				case MotionEvent.ACTION_UP: this.handleUp(event); break;
			}

			return true;
		});
	}

	@Override public void initListenersAndObservers() { }

	protected void animateViewHorizontalTranslation(View view, float start, float end, int duration, int reverseAlpha, AnimationCallback animationCallback) {
		ObjectAnimator translationAnimator = ObjectAnimator.ofFloat(view, "x", start, end);
		translationAnimator.setDuration(duration);

		ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(view, "alpha", reverseAlpha == 0? 0f: 1f, reverseAlpha == 1? 0f: 1f);
		alphaAnimator.setDuration(duration);

		AnimatorSet animatorSet = new AnimatorSet();
		animatorSet.playTogether(translationAnimator, alphaAnimator);

		animatorSet.addListener(new Animator.AnimatorListener() {
			@Override public void onAnimationStart(@NonNull Animator animator) {
				isAnimating = true;
			}

			@Override public void onAnimationEnd(@NonNull Animator animator) {
				animationCallback.onAnimationEnd();
				isAnimating = false;
			}

			@Override public void onAnimationCancel(@NonNull Animator animator) { }

			@Override public void onAnimationRepeat(@NonNull Animator animator) { }
		});

		animatorSet.start();
	}

	protected void handleBackButtonPressed() {
		this.animateAndPopSelf(0, (this.backstackThreshold - 0.2f) * this.draggableView.getWidth(), 150, 1);
	}

	protected void animateAndPopSelf(float start, float end, int duration, int reverseAlpha) {
		this.animateViewHorizontalTranslation(this.draggableView, start, end, duration, reverseAlpha, () -> {
			this.requireActivity()
					.getSupportFragmentManager()
					.popBackStack();
		});
	}

	protected void handleDown(MotionEvent event) {
		this.startPoint.x = event.getRawX();
		this.startPoint.y = event.getRawY();

		this.firstDrag = true;
	}

	protected void handleMove(MotionEvent event) {
		float dragAngle = Utils.calculateAngle(startPoint, new PointF(event.getRawX(), event.getRawY()));

		if(this.isDragging || (this.firstDrag && dragAngle <= MIN_DRAG_ANGLE && dragAngle >= -MIN_DRAG_ANGLE)) {
			float deltaX = event.getRawX() - this.startPoint.x;

			float newX = this.draggableView.getX() + deltaX;

			if(newX >= 0) {
				this.startPoint.x = event.getRawX();

				this.draggableView.setX(newX);
			}

			this.isDragging = true;
		}
	}

	protected void handleUp(MotionEvent event) {
		if(this.draggableView.getX() > this.backstackThreshold * this.draggableView.getWidth()) {
			this.animateAndPopSelf(this.draggableView.getX(), this.draggableView.getWidth(),50, -1);
		} else {
			this.animateViewHorizontalTranslation(this.draggableView, this.draggableView.getX(), 0, 50, -1, () -> {});
		}

		this.isDragging = false;

		this.firstDrag = false;
	}
}

