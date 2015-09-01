package com.example.myviewpager;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnPageChangeListener {
	
	private ViewPager m_ViewPager = null;
	private ImageView[] m_circles;	// 存放换页小圆点
	private ImageView[] m_imageView;	// 存放每个tab放的图片
	private int[] m_imgIDArray;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ViewGroup m_viewGroup = (ViewGroup)findViewById(R.id.viewGroup);
		m_ViewPager = (ViewPager)findViewById(R.id.viewPager);
		
		// 载入图片资源ID
		m_imgIDArray = new int[] {R.drawable.item01, R.drawable.item02, 
				R.drawable.item03, R.drawable.item04, R.drawable.item05};
		
		// 将小圆圈加入到viewGroup中
		m_circles = new ImageView[m_imgIDArray.length];
		for(int i = 0; i < m_circles.length; i++) {
			ImageView imageView = new ImageView(this);
			imageView.setLayoutParams(new LayoutParams(10,10));
			m_circles[i] = imageView;
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
	}

}
