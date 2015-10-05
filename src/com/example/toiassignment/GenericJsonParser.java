package com.example.toiassignment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

public class GenericJsonParser {

	static InputStream is = null;
	static JSONObject jObj = null;
	static String json = "";

	public JSONObject parse(InputStream in) throws IOException {
		try {

			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "iso-8859-1"), 8);
			StringBuilder builder = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				builder.append(line + "\n");
			}
			json = builder.toString();
		} catch (Exception e) {
			Log.e("Buffer Error", "Error converting result " + e.toString());
		} finally {
			in.close();
		}

		try {
			jObj = new JSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jObj;
	}

}

