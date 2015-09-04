package com.ray.listviewupdate;

import java.util.ArrayList;

import com.ray.listviewupdate.RefreshListView.IRrefreshListener;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity implements IRrefreshListener {
	
	private int index = 1;
	private String data[] = {"hello", "android", "google", "Tencent", 
			"what", "hell", "nice", "monday", "yolanda", "ray", 
			"stephen", "curry", "nba final", "history", "gundam"};
	private ArrayList<String> showdata;
	
	private RefreshListView m_listView = null;
	private ListAdapter m_listAdapter = null;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		showdata = new ArrayList<String>();
		for (String d: data) {
			showdata.add(d);
		}
		
		showList();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void showList() {
		m_listView = (RefreshListView)findViewById(R.id.listView1);
		m_listView.setInterface(this);
		m_listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_single_choice, showdata);
		m_listView.setAdapter(m_listAdapter);
	}
	
	private void setRefreshData() {
		for(int i = 0; i < 2; i++) {
			showdata.add(0,"index" + String.valueOf(index++));	// 放在最上面
		}
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		// 获取最新的数据
		setRefreshData();
		// 通知界面显示数据
		showList();
		// 通知Listview刷新数据完成
		m_listView.reflashComplete();
	}

}
