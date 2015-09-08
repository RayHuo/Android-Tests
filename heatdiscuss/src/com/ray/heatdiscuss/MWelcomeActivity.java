package com.ray.heatdiscuss;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.R.integer;
import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.ListAdapter;

public class MWelcomeActivity extends Activity {
	
	private ViewPager m_viewPager = null;
	private int[] m_imageIds = {R.drawable.guide_image1, R.drawable.guide_image2, R.drawable.guide_image3};
	private List<ImageView> m_images = new ArrayList<ImageView>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除应用标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mwelcome);
		
		m_viewPager = (ViewPager)findViewById(R.id.m_viewpager);
		m_viewPager.setAdapter(new PagerAdapter() {
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageView m_ImageView = new ImageView(MWelcomeActivity.this);
				m_ImageView.setImageResource(m_imageIds[position]);
				m_ImageView.setScaleType(ScaleType.CENTER_CROP);
				container.addView(m_ImageView);
				m_images.add(m_ImageView);
				return m_ImageView;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(m_images.get(position));
			}
			
			@Override
			public boolean isViewFromObject(View view, Object object) {
				// TODO Auto-generated method stub
				return view == object;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return m_imageIds.length;
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mwelcome, menu);
		return true;
	}

}
