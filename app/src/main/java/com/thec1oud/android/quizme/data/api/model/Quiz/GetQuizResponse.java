package com.thec1oud.android.quizme.data.api.model.Quiz;

import com.thec1oud.android.quizme.data.api.model.common.Base;
import com.thec1oud.android.quizme.data.api.model.common.APIError;

public class GetQuizResponse {
	public static class Success extends Base.Quiz { }

	public static class Error extends APIError { }
}
