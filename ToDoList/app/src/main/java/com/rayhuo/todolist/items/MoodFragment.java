package com.rayhuo.todolist.items;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.rayhuo.todolist.R;
import com.rayhuo.todolist.add.AddMoodActivity;
import com.rayhuo.todolist.db.DataBaseHelper;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




public class MoodFragment extends Fragment {

	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	private final String TABLENAME = "moods";
	
	private View rootView = null;
	private ImageView moodSearch = null;
	private ImageView moodAdd = null;
	
	private ListView moodList = null;

	private SimpleAdapter mSimpleAdapter = null;
	private List<Map<String, Object>> mDataList = null;

	
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
		moodList = (ListView) rootView.findViewById(R.id.mood_list);
	}
	
	private void prepareListener() {
		initListData();
		moodList.setAdapter(mSimpleAdapter);
		moodList.setOnItemClickListener(mItemClickListener);

		moodAdd.setOnClickListener(addClickListener);
		moodSearch.setOnClickListener(searchClickListener);
	}

	private void initListData() {
		String img_path2 = getStoragePath();
		// 提取数据并转化为最后可用的数据
		Cursor cur = mSQLDB.rawQuery("select *, mood_id as _id from " + TABLENAME, null);
		getActivity().startManagingCursor(cur);

		mDataList = new ArrayList<Map<String, Object>>();
		Map<String, Object> tmp_map = null;
		while(cur.moveToNext()) {
			tmp_map = new HashMap<String, Object>();

			tmp_map.put("mood_id", cur.getInt(cur.getColumnIndex("_id")));
			tmp_map.put("mark_time", cur.getString(cur.getColumnIndex("mark_time")));
			tmp_map.put("content", cur.getString(cur.getColumnIndex("content")));

			// 最后的 image 是一个 drawable 对象，以让 ImageView 显示
			String img_path = getStoragePath() + "/" + cur.getString(cur.getColumnIndex("image"));	// 后者只保留照片的名字
			tmp_map.put("mood_img", cur.getString(cur.getColumnIndex("image")));
			mDataList.add(tmp_map);
		}

		String[] mfrom = new String[] {"mark_time", "content", "mood_img"};
		int[] mto = new int[] {R.id.mood_date, R.id.mood_text, R.id.mood_img};
		mSimpleAdapter = new SimpleAdapter(getActivity(), mDataList, R.layout.mood_item, mfrom, mto);

	}


	private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

		}
	};


	private OnClickListener addClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {
			Intent intent = new Intent(getActivity(), AddMoodActivity.class);
			startActivity(intent);
		}
	};

	private OnClickListener searchClickListener = new OnClickListener() {

		@Override
		public void onClick(View view) {

		}
	};


	/*
	* 判断手机是否有扩展SD卡：
	* 有：返回SD卡上的存储图片路径
	* 无：返回本地存储图片的路径
	* */
	private String getStoragePath() {
		File pathFolder =  new File(getActivity().getFilesDir().toString() + "/ToDoDICM/");

		if (!pathFolder.exists()) { // 如果目录不存在，则创建一个名为"ToDoDICM"的目录
			pathFolder.mkdir();
		}

//		String status = Environment.getExternalStorageState();
//		// 存在外部SD卡时
//		if(status.equals(Environment.MEDIA_MOUNTED)) {
//			path = Environment.getExternalStorageDirectory().toString();
//		}
//		// 不存在外部SD卡时
//		else {
//			path = getActivity().getFilesDir().toString();
//		}
//		path = getActivity().getFilesDir().toString();

		return pathFolder.getPath();
	}


	@Override
	public void onDestroy() {
		mSQLDB.close();
		super.onDestroy();
	}
}
