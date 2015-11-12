package com.rayhuo.todolist.items;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.rayhuo.todolist.R;

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
