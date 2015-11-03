package com.ray.todolist.add;

import com.ray.todolist.R;
import com.ray.todolist.db.DataBaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;

public class AddNotificationActivity extends Activity {
	
	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	private final String TABLENAME = "notifications";
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_notification_layout);
		
		initDataBase();
		initView();
		prepareListener();
		
	}
	
	
	private void initDataBase() {
		mDBHelper = new DataBaseHelper(this);
		mSQLDB = mDBHelper.getWritableDatabase();
	}
	
	
	private void initView() {
		
	}
	
	
	private void prepareListener() {
		
	}
	
}
