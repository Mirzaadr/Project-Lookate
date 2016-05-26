package com.android.mirzaadr.lookate;

import java.util.ArrayList;
import java.util.HashMap;
 
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ListViewAdapter extends BaseAdapter {
 
	// Declare Variables
	Context context;
	LayoutInflater inflater;
	ArrayList<HashMap<String, String>> data;
	ImageLoader imageLoader;
	HashMap<String, String> resultp = new HashMap<String, String>();
 
	public ListViewAdapter(Context context,
			ArrayList<HashMap<String, String>> arraylist) {
		this.context = context;
		data = arraylist;
		imageLoader = new ImageLoader(context);
	}
 
	@Override
	public int getCount() {
		return data.size();
	}
 
	@Override
	public Object getItem(int position) {
		return null;
	}
 
	@Override
	public long getItemId(int position) {
		return 0;
	}
 
	@SuppressLint("ViewHolder") public View getView(final int position, View convertView, ViewGroup parent) {
		// Declare Variables
		TextView rank;
		TextView country;
		TextView population;
        TextView ide;
		ImageView flag;
 
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View itemView = inflater.inflate(R.layout.list_view, parent, false);
		// Get the position
		resultp = data.get(position);
 
		// Locate the TextViews in listview_item.xml
		rank = (TextView) itemView.findViewById(R.id.id);
		country = (TextView) itemView.findViewById(R.id.entry);
		population = (TextView) itemView.findViewById(R.id.meaning);
        ide = (TextView) itemView.findViewById(R.id.idsearch);

 
		// Locate the ImageView in listview_item.xml
		flag = (ImageView) itemView.findViewById(R.id.image);
 
		// Capture position and set results to the TextViews
		rank.setText(resultp.get(detilmakanan.TAG_NAMA));
		country.setText(resultp.get(detilmakanan.TAG_ALAMAT));
		population.setText(resultp.get(detilmakanan.TAG_CP));
        ide.setText(resultp.get(SearchResultsActivity.TAG_ID));
		// Capture position and set results to the ImageView
		// Passes flag images URL into ImageLoader.class
		imageLoader.DisplayImage(resultp.get(detilmakanan.TAG_IMAGE), flag);
		// Capture ListView item click
		return itemView;
	}
}