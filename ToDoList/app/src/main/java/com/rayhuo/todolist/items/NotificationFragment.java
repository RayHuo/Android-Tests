package com.rayhuo.todolist.items;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rayhuo.todolist.add.AddNotificationActivity;
import com.rayhuo.todolist.db.DataBaseHelper;
import com.rayhuo.todolist.R;

import java.util.List;
import java.util.Map;

public class NotificationFragment extends Fragment {
	
	private View rootView = null;
	
	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	private final String TABLENAME = "notifications";
	
	private ImageView addNotify = null;
	private ListView mNotifyList = null;
	private SimpleAdapter mSimpleAdapter = null;
	private List<Map<String, Object>> dataList = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.notification_layout, container, false);
		
		initDataBase();
		initView();
		prepareListener();
		
		return rootView;
	}
	
	
	private void initDataBase() {
		mDBHelper = new DataBaseHelper(getActivity());
		mSQLDB = mDBHelper.getWritableDatabase();
	}
	
	
	private void initView() {
		addNotify = (ImageView) rootView.findViewById(R.id.add_notification_img);
		mNotifyList = (ListView) rootView.findViewById(R.id.notification_list);
	}
	
	
	private void prepareListener() {
		addNotify.setOnClickListener(mANListener);
		
		
	}
	
	
	private OnClickListener mANListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Intent addNotifyIntent = new Intent(getActivity(), AddNotificationActivity.class);
			startActivity(addNotifyIntent);
		}
	};
	
	
	@Override
	public void onDestroy() {
		mSQLDB.close();
		super.onDestroy();
	}
	
}
