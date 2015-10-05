package com.example.toiassignment;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ListViewAdapter extends BaseAdapter {
	Context context;
	ArrayList<HashMap<String, String>> data;
	HashMap<String, String> result;

	LayoutInflater inflater;

	public ListViewAdapter(MainActivity mainActivity,
			ArrayList<HashMap<String, String>> arraylist) {
		// TODO Auto-generated constructor stub
		this.context = mainActivity;
		this.data = arraylist;
		Log.d("Nitin", "ArrayList" + data.toString());
		// loader=new ImageLoader(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View converView, ViewGroup parent) {
		// TODO Auto-generated method stub
		TextView title;
		TextView desc;
		ImageView img;

		inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		View itemView = inflater.inflate(R.layout.list_item, parent, false);
		result = data.get(position);
		// Log.d("Nitin", "Result" + result.toString());
		title = (TextView) itemView.findViewById(R.id.text);

		desc = (TextView) itemView.findViewById(R.id.desc);
		img = (ImageView) itemView.findViewById(R.id.image);

		title.setText(result.get(MainActivity.HEAD));

		long timeDifference = Utils.getTimeDifference(result
				.get(MainActivity.DATE));
		if ((result.get(MainActivity.BYLINE) != null) && timeDifference < 60) {

			desc.setText(result.get(MainActivity.BYLINE) + " - "
					+ timeDifference + " minutes ago");

		}
		desc.setTextColor(Color.parseColor("#888888"));
		desc.setTypeface(null, Typeface.ITALIC);

		if (result.get(MainActivity.THUMB) != null) {
			Picasso.with(context).load(result.get(MainActivity.THUMB))
					.into(img);
		}
		return itemView;
	}

}
