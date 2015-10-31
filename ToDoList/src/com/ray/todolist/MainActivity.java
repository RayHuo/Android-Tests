package com.ray.todolist;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ray.todolist.items.LearningFragment;
import com.ray.todolist.items.MoodFragment;
import com.ray.todolist.items.NotificationFragment;
import com.ray.todolist.items.SettingsActivity;
import com.ray.todolist.items.ToDoFragment;
import com.ray.todolist.views.SlideMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.*;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends FragmentActivity {
	
	private ImageView switchImage = null;
	private SlideMenu mSlideMenu = null;
	
	private ListView mListView = null;
	private SimpleAdapter mSimpleAdapter = null;
	private RelativeLayout mMainView = null;
	
	private String[] itemStrings = {"待办事项", "学习计划", "心情", "日常闹钟", "设置"};
	private int[] itemImages = {R.drawable.ic_to_do, R.drawable.ic_learning_plan, 
			R.drawable.ic_mood, R.drawable.ic_notifications, R.drawable.ic_settings};
	
	// "待办事项", "学习计划", "心情", "日常闹钟", "设置"，前四个对应一个单独的layout，最后一个对应一个activity
	private int[] itemLayouts = {R.layout.to_do_layout, R.layout.learning_layout, R.layout.mood_layout, R.layout.notification_layout};
	
	private List<Map<String, Object>> dataList = null;
	
	private LayoutInflater inflater = null;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		prepareListener();
	}
	
	
	private void initView() {	
		mMainView = (RelativeLayout) findViewById(R.id.main_view);
		switchImage = (ImageView) findViewById(R.id.switch_image);
		mSlideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		mListView = (ListView) findViewById(R.id.main_items);
		inflater = getLayoutInflater();
	}
	
	private void prepareListener() {
		switchImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mSlideMenu.toggle();
			}
		});
		
		initListData();
		mListView.setAdapter(mSimpleAdapter);
		mListView.setDivider(null);
		mListView.setOnItemClickListener(mOnItemClickListener);
		
		// 默认显示“待办事项”
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.real_fragment, new ToDoFragment());
        ft.commit();
	}

	private void initListData() {
		String[] mfrom = {"main_item_icon", "main_item"};
		int[] mto = {R.id.main_item_icon, R.id.main_item};
		
		dataList = new ArrayList<Map<String, Object>>();
		
		for(int i = 0; i < itemStrings.length; i++) {
			Map<String, Object> tmp_map = new HashMap<String, Object>();
			tmp_map.put("main_item_icon", itemImages[i]);
			tmp_map.put("main_item", itemStrings[i]);
			dataList.add(tmp_map);
		}
		
		mSimpleAdapter  = new SimpleAdapter(this, dataList, R.layout.main_item, mfrom, mto);
	}
	
	
	@SuppressLint("InflateParams")
	private OnItemClickListener mOnItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// TODO Auto-generated method stub
			Toast.makeText(MainActivity.this, itemStrings[position] + ", " + mMainView.getChildCount(), Toast.LENGTH_SHORT).show();
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
			
			switch(position) {
				case 0:
//					LinearLayout todoLayout = (LinearLayout) inflater.inflate(R.layout.to_do_layout, null);
//					if(mMainView.getChildCount() > 1) {
//						mMainView.removeViews(0, mMainView.getChildCount()-1);
//					}
//					mMainView.addView(todoLayout, 0);
			        ft.replace(R.id.real_fragment, new ToDoFragment());
			        ft.commit();
					break;
				case 1:
					ft.replace(R.id.real_fragment, new LearningFragment());
			        ft.commit();
					break;
				case 2:
					ft.replace(R.id.real_fragment, new MoodFragment());
			        ft.commit();
					break;
				case 3:
					ft.replace(R.id.real_fragment, new NotificationFragment());
			        ft.commit();
					break;
				case 4:
					Intent gotoSettingIntent = new Intent(MainActivity.this, SettingsActivity.class);
					startActivity(gotoSettingIntent);
					break;
				default:
					break;
			}
			
		}
	};

	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	@Override
	protected void onPause() {
	    // TODO Auto-generated method stub
	    super.onPause();
	}
	
	@Override
	protected void onStop() {
	    // TODO Auto-generated method stub
	    super.onStop();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
}
