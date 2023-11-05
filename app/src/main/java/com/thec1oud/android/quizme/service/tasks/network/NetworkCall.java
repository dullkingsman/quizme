package com.thec1oud.android.quizme.service.tasks.network;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

public class NetworkCall<S, E> {
	public static interface SuccessCallback<T> {
		void onSuccess(T data);
	}

	public static interface ErrorCallback<E> {
		void onError(E error, boolean errorIsLocal);
	}

	public static interface FinalCallback {
		void onFinal();
	}

	private SuccessCallback<S> successCallback;
	private ErrorCallback<E> errorCallback;
	private FinalCallback finalCallback;

	private final Type responseType;

	Handler mainHandler = new Handler(Looper.getMainLooper());

	public NetworkCall(Class<S> dataClass, Class<E> errorClass) {
		this.responseType = TypeToken.getParameterized(Response.class, dataClass, errorClass).getType();
	}

	public void execute(
		Request apiCallData,
		SuccessCallback<S> successCallback,
		ErrorCallback<E> errorCallback,
		FinalCallback finalCallback
	) {
		this.successCallback = successCallback;
		this.errorCallback = errorCallback;
		this.finalCallback = finalCallback;

		Thread thread = new Thread(() -> handleResult(doInBackground(apiCallData)));
		thread.start();
	}

	private Response<S, E> doInBackground(Request apiCallData) {
		HttpURLConnection connection = null;
		BufferedReader reader = null;

		Log.d("NETWORK_CALL", "DO I EVEN RUN?");

		try {
			Log.d("NETWORK_CALL", "I am about to initialize!");

			URL url = new URL(apiCallData.getUrl());

			Log.d("NETWORK_CALL", "I got the url!");

			connection = (HttpURLConnection) url.openConnection();

			connection.setRequestMethod(apiCallData.getMethod());

			connection.setConnectTimeout(10000);

			connection.setReadTimeout(60000);

			Log.d("NETWORK_CALL", "I initialize the connection!");

			if (apiCallData.getHeaders() != null) {
				for (Map.Entry<String, String> headerEntry : apiCallData.getHeaders().entrySet()) {
					connection.setRequestProperty(headerEntry.getKey(), headerEntry.getValue());
				}
			}

			if (apiCallData.getBody() != null) {
				connection.setDoOutput(true);
				connection.setRequestProperty("Content-Type", "application/json");
				byte[] postData = apiCallData.getBody().getBytes(StandardCharsets.UTF_8);
				DataOutputStream out = new DataOutputStream(connection.getOutputStream());
				out.write(postData);
				out.flush();
				out.close();
			}

			Log.d("NETWORK_CALL", "It gonna connect");

			connection.connect();

			int responseCode = connection.getResponseCode();

			if (responseCode == HttpURLConnection.HTTP_OK) {
				Log.d("NETWORK_CALL", "It is okay");

				reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				StringBuilder result = new StringBuilder();
				String line;

				while ((line = reader.readLine()) != null) {
					result.append(line);
				}

				Log.d("NETWORK_CALL", "result: " + result);

				return !Objects.equals(connection.getHeaderField("Content-Type"), "application/json")
					? new Response<>(result.toString())
					: (new Gson()).fromJson(result.toString(), this.responseType);
			} else if (Objects.equals(connection.getHeaderField("Content-Type"), "application/json")) {
				Log.d("NETWORK_CALL", "It error but json");
				reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
				StringBuilder errorResult = new StringBuilder();
				String errorLine;

				while ((errorLine = reader.readLine()) != null) errorResult.append(errorLine);

				Log.d("NETWORK_CALL", "result: " + errorResult);

				return new Response<>(
					null,
					(new Gson()).fromJson(errorResult.toString(), this.responseType)
				);
			} else return new Response<>();
		} catch (IOException e) {
			e.printStackTrace();

			return null;
		} finally {
			if (connection != null) connection.disconnect();

			if (reader != null) {
				try { reader.close(); }
				catch (IOException e) { e.printStackTrace(); }
			}
		}
	}

	private void handleResult(Response<S, E> response) {
		mainHandler.post(() -> {
			if (response != null) {
				if (response.getData() != null)
					if (successCallback != null) successCallback.onSuccess(response.getData());
				else
					if (errorCallback != null) errorCallback.onError(response.getError(), false);
			} else errorCallback.onError(null, true);

			if(this.finalCallback != null) this.finalCallback.onFinal();
		});
	}
}