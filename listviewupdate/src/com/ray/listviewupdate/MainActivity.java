package com.ray.listviewupdate;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	
	private String data[] = {"hello", "android", "google", "Tencent", 
			"what", "hell", "nice", "monday", "yolanda", "ray", 
			"stephen", "curry", "nba final", "history", "gundam"};
	
	private RefreshListView m_listView = null;
	private ListAdapter m_listAdapter = null;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		m_listView = (RefreshListView)findViewById(R.id.listView1);
		m_listAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_single_choice, data);
		m_listView.setAdapter(m_listAdapter);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
