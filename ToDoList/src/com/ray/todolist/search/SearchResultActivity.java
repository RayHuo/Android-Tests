package com.ray.todolist.search;

import com.ray.todolist.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SearchResultActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_result_layout);
		
		initView();
		prepareListener();
	}
	
	private void initView() {
		
	}
	
	private void prepareListener() {
		
	}
	
	private void searchItems(String text) {
		
	}
}
