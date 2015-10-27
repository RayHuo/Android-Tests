package com.ray.todolist;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ray.todolist.views.SlideMenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	private ImageView switchImage = null;
	private SlideMenu mSlideMenu = null;
	
	private ListView mListView = null;
	private SimpleAdapter mSimpleAdapter = null;
	
	private String[] itemStrings = {"待办事项", "学习计划", "心情", "日常闹钟", "设置"};
	private int[] itemImages = {R.drawable.ic_to_do, R.drawable.ic_learning_plan, 
			R.drawable.ic_mood, R.drawable.ic_notifications, R.drawable.ic_settings};
	
	private List<Map<String, Object>> dataList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		initListener();
	}
	
	
	private void initView() {		
		switchImage = (ImageView) findViewById(R.id.switch_image);
		mSlideMenu = (SlideMenu) findViewById(R.id.slide_menu);
		mListView = (ListView) findViewById(R.id.main_items);
	}
	
	private void initListener() {
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
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long id) {
				// TODO Auto-generated method stub
				Toast.makeText(MainActivity.this, itemStrings[position], Toast.LENGTH_SHORT).show();
			}
		});
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
	
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
	
	@Override
	protected void onResume() {
		super.onResume();
	}
	
}
