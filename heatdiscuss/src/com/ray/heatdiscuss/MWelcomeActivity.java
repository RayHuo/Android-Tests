package com.ray.heatdiscuss;

import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class MWelcomeActivity extends Activity implements OnClickListener {
	
	private static final String	TAG	= "MWelcomeActivity";
	
	private Button btn_start = null;
	private ViewPager m_viewPager = null;
	private int[] m_imageIds = {R.drawable.guide_image1, R.drawable.guide_image2, R.drawable.guide_image3};
	private List<ImageView> m_images = new ArrayList<ImageView>();
	
	private ImageView mCircle1 = null;
	private ImageView mCircle2 = null;
	private ImageView mCircle3 = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除应用标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_mwelcome);
		
		initView();
	}

	
	private void initView() {
		btn_start = (Button)findViewById(R.id.btn_start);
		btn_start.setOnClickListener(this);
		
		mCircle1 = (ImageView)findViewById(R.id.circle1);
		mCircle2 = (ImageView)findViewById(R.id.circle2);
		mCircle3 = (ImageView)findViewById(R.id.circle3);
		
		m_viewPager = (ViewPager)findViewById(R.id.m_viewpager);
		m_viewPager.setAdapter(new PagerAdapter() {
			
			// 这个函数表示的是：我的理解是一个Page在切换完成后会调用该方法去加载下一个即将展示的Page，
			// 至于是哪个Page取决于切换动作，比如Page1切换到Page2，切换完成后会调用该方法去加载Page3。
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
		
		m_viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				resetCircles();
				int currentItem = m_viewPager.getCurrentItem();
				switch (currentItem) {
				case 0:
					mCircle1.setImageResource(R.drawable.circle_black);
					break;
				case 1:
					mCircle2.setImageResource(R.drawable.circle_black);
					break;
				case 2:
					mCircle3.setImageResource(R.drawable.circle_black);
					break;
				default:
					break;
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mwelcome, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		Intent mIntent = new Intent(MWelcomeActivity.this, MainTabActivity.class);
		mIntent.setAction(Intent.ACTION_SEND);
		startActivity(mIntent);
	}

	// 把圈圈都置为白色
	private void resetCircles() {
		mCircle1.setImageResource(R.drawable.circle_white);
		mCircle2.setImageResource(R.drawable.circle_white);
		mCircle3.setImageResource(R.drawable.circle_white);
	}
	
}
