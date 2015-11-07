package com.ray.todolist.search;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ray.todolist.R;
import com.ray.todolist.db.DataBaseHelper;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchResultActivity extends Activity {

	private ImageView searchResultBack = null;
	private ListView searchResultList = null;
	
	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	
	private List<Map<String, Object>> mSearchResult = null;
	private SimpleAdapter mSimpleAdapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.search_result_layout);
		
		initDataBase();
		initView();
		prepareListener();
	}
	
	private void initDataBase() {
		mDBHelper = new DataBaseHelper(this);
		mSQLDB = mDBHelper.getWritableDatabase();
	}
	
	private void initView() {
		searchResultBack = (ImageView) findViewById(R.id.search_result_back);
		searchResultList = (ListView) findViewById(R.id.search_result_list);
	}
	
	private void prepareListener() {
		
		searchResultBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		Bundle args = getIntent().getExtras();
		String searchText = args.getString("search_text");
		String tableName = args.getString("table_name");
		searchItems(searchText, tableName);
		searchResultList.setAdapter(mSimpleAdapter);
	}
	
	private void searchItems(String text, String tableName) {
		String execution = "select *, item_id as _id from " + tableName + " where content like \"%" + text +  "%\"";
		System.out.println(execution);
		Cursor cur = mSQLDB.rawQuery(execution, null);
		startManagingCursor(cur);
//		mListAdapter = new SimpleCursorAdapter(getActivity(), R.layout.to_do_item, cur, mfrom, mto);
		
		String[] mfrom = {"to_do_item_background", "to_do_item_content", "to_do_create_time", "to_do_deadline", "to_do_importance", "to_do_emergency"};
		int[] mto = {R.id.to_do_item_background, R.id.to_do_item_content, R.id.to_do_create_time, R.id.to_do_deadline, R.id.to_do_importance, R.id.to_do_emergency};
		mSearchResult = new ArrayList<Map<String, Object>>();
		Map<String, Object> tmp_map = null;
		while(cur.moveToNext()) {
			tmp_map = new HashMap<String, Object>();
			
			tmp_map.put("to_do_item_content", cur.getString(cur.getColumnIndex("content")));
			tmp_map.put("to_do_create_time", "创建时间 : " + cur.getString(cur.getColumnIndex("create_time")));
			tmp_map.put("to_do_deadline", "截至时间 : " + cur.getString(cur.getColumnIndex("deadline")));
			
			int ipot = cur.getInt(cur.getColumnIndex("importance"));
			int emg = cur.getInt(cur.getColumnIndex("emergency"));
			String ipotStr = ipot == 1 ? "重要" : "不重要";
			String emgStr = emg == 1 ? "紧急" : "不紧急";
			tmp_map.put("to_do_importance", ipotStr);
			tmp_map.put("to_do_emergency", emgStr);
			
			int backgroundColor = -1;
			if(ipot == 1 && emg == 1) {
				backgroundColor = R.drawable.important_emergency;
			}
			if(ipot == 1 && emg == 0) {
				backgroundColor = R.drawable.important_noemergency;
			}
			if(ipot == 0 && emg == 1) {
				backgroundColor = R.drawable.noimportant_emergency;
			}
			if(ipot == 0 && emg == 0) {
				backgroundColor = R.drawable.noimportant_noemergency;
			}
			tmp_map.put("to_do_item_background", backgroundColor);
			
			mSearchResult.add(tmp_map);
		}
		mSimpleAdapter = new SimpleAdapter(this, mSearchResult, R.layout.to_do_item, mfrom, mto);
		stopManagingCursor(cur);
	}
	
	
	@Override
	public void onDestroy() {
		mSQLDB.close();
		super.onDestroy();
	}
	
}
