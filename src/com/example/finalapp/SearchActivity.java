package com.example.finalapp;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ListActivity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.finalapp.SearchResultsArrayAdapter;

public class SearchActivity extends ListActivity {

	ArrayList<JSONObject> RecipesArray = new ArrayList<JSONObject>();

	JSONObject jsonResponse;

	TextView textview;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		// setContentView(R.layout.list_search_results);

		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		// Get data via the key
		String SearchQ = extras.getString("SearchQ");
		if (SearchQ != null) {
			// performSearch(SearchQ);
			HttpRequestTask handle = new HttpRequestTask();
			handle.execute(SearchQ);
		}

		setListAdapter(new SearchResultsArrayAdapter(this, RecipesArray));

	}

	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {

		// get selected items
		String selectedValue = (String) getListAdapter().getItem(position);
		Toast.makeText(this, selectedValue, Toast.LENGTH_SHORT).show();

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	public void onBackPressed() {
		finish();
		
	}

	private class HttpRequestTask extends AsyncTask<String, Void, String> {
		@Override
		protected String doInBackground(String... query) {
			String response = "";
			String url = "http://api.yummly.com/v1/api/recipes?_app_id=d8395b4e&_app_key=7e908a7401f82e0cefcd6359a549a2ab&q="+query[0];
			Log.v("CBP",url);
			DefaultHttpClient client = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			try {
				HttpResponse execute = client.execute(httpGet);
				InputStream content = execute.getEntity().getContent();

				BufferedReader buffer = new BufferedReader(
						new InputStreamReader(content));
				String s = "";
				while ((s = buffer.readLine()) != null) {
					response += s;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
			return response;
		}

		@Override
		protected void onPostExecute(String result) {
			// textView.setText(result);
			try {
				setJson(result);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return;
		}

	}

	private void setJson(String response) throws JSONException {
		// TODO Auto-generated method stub
		jsonResponse = null;
		if (response != null) {
			jsonResponse = new JSONObject(response);

			updateList();
		}
	}

	private void updateList() {
		RecipesArray.clear();
		try {
			JSONArray jArray = jsonResponse.getJSONArray("matches");

			for (int i = 0; i < jArray.length(); i++) {
				JSONObject oneObject = jArray.getJSONObject(i);
				// Pulling items from the array
				String recipeName = oneObject.getString("recipeName");
				String imageUrl = oneObject.getJSONArray("smallImageUrls").get(0).toString();
				RecipesArray.add(oneObject);
				Log.v("CBP", "recipe name: " + recipeName + "&  image: "
						+ imageUrl);
				
			}
			

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setListAdapter(new SearchResultsArrayAdapter(this, RecipesArray));
	}

}
