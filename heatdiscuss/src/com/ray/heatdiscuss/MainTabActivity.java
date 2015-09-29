package com.ray.heatdiscuss;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainTabActivity extends Activity implements OnClickListener{
	
	private ViewPager mViewPager = null;
	private PagerAdapter mPagerAdapter = null;
	private List<View> mViewList = new ArrayList<View>();
	
	private LinearLayout mArticleLayout = null;			// "文章"tab
	private LinearLayout mFriendsArticleLayout = null;	// "友笔"tab
	private LinearLayout mFriendsLayout = null;			// "知己"tab
	private LinearLayout mMineLayout = null;			// "吾"tab
	
	// 对应的bottom部分按钮
	private ImageButton mArticleBtn = null;
	private ImageButton mFriendsArticleBtn = null;
	private ImageButton mFriendsBtn = null;
	private ImageButton mMineBtn = null;
	
	private TextView mTopItem = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_tab);
		
		initView();
		
	}
	
	private void initView() {
		mTopItem = (TextView)findViewById(R.id.topItem);
		mTopItem.setText(R.string.tab_text_article);		// 默认显示文章tab
		
		mArticleBtn = (ImageButton)findViewById(R.id.id_btn_article);
		mFriendsArticleBtn = (ImageButton)findViewById(R.id.id_btn_friendsarticle);
		mFriendsBtn = (ImageButton)findViewById(R.id.id_btn_friends);
		mMineBtn = (ImageButton)findViewById(R.id.id_btn_mine);
		
		mArticleLayout = (LinearLayout)findViewById(R.id.id_tab_article);
		mFriendsArticleLayout = (LinearLayout)findViewById(R.id.id_tab_friendsarticle);
		mFriendsLayout = (LinearLayout)findViewById(R.id.id_tab_friends);
		mMineLayout = (LinearLayout)findViewById(R.id.id_tab_mine);
		
		mArticleLayout.setOnClickListener(this);
		mFriendsArticleLayout.setOnClickListener(this);
		mFriendsLayout.setOnClickListener(this);
		mMineLayout.setOnClickListener(this);
		
		LayoutInflater mInflater = LayoutInflater.from(this);
		View tab_article = mInflater.inflate(R.layout.article_layout, null);
		View tab_friendsarticle = mInflater.inflate(R.layout.friendsarticle_layout, null);
		View tab_friends = mInflater.inflate(R.layout.friends_layout, null);
		View tab_mine = mInflater.inflate(R.layout.mine_layout, null);
		mViewList.add(tab_article);
		mViewList.add(tab_friendsarticle);
		mViewList.add(tab_friends);
		mViewList.add(tab_mine);
		
		mViewPager = (ViewPager)findViewById(R.id.m_viewpager);
		mPagerAdapter = new PagerAdapter() {
			
			@Override
			public void destroyItem(ViewGroup container, int position,
					Object object)
			{
				container.removeView(mViewList.get(position));
			}

			@Override
			public Object instantiateItem(ViewGroup container, int position)
			{
				View view = mViewList.get(position);
				container.addView(view);
				return view;
			}
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mViewList.size();
			}
		};
		mViewPager.setAdapter(mPagerAdapter);
		
		// 在tab更换的时候，修改top中的文字，同时更改bottom中的高亮项
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				int currentTab = mViewPager.getCurrentItem();
				allNormal();	// 先把bottom中的全部项都置为灰色
				switch (currentTab) {
				case 0:
					mArticleBtn.setImageResource(R.drawable.tab_weixin_pressed);
					mTopItem.setText(R.string.tab_text_article);
					break;
				case 1:
					mFriendsArticleBtn.setImageResource(R.drawable.tab_find_frd_pressed);
					mTopItem.setText(R.string.tab_text_friendsarticle);				
					break;
				case 2:
					mFriendsBtn.setImageResource(R.drawable.tab_address_pressed);
					mTopItem.setText(R.string.tab_text_friends);
					break;
				case 3:
					mMineBtn.setImageResource(R.drawable.tab_settings_pressed);
					mTopItem.setText(R.string.tab_text_mine);
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
	public void onClick(View v) {
		// TODO Auto-generated method stub
		allNormal();
		
		switch (v.getId()) {
			case R.id.id_tab_article:
				mViewPager.setCurrentItem(0);
				mArticleBtn.setImageResource(R.drawable.tab_weixin_pressed);
				mTopItem.setText(R.string.tab_text_article);
				break;
			case R.id.id_tab_friendsarticle:
				mViewPager.setCurrentItem(1);
				mFriendsArticleBtn.setImageResource(R.drawable.tab_find_frd_pressed);
				mTopItem.setText(R.string.tab_text_friendsarticle);	
				break;
			case R.id.id_tab_friends:
				mViewPager.setCurrentItem(2);
				mFriendsBtn.setImageResource(R.drawable.tab_address_pressed);
				mTopItem.setText(R.string.tab_text_friends);
				break;
			case R.id.id_tab_mine:
				mViewPager.setCurrentItem(3);
				mMineBtn.setImageResource(R.drawable.tab_settings_pressed);
				mTopItem.setText(R.string.tab_text_mine);
				break;
			default:
				break;
		}
	}
	
	// 把bottom中的所有tab的图标设为normal状态，即灰掉。
	private void allNormal() {
		mArticleBtn.setImageResource(R.drawable.tab_weixin_normal);
		mFriendsArticleBtn.setImageResource(R.drawable.tab_find_frd_normal);
		mFriendsBtn.setImageResource(R.drawable.tab_address_normal);
		mMineBtn.setImageResource(R.drawable.tab_settings_normal);
	}
}
