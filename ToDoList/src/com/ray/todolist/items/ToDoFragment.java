package com.ray.todolist.items;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.CDATASection;

import com.ray.todolist.R;
import com.ray.todolist.db.DataBaseHelper;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;

public class ToDoFragment extends Fragment {
	
	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	private final String TABLENAME = "to_do_items";
	
	private View rootView = null;
	
	private SearchView todoSearchView = null;
	private ImageView addItemView = null;
	private ListView mListView = null;
	private SimpleAdapter mSimpleAdapter = null;
	
	private List<Map<String, Object>> mToDoListData = null;
	private String mContents = "�������ݣ�������Ҫ�Ĳ������ݰ����벻Ҫ�ҷ���������ǰѶ�����Ū�꣬�ͺ���";
	private String[] mCreateTime = {"2015-10-22", "2015-10-23", "2015-10-24", "2015-10-25", "2015-10-26", "2015-10-27", "2015-10-28"};
	private String[] mDeadline = {"2015-10-23", "2015-10-24", "2015-10-25", "2015-10-26", "2015-10-27", "2015-10-28", "2015-10-29"};
	private String[] mImportance = {"��Ҫ", "����Ҫ", "��Ҫ", "����Ҫ", "��Ҫ", "����Ҫ", "����Ҫ"};
	private String[] mEmergency = {"������", "����", "����", "������", "����", "����", "������"};
	private int[] mBackground = {R.drawable.important_noemergency, R.drawable.noimportant_emergency, R.drawable.important_emergency, 
								 R.drawable.noimportant_noemergency, R.drawable.important_emergency, R.drawable.noimportant_emergency, 
								 R.drawable.noimportant_noemergency};
	
	private boolean[] mImportance2 = {true, false, true, false, true, false, false};
	private boolean[] mEmergency2 = {false, true, true, false, true, true, false};
	private ListAdapter mListAdapter = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.to_do_layout, container, false);
		
		initDataBase();
		initView();
		initListener();
		
		mSQLDB.close();
		return rootView;
	}
	
	
	private void initView() {
		todoSearchView = (SearchView) rootView.findViewById(R.id.to_do_search);
		addItemView = (ImageView) rootView.findViewById(R.id.add_to_do_item);
		mListView = (ListView) rootView.findViewById(R.id.to_do_list);
	}
	
	private void initListener() {
//		initListData();
//		mListView.setAdapter(mSimpleAdapter);
		initListData2();
		mListView.setAdapter(mListAdapter);
	}
	
	private void initListData() {
		String[] mfrom = {"to_do_item_background", "to_do_item_content", "to_do_create_time", "to_do_deadline", "to_do_importance", "to_do_emergency"};
		int[] mto = {R.id.to_do_item_background, R.id.to_do_item_content, R.id.to_do_create_time, R.id.to_do_deadline, R.id.to_do_importance, R.id.to_do_emergency};
		
		mToDoListData = new ArrayList<Map<String, Object>>();
		Map<String, Object> tmp_map = null;
		for(int i = 0; i < mCreateTime.length; i++) {
			tmp_map = new HashMap<String, Object>();
			tmp_map.put("to_do_item_background", mBackground[i]);
			tmp_map.put("to_do_item_content", mContents + i);
			tmp_map.put("to_do_create_time", "����ʱ�� : " + mCreateTime[i]);
			tmp_map.put("to_do_deadline", "����ʱ�� : " + mDeadline[i]);
			tmp_map.put("to_do_importance", mImportance[i]);
			tmp_map.put("to_do_emergency", mEmergency[i]);
			mToDoListData.add(tmp_map);
		}
		
		mSimpleAdapter = new SimpleAdapter(getActivity(), mToDoListData, R.layout.to_do_item, mfrom, mto);
	}
	
	private void initDataBase() {
		mDBHelper = new DataBaseHelper(getActivity());
		mSQLDB = mDBHelper.getReadableDatabase();
		
		ContentValues cv;
		for(int i = 0; i < mCreateTime.length; i++) {
			cv = new ContentValues();
	        cv.put("content", mContents + i);
	        cv.put("background", mBackground[i]);
	        cv.put("importance", mImportance2[i]);
	        cv.put("emergency", mEmergency2[i]);
	        cv.put("comment", "����" + i);
	        mSQLDB.insert(TABLENAME, null, cv);
		}
//        mSQLDB.close();
	}
	
	@SuppressWarnings("deprecation")
	private void initListData2() {
		// mfrom �е��ַ�����Ҫ�����ݿ����еĶ�Ӧһ��
		String[] mfrom = {"content", "background", "create_time", "deadline", "importance", "emergency"};
		int[] mto = {R.id.to_do_item_content, R.id.to_do_item_background, R.id.to_do_create_time, R.id.to_do_deadline, R.id.to_do_importance, R.id.to_do_emergency};
		
		/*
		 * item_id as _id ��Ϊ������ SimpleCursorAdapter���ض�������Ҫ����б�����һ���ֶ���Ϊ"_id"
		 * */
		Cursor cur = mSQLDB.rawQuery("select *, item_id as _id from " + TABLENAME, null);
		// ���ʵ��û�а취�ͱ���cur��������ߵ����ݸ�ֵ��һ�� Map<String, Object> ����
		getActivity().startManagingCursor(cur);
		mListAdapter = new SimpleCursorAdapter(getActivity(), R.layout.to_do_item, cur, mfrom, mto);
	}
	
	
	@Override
	public void onStop() {
//		mSQLDB.close();
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
//		mSQLDB.close();
		super.onDestroy();
	}
}
