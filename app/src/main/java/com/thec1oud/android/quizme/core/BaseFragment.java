package com.thec1oud.android.quizme.core;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class BaseFragment extends Fragment {

	public BaseFragment(int contentLayoutId) {
		super(contentLayoutId);
	}

	@Nullable @Override public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);

		this.initViews(view);
		this.initAnimations();
		this.initListenersAndObservers();

		return view;
	}

	public abstract void initViews(View fragment);

	public abstract void initAnimations();

	public abstract void initListenersAndObservers();

	protected void hideSoftInput(IBinder windowToken, int flags) {
		((InputMethodManager) requireActivity()
			.getSystemService(Context.INPUT_METHOD_SERVICE))
			.hideSoftInputFromWindow(windowToken, flags);
	}
}

