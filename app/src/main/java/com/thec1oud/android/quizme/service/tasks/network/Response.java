package com.thec1oud.android.quizme.service.tasks.network;

public class Response<S, E> {
	S data = null;
	E error = null;
	String rawResponse = null;

	Response() { }

	Response(String rawResponse){
		this.rawResponse = rawResponse;
	}

	Response(S data, E error){
		this.data = data;
		this.error = error;
	}

	public S getData() {
		return data;
	}

	public void setData(S data) {
		this.data = data;
	}

	public E getError() {
		return error;
	}

	public void setError(E error) {
		this.error = error;
	}
}
