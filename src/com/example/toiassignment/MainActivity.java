package com.example.toiassignment;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

public class MainActivity extends Activity {
	ListView listview;
	ListViewAdapter adapter;
	ProgressDialog mProgressDialog;
	JSONArray jArray;
	JSONObject jSonObject;
	ArrayList<HashMap<String, String>> arraylist;
	public static final String ITEM = "NewsItem";
	public static final String HEAD = "HeadLine";
	public static final String DATE = "DateLine";
	public static final String IMAGE = "Image";
	public static final String THUMB = "Thumb";
	public static final String BYLINE = "ByLine";
	File dir;
	File file;
	String FEED_FILE = "/feed.txt";
	String FOLDER = "/TOIFEED";

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		dir = new File(Environment.getExternalStorageDirectory() + FOLDER);
		if (!dir.exists() || !dir.isDirectory()) {
			dir.mkdir();
		}
		file = new File(dir + FEED_FILE);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		String url = "http://timesofindia.indiatimes.com/feeds/newsdefaultfeeds.cms?feedtype=sjson";
		new DownloadXmlTask(MainActivity.this, url).execute();
	}

	public class DownloadXmlTask extends AsyncTask<Void, Void, Void> {
		private Context context;
		ProgressDialog progressDialog;
		ListView listView;
		ArrayList<HashMap<String, String>> arraylist;
		MainActivity activity;
		String url;

		public DownloadXmlTask(MainActivity mainActivity, String url) {
			this.context = mainActivity;
			this.activity = mainActivity;
			this.url = url;

		}

		private void loadJsonFromNetwork() throws IOException {

			DataRetrieval objDate = new DataRetrieval();
			JSONObject json;
			arraylist = new ArrayList<HashMap<String, String>>();
			InputStream iStream = null;
			GenericJsonParser jSonParse = new GenericJsonParser();

			iStream = objDate.downloadUrl(url, context);

			json = jSonParse.parse(iStream);
			Utils.writeToFile(json, file);

			try {

				jArray = json.getJSONArray(ITEM);
				for (int i = 0; i < jArray.length(); i++) {
					HashMap<String, String> data = new HashMap<String, String>();
					jSonObject = jArray.getJSONObject(i);

					data.put(HEAD, jSonObject.optString(HEAD));
					data.put(DATE, jSonObject.optString(DATE));

					JSONObject imageObject = jSonObject.optJSONObject(IMAGE);
					if (imageObject.has(THUMB)) {
						String thumb = imageObject.optString(THUMB);
						data.put(THUMB, thumb);

					}
					if (jSonObject.has(BYLINE)) {
						data.put(BYLINE, jSonObject.optString(BYLINE));
					}
					arraylist.add(data);
				}

			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

		}

		@Override
		protected Void doInBackground(Void... arg0) {
			// TODO Auto-generated method stub

			ConnectivityManager connMgr = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected())
				try {
					loadJsonFromNetwork();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			else {
				Log.d("Nitin", "No netwoork");
				loadJsonFromStorage();
			}

			return null;
		}

		private void loadJsonFromStorage() {
			// TODO Auto-generated method stub
			JSONObject json = Utils.readFromFile(file);
			arraylist = new ArrayList<HashMap<String, String>>();
			Log.d("Nitin", "" + json);
			try {

				jArray = json.getJSONArray(ITEM);
				for (int i = 0; i < jArray.length(); i++) {
					HashMap<String, String> data = new HashMap<String, String>();
					jSonObject = jArray.getJSONObject(i);
					data.put(HEAD, jSonObject.optString(HEAD));
					data.put(DATE, jSonObject.optString(DATE));

					JSONObject imageObject = jSonObject.optJSONObject(IMAGE);
					if (imageObject.has(THUMB)) {
						String thumb = imageObject.optString(THUMB);
						data.put(THUMB, thumb);

					}
					if (jSonObject.has(BYLINE)) {
						data.put(BYLINE, jSonObject.optString(BYLINE));
					}
					arraylist.add(data);
				}

			} catch (JSONException e) {
				Log.e("Error", e.getMessage());
				e.printStackTrace();
			}

		}

		@Override
		protected void onPostExecute(Void result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);

			listView = (ListView) findViewById(R.id.listView);
			// Log.d("Nitin", "ArrayList" + arraylist.toString());
			ListViewAdapter adapter = new ListViewAdapter(activity, arraylist);

			listView.setAdapter(adapter);

			progressDialog.dismiss();

		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Loading");
			progressDialog.show();

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		return super.onOptionsItemSelected(item);

	}

}
