package com.ray.todolist.items;

import com.ray.todolist.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class SettingsActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.settings_layout);
		
		initView();
		prepareListener();
	}

	private void initView() {
		
	}
	
	private void prepareListener() {
		
	}
}
