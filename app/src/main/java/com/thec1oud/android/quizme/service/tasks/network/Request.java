package com.thec1oud.android.quizme.service.tasks.network;


import java.util.HashMap;
import java.util.Map;

public class Request {
	private String url;
	private String method;
	private String body;
	private Map<String, String> headers = new HashMap<>();

	public Request(String url) {
		this.url = url;
		this.method = "GET";
	}

	public Request(String url, String method) {
		this.url = url;
		this.method = method;
		this.body = null;
	}

	public Request(String url, String method, String body) {
		this.url = url;
		this.method = method;
		this.body = body;
	}

	public Request(String url, String method, String body, Map<String, String> headers) {
		this.url = url;
		this.method = method;
		this.body = body;
		this.headers = headers;
	}

	public String getUrl() {
		return url;
	}

	public String getMethod() {
		return method;
	}

	public String getBody() {
		return body;
	}

	public Map<String, String> getHeaders() {
		return this.headers;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}
}