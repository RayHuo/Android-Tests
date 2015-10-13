package com.ray.heatdiscuss;

import java.util.ArrayList;
import java.util.List;

import android.R.anim;
import android.R.integer;
import android.app.Activity;
//import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
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

public class MainTabActivity extends FragmentActivity implements OnClickListener{
	
	private ViewPager mViewPager = null;
	private FragmentPagerAdapter mFPagerAdapter = null;
//	private List<View> mViewList = new ArrayList<View>();
	private List<Fragment> mFragments = new ArrayList<Fragment>();
	
	private LinearLayout mArticleLayout = null;			// "文章"tab按钮
//	private LinearLayout mFriendArticleLayout = null;	// "友笔"tab按钮
	private LinearLayout mFriendArticleLayout = null;	// "友笔"tab按钮
	private LinearLayout mFriendsLayout = null;			// "知己"tab按钮
	private LinearLayout mMineLayout = null;			// "吾"tab按钮
	
	private LinearLayout mFriendTitle = null;
	private LinearLayout mTitle = null;
	private TextView mTitleText = null;
	
	private TextView mConversationTitle = null;
	private TextView mContactTitle = null;
	
	// 对应的bottom部分按钮
	private ImageButton mArticleBtn = null;
	private ImageButton mFriendsArticlesBtn = null;
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
//		mTopItem = (TextView)findViewById(R.id.topItem);
//		mTopItem.setText(R.string.tab_text_article);		// 默认显示文章tab
		mFriendTitle = (LinearLayout) findViewById(R.id.m_friends_title);
		mTitle = (LinearLayout) findViewById(R.id.m_title);
		mTitleText = (TextView) findViewById(R.id.m_title_text);
		mTitleText.setText(R.string.tab_text_article);
		
		mConversationTitle = (TextView) findViewById(R.id.conversation_tab);
		mContactTitle = (TextView) findViewById(R.id.contact_tab);
		
		mConversationTitle.setOnClickListener(switchClickListener);
		mContactTitle.setOnClickListener(switchClickListener);
		
		mArticleBtn = (ImageButton)findViewById(R.id.id_btn_article);
		mFriendsArticlesBtn = (ImageButton)findViewById(R.id.id_btn_friends_articles);
		mFriendsBtn = (ImageButton)findViewById(R.id.id_btn_friends);
		mMineBtn = (ImageButton)findViewById(R.id.id_btn_mine);
		
		mArticleLayout = (LinearLayout)findViewById(R.id.id_tab_article);
		mFriendArticleLayout = (LinearLayout)findViewById(R.id.id_tab_friendarticle);
		mFriendsLayout = (LinearLayout)findViewById(R.id.id_tab_friends);
		mMineLayout = (LinearLayout)findViewById(R.id.id_tab_mine);
		
		mArticleLayout.setOnClickListener(this);
		mFriendArticleLayout.setOnClickListener(this);
		mFriendsLayout.setOnClickListener(this);
		mMineLayout.setOnClickListener(this);
		
		
		
//		LayoutInflater mInflater = LayoutInflater.from(this);
//		View tab_article = mInflater.inflate(R.layout.article_layout, null);
//		View tab_conversation = mInflater.inflate(R.layout.conversation_layout, null);
//		View tab_friends = mInflater.inflate(R.layout.friends_layout, null);
//		View tab_mine = mInflater.inflate(R.layout.mine_layout, null);
//		mViewList.add(tab_article);
//		mViewList.add(tab_conversation);
//		mViewList.add(tab_friends);
//		mViewList.add(tab_mine);
		
		Fragment frag_article = new ArticleFragment();
		Fragment frag_friendsArticle = new FriendsArticleFragment();
		Fragment frag_friends = new FriendsFragment();
		Fragment frag_mine = new MineFragment();
		mFragments.add(frag_article);
		mFragments.add(frag_friendsArticle);
		mFragments.add(frag_friends);
		mFragments.add(frag_mine);
		
		mViewPager = (ViewPager)findViewById(R.id.m_viewpager);
		mFPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return mFragments.size();
			}
			
			@Override
			public Fragment getItem(int arg0) {
				// TODO Auto-generated method stub
				return mFragments.get(arg0);
			}
		};
		mViewPager.setAdapter(mFPagerAdapter);
		
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
					mFriendTitle.setVisibility(View.INVISIBLE);
					mTitle.setVisibility(View.VISIBLE);
					mTitleText.setText(R.string.tab_text_article);
					break;
				case 1:
					mFriendsArticlesBtn.setImageResource(R.drawable.tab_find_frd_pressed);
					mFriendTitle.setVisibility(View.INVISIBLE);
					mTitle.setVisibility(View.VISIBLE);
					mTitleText.setText(R.string.tab_text_friends_articles);				
					break;
				case 2:
					mFriendsBtn.setImageResource(R.drawable.tab_address_pressed);
//					mTopItem.setText(R.string.tab_text_friends);
					mTitle.setVisibility(View.INVISIBLE);
					mFriendTitle.setVisibility(View.VISIBLE);
					break;
				case 3:
					mMineBtn.setImageResource(R.drawable.tab_settings_pressed);
					mFriendTitle.setVisibility(View.INVISIBLE);
					mTitle.setVisibility(View.VISIBLE);
					mTitleText.setText(R.string.tab_text_mine);
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
				mFriendTitle.setVisibility(View.INVISIBLE);
				mTitle.setVisibility(View.VISIBLE);
				mTitleText.setText(R.string.tab_text_article);
				break;
			case R.id.id_tab_friendarticle:
				mViewPager.setCurrentItem(1);
				mFriendsArticlesBtn.setImageResource(R.drawable.tab_find_frd_pressed);
				mFriendTitle.setVisibility(View.INVISIBLE);
				mTitle.setVisibility(View.VISIBLE);
				mTitleText.setText(R.string.tab_text_friends_articles);	
				break;
			case R.id.id_tab_friends:
				mViewPager.setCurrentItem(2);
				mFriendsBtn.setImageResource(R.drawable.tab_address_pressed);
//				mTopItem.setText(R.string.tab_text_friends);
				mTitle.setVisibility(View.INVISIBLE);
				mFriendTitle.setVisibility(View.VISIBLE);
				break;
			case R.id.id_tab_mine:
				mViewPager.setCurrentItem(3);
				mMineBtn.setImageResource(R.drawable.tab_settings_pressed);
				mFriendTitle.setVisibility(View.INVISIBLE);
				mTitle.setVisibility(View.VISIBLE);
				mTitleText.setText(R.string.tab_text_mine);
				break;
			default:
				break;
		}
	}
	
	// 把bottom中的所有tab的图标设为normal状态，即灰掉。
	private void allNormal() {
		mArticleBtn.setImageResource(R.drawable.tab_weixin_normal);
		mFriendsArticlesBtn.setImageResource(R.drawable.tab_find_frd_normal);
		mFriendsBtn.setImageResource(R.drawable.tab_address_normal);
		mMineBtn.setImageResource(R.drawable.tab_settings_normal);
	}
	
	
	//  “知己”tab 中切换 “会话” 和 “通讯录”
	private OnClickListener switchClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			switch(view.getId()) {
				case R.id.contact_tab:
					mContactTitle.setBackgroundColor(0xFF0066FF);
					mConversationTitle.setBackgroundColor(0xFF00CCFF);
					FriendsFragment tmpFragment0 = (FriendsFragment) mFPagerAdapter.getItem(2);
					tmpFragment0.switchToPage(0);
					break;
				case R.id.conversation_tab:
					mContactTitle.setBackgroundColor(0xFF00CCFF);
					mConversationTitle.setBackgroundColor(0xFF0066FF);
					FriendsFragment tmpFragment1 = (FriendsFragment) mFPagerAdapter.getItem(2);
					tmpFragment1.switchToPage(1);
					break;
				default:
						break;
			}
		}
	};
	
	
}
