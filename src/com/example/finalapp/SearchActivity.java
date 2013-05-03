package com.example.finalapp;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class SearchActivity extends Activity {

	TextView textview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
Log.v("CBP","created");
		Bundle extras = getIntent().getExtras();
		if (extras == null) {
			return;
		}
		// Get data via the key
		String SearchQ = extras.getString("SearchQ");
		if (SearchQ != null) {
			performSearch(SearchQ);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	
	public void performSearch(String query){
		
		//perform the search, for now just update the text view for testing
		textview = (TextView) findViewById(R.id.searchview);
		textview.setText(query);
		
	}
	
}
