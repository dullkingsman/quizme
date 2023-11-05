package com.thec1oud.android.quizme.data.api.callers;

import com.thec1oud.android.quizme.data.api.model.Quiz.GetQuizResponse;
import com.thec1oud.android.quizme.service.tasks.network.NetworkCall;

public class QuizCall {
	public static NetworkCall<GetQuizResponse.Success, GetQuizResponse.Error> getQuiz
		= new NetworkCall<>(GetQuizResponse.Success.class, GetQuizResponse.Error.class);
}
