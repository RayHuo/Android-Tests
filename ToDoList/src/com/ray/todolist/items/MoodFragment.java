package com.ray.todolist.items;

import com.ray.todolist.R;
import com.ray.todolist.db.DataBaseHelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MoodFragment extends Fragment {

	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	private final String TABLENAME = "moods";
	
	private View rootView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.mood_layout, container, false);
		
		initDataBase();
		initView();
		prepareListener();
		
		return rootView;
	}
	
	
	private void initDataBase() {
		mDBHelper = new DataBaseHelper(getActivity());
		mSQLDB = mDBHelper.getReadableDatabase();		// 仅能读的数据库
	}
	
	
	private void initView() {
		
	}
	
	private void prepareListener() {
		
	}
	
	
	@Override
	public void onDestroy() {
		mSQLDB.close();
		super.onDestroy();
	}
}
