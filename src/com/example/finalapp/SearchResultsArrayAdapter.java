package com.example.finalapp;

import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.example.finalapp.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class SearchResultsArrayAdapter extends ArrayAdapter<JSONObject> {
	private final Context context;
	private ArrayList<JSONObject> values;
 
	public SearchResultsArrayAdapter(Context context, ArrayList<JSONObject> values) {
		super(context, R.layout.list_search_results, values);
		this.context = context;
		this.values = values;
	}
 
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
 
		View rowView = inflater.inflate(R.layout.list_search_results, parent, false);
		TextView textView = (TextView) rowView.findViewById(R.id.recipeName);
		try {
			AsyncTask<String, Void, Bitmap> image = new DownloadImageTask((ImageView) rowView.findViewById(R.id.smallImage)).execute(values.get(position).getJSONArray("smallImageUrls").get(0).toString());
			textView.setText(values.get(position).getString("recipeName"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
 
		// Change icon based on name
		//String s = values[position];
 
		//System.out.println(s);
 
		/*if (s.equals("WindowsMobile")) {
			imageView.setImageResource(R.drawable.windowsmobile_logo);
		} else if (s.equals("iOS")) {
			imageView.setImageResource(R.drawable.ios_logo);
		} else if (s.equals("Blackberry")) {
			imageView.setImageResource(R.drawable.blackberry_logo);
		} else {
			imageView.setImageResource(R.drawable.android_logo);
		}*/
 
		return rowView;
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
}