package com.example.toiassignment;

import java.io.IOException;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.content.Context;
import android.util.Log;

public class DataRetrieval {

	public InputStream downloadUrl(String urlString, Context mContext)
			throws IOException {

		HttpClient httpClient = new DefaultHttpClient();
		HttpGet httpget = new HttpGet(urlString);
		HttpResponse response;
		response = httpClient.execute(httpget);
		int x = response.getStatusLine().getStatusCode();

		if (x == 200) {
			HttpEntity entity = response.getEntity();
			return entity.getContent();

		} else {
			Log.i("Nitin", " network not not available");

			return null;
		}

	}

}
