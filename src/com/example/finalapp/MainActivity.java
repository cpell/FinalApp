package com.example.finalapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	Button mButton;
	EditText mEdit;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mButton = (Button) findViewById(R.id.button1);
		mEdit = (EditText) findViewById(R.id.editText1);

		mButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Log.v("EditText", mEdit.getText().toString());
				Intent i = new Intent(MainActivity.this, SearchActivity.class);
				i.putExtra("SearchQ", mEdit.getText().toString());
				i.putExtra("Value2", "This value two ActivityTwo");
				// Set the request code to any code you like, you can identify
				// the
				// callback via this code
				startActivity(i);
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
