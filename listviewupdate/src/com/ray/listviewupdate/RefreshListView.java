package com.ray.listviewupdate;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RefreshListView extends ListView {

	View header = null;	// 对应的顶部布局文件
		
	public RefreshListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	
	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	
	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	
	// 初始化页面，添加顶部布局文件到Listview中
	public void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.header_layout, null);
		this.addHeaderView(header);	// 添加顶部布局文件
	}

}
