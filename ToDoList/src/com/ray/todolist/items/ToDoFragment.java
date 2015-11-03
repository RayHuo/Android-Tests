package com.ray.todolist.items;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.w3c.dom.CDATASection;

import com.ray.todolist.R;
import com.ray.todolist.add.AddToDoItemActivity;
import com.ray.todolist.db.DataBaseHelper;
import com.ray.todolist.search.SearchActivity;
import com.ray.todolist.views.ToDoItemActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
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
	
	private ImageView todoSearchView = null;
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
		prepareListener();
		
		return rootView;
	}
	
	
	private void initView() {
		todoSearchView = (ImageView) rootView.findViewById(R.id.to_do_search);
		addItemView = (ImageView) rootView.findViewById(R.id.add_to_do_item);
		mListView = (ListView) rootView.findViewById(R.id.to_do_list);
	}
	
	private void prepareListener() {
//		initListData();
//		mListView.setAdapter(mSimpleAdapter);
		initListData2();
		mListView.setAdapter(mSimpleAdapter);
		mListView.setOnItemClickListener(itemOnClickListener);
		
		addItemView.setOnClickListener(addItemClickListener);
		
		todoSearchView.setOnClickListener(searchClickListener);
	}
	
	// �����ģ������
	private void initListData() {
		String[] mfrom = {"to_do_item_background", "to_do_item_content", "to_do_create_time", "to_do_deadline", "to_do_importance", "to_do_emergency"};
		int[] mto = {R.id.to_do_item_background, R.id.to_do_item_content, R.id.to_do_create_time, R.id.to_do_deadline, R.id.to_do_importance, R.id.to_do_emergency};
		
		mToDoListData = new ArrayList<Map<String, Object>>();
		Map<String, Object> tmp_map = null;
		for(int i = 0; i < mCreateTime.length; i++) {
			tmp_map = new HashMap<String, Object>();
			tmp_map.put("to_do_item_background", mBackground[i]);
			tmp_map.put("to_do_item_content", mContents + i);
			tmp_map.put("to_do_create_time", mCreateTime[i]);
			tmp_map.put("to_do_deadline", mDeadline[i]);
			tmp_map.put("to_do_importance", mImportance[i]);
			tmp_map.put("to_do_emergency", mEmergency[i]);
			mToDoListData.add(tmp_map);
		}
		
		mSimpleAdapter = new SimpleAdapter(getActivity(), mToDoListData, R.layout.to_do_item, mfrom, mto);
	}
	
	private void initDataBase() {
		mDBHelper = new DataBaseHelper(getActivity());
		mSQLDB = mDBHelper.getReadableDatabase();
		
//		ContentValues cv;
//		for(int i = 0; i < mCreateTime.length; i++) {
//			cv = new ContentValues();
//	        cv.put("content", mContents + i);
////	        cv.put("background", mBackground[i]);
//	        cv.put("importance", mImportance2[i]);
//	        cv.put("emergency", mEmergency2[i]);
//	        cv.put("comment", "����" + i);
//	        mSQLDB.insert(TABLENAME, null, cv);
//		}
////        mSQLDB.close();
	}
	
	@SuppressWarnings("deprecation")
	private void initListData2() {
		// mfrom �е��ַ�����Ҫ�����ݿ����еĶ�Ӧһ��
//		String[] mfrom = {"content", "background", "create_time", "deadline", "importance", "emergency"};
//		int[] mto = {R.id.to_do_item_content, R.id.to_do_item_background, R.id.to_do_create_time, R.id.to_do_deadline, R.id.to_do_importance, R.id.to_do_emergency};
		
		/*
		 * item_id as _id ��Ϊ������ SimpleCursorAdapter���ض�������Ҫ����б�����һ���ֶ���Ϊ"_id"
		 * */
		Cursor cur = mSQLDB.rawQuery("select *, item_id as _id from " + TABLENAME, null);
		getActivity().startManagingCursor(cur);
//		mListAdapter = new SimpleCursorAdapter(getActivity(), R.layout.to_do_item, cur, mfrom, mto);
		
		String[] mfrom = {"to_do_item_background", "to_do_item_content", "to_do_create_time", "to_do_deadline", "to_do_importance", "to_do_emergency"};
		int[] mto = {R.id.to_do_item_background, R.id.to_do_item_content, R.id.to_do_create_time, R.id.to_do_deadline, R.id.to_do_importance, R.id.to_do_emergency};
		mToDoListData = new ArrayList<Map<String, Object>>();
		Map<String, Object> tmp_map = null;
		while(cur.moveToNext()) {
			tmp_map = new HashMap<String, Object>();
			
			tmp_map.put("to_do_id", cur.getInt(cur.getColumnIndex("_id")));	
			tmp_map.put("to_do_item_comment", cur.getString(cur.getColumnIndex("comment")));
			tmp_map.put("to_do_item_content", cur.getString(cur.getColumnIndex("content")));
			tmp_map.put("to_do_create_time", cur.getString(cur.getColumnIndex("create_time")));
			tmp_map.put("to_do_deadline", cur.getString(cur.getColumnIndex("deadline")));
			
			int ipot = cur.getInt(cur.getColumnIndex("importance"));
			int emg = cur.getInt(cur.getColumnIndex("emergency"));
			String ipotStr = ipot == 1 ? "��Ҫ" : "����Ҫ";
			String emgStr = emg == 1 ? "����" : "������";
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
			
			mToDoListData.add(tmp_map);
		}
		mSimpleAdapter = new SimpleAdapter(getActivity(), mToDoListData, R.layout.to_do_item, mfrom, mto);
		getActivity().stopManagingCursor(cur);

	}
	
	
	
	private OnItemClickListener itemOnClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> view, View parent, int position,
				long id) {
			// TODO Auto-generated method stub
			Map<String, Object> m_item = mToDoListData.get(position);	// ��ǰ����������
			
			Intent itemIntent = new Intent(getActivity(), ToDoItemActivity.class);
			itemIntent.putExtra("to_do_id", m_item.get("to_do_id").toString());
			itemIntent.putExtra("to_do_item_content", m_item.get("to_do_item_content").toString());
			itemIntent.putExtra("to_do_item_comment", m_item.get("to_do_item_comment").toString());
			itemIntent.putExtra("to_do_create_time", m_item.get("to_do_create_time").toString());
			itemIntent.putExtra("to_do_deadline", m_item.get("to_do_deadline").toString());
			itemIntent.putExtra("to_do_importance", m_item.get("to_do_importance").toString());
			itemIntent.putExtra("to_do_emergency", m_item.get("to_do_emergency").toString());
			
			startActivity(itemIntent);
		}
		
		
	};
	
	
	private OnClickListener addItemClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Intent addtodoItem = new Intent(getActivity(), AddToDoItemActivity.class);
//			startActivityForResult(addtodoItem, 0);
			startActivity(addtodoItem);
		}
	};
	
	
	private OnClickListener searchClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent mIntent = new Intent(getActivity(), SearchActivity.class);
			mIntent.putExtra("table_name", TABLENAME);
			getActivity().startActivity(mIntent);
			getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
		}
	};
	
	
	@Override
	public void onResume() {
		super.onResume();
		initListData2();
		mListView.setAdapter(mSimpleAdapter);
	}
	
	@Override
	public void onStop() {
		super.onStop();
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		mSQLDB.close();
	}
}
