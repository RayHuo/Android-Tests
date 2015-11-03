package com.ray.todolist.search;

import com.ray.todolist.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

public class SearchActivity extends Activity {
	
	private ImageView searchBack = null;
	private ImageView searchGo = null;
	private AutoCompleteTextView searchText = null;
	private String searchTableName = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_layout);
		
		initView();
		prepareListener();
	}
	
	private void initView() {
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		
		searchBack = (ImageView) findViewById(R.id.search_back);
		searchGo = (ImageView) findViewById(R.id.search_go);
		searchText = (AutoCompleteTextView) findViewById(R.id.search_text);
		
		searchTableName = getIntent().getStringExtra("table_name");
	}
	
	private void prepareListener() {
		searchBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				System.exit(0);
			}
		});
		
		
		searchGo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent mIntent = new Intent(SearchActivity.this, SearchResultActivity.class);
				mIntent.putExtra("search_text", searchText.getText().toString());
				mIntent.putExtra("table_name", searchTableName);
				startActivity(mIntent);
				
			}
		});
		
		
	}
	
	
	
}
