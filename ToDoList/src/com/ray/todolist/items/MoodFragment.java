package com.ray.todolist.items;

import com.ray.todolist.R;
import com.ray.todolist.db.DataBaseHelper;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.ImageView;

public class MoodFragment extends Fragment {

	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	private final String TABLENAME = "moods";
	
	private View rootView = null;
	private ImageView moodSearch = null;
	private ImageView moodAdd = null;
	
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
		moodSearch = (ImageView) rootView.findViewById(R.id.mood_search);
		moodAdd = (ImageView) rootView.findViewById(R.id.mood_add);
	}
	
	private void prepareListener() {
		
	}
	
	
	@Override
	public void onDestroy() {
		mSQLDB.close();
		super.onDestroy();
	}
}
