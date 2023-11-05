package com.thec1oud.android.quizme.ui.fragments;

import android.view.View;
import android.widget.ImageButton;

import com.thec1oud.android.quizme.R;
import com.thec1oud.android.quizme.core.DraggableFragment;

public class QueryHistoryFragment extends DraggableFragment {
	ImageButton backButton;

	public QueryHistoryFragment(int contentLayoutId) {
		super(contentLayoutId);
	}

	@Override public void initViews(View fragment) {
		super.initViews(fragment);

		this.backButton = fragment.findViewById(R.id.query_history_fragment_back_button);
	}

	@Override public void initListenersAndObservers() {
		super.initListenersAndObservers();

		this.backButton.setOnClickListener(view -> {
			this.animateAndPopSelf(0, (this.backstackThreshold - 0.2f) * this.draggableView.getWidth(), 150, 1);
		});
	}
}

