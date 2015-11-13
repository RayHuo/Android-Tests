package com.rayhuo.todolist.items;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.rayhuo.todolist.views.MoodItemViewActivity;

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

//	private static final int reqheight = 400;       // 目标高度
	private static final int reqwidth = 400;        // 目标宽度

	
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

			// 最后的 image 是一个路径，通过以让 ImageView 显示
			String img_path = cur.getString(cur.getColumnIndex("image"));
			tmp_map.put("mood_img", getCompressImage(img_path));
			tmp_map.put("mood_img_path", img_path);

			mDataList.add(tmp_map);
		}

		String[] mfrom = new String[] {"mark_time", "content", "mood_img"};
		int[] mto = new int[] {R.id.mood_date, R.id.mood_text, R.id.mood_img};
		mSimpleAdapter = new SimpleAdapter(getActivity(), mDataList, R.layout.mood_item, mfrom, mto);

		// 实现 SimpleAdapter 显示 bitmap 在 ImageView 上
		mSimpleAdapter.setViewBinder(new SimpleAdapter.ViewBinder() {
			@Override
			public boolean setViewValue(View view, Object data, String textRepresentation) {
				if(view instanceof ImageView && data instanceof Bitmap){
					ImageView i = (ImageView)view;
					i.setImageBitmap((Bitmap) data);
					return true;
				}
				return false;
			}
		});
	}


	private OnItemClickListener mItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
			Bundle args = new Bundle();
			Map<String, Object> m_item = mDataList.get(position);	// 当前点击项的内容

			args.putString("mood_image", m_item.get("mood_img_path").toString());
			args.putString("mood_content", m_item.get("content").toString());
			args.putString("mood_date", m_item.get("mark_time").toString());

			Intent intent = new Intent(getActivity(), MoodItemViewActivity.class);
			intent.putExtras(args);
			startActivity(intent);
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


	private Bitmap getCompressImage(String path) {
		BitmapFactory.Options opt = new BitmapFactory.Options();
		opt.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, opt);

		// 根据分辨率对资源进行压缩，减少OOM，这里边的目标高度和宽度不能太小，否则图片会失真。centerCrop本身还会做一层压缩的
		if (opt.outWidth >= reqwidth && reqwidth != 0) {
			opt.inSampleSize = opt.outWidth / reqwidth;
			opt.outHeight = (int)((double)opt.outHeight * ((double)reqwidth / (double)opt.outWidth));
			opt.outWidth = reqwidth;
		}
		else {
			opt.inSampleSize = 1;
		}

		opt.inPurgeable = true;
		opt.inJustDecodeBounds = false;

		Bitmap bitmap = BitmapFactory.decodeFile(path, opt);

		return bitmap;
	}




	@Override
	public void onDestroy() {
		mSQLDB.close();
		super.onDestroy();
	}
}
