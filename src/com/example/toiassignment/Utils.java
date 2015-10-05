package com.example.toiassignment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.json.JSONException;
import org.json.JSONObject;

public class Utils {

	public static void writeToFile(JSONObject json, File file) {
		// TODO Auto-generated method stub
		try {
			FileWriter fileW = new FileWriter(file.toString());
			fileW.write(json.toString());
			fileW.flush();
			fileW.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static JSONObject readFromFile(File file) {
		// TODO Auto-generated method stub
		String json = "";
		JSONObject jObject = null;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			StringBuilder builder = new StringBuilder();
			String line = "";
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			json = builder.toString();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			jObject = new JSONObject(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return jObject;
	}

	public static long getTimeDifference(String dateTime) {
		// TODO Auto-generated method stub Sep 23, 2015, 10.16AM IST

		dateTime = dateTime.substring(0, dateTime.length() - 4);
		SimpleDateFormat dateFormatter = new SimpleDateFormat(
				"MMM d, yyyy, hh.mmaa", Locale.getDefault());
		try {
			Date date = dateFormatter.parse(dateTime);
			Calendar calender = Calendar.getInstance();
			calender.setTime(date);
			long time = calender.getTimeInMillis();
			long currentTime = System.currentTimeMillis();
			long difference = currentTime - time;
			return difference / (1000 * 60);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

}

