package com.ray.heatdiscuss;

import java.util.ArrayList;
import java.util.List;

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
	
	private LinearLayout mArticleLayout = null;			// "����"tab��ť
	private LinearLayout mconversationLayout = null;	// "�ѱ�"tab��ť
	private LinearLayout mFriendsLayout = null;			// "֪��"tab��ť
	private LinearLayout mMineLayout = null;			// "��"tab��ť
	
	// ��Ӧ��bottom���ְ�ť
	private ImageButton mArticleBtn = null;
	private ImageButton mconversationBtn = null;
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
		mTopItem.setText(R.string.tab_text_article);		// Ĭ����ʾ����tab
		
		mArticleBtn = (ImageButton)findViewById(R.id.id_btn_article);
		mconversationBtn = (ImageButton)findViewById(R.id.id_btn_conversation);
		mFriendsBtn = (ImageButton)findViewById(R.id.id_btn_friends);
		mMineBtn = (ImageButton)findViewById(R.id.id_btn_mine);
		
		mArticleLayout = (LinearLayout)findViewById(R.id.id_tab_article);
		mconversationLayout = (LinearLayout)findViewById(R.id.id_tab_conversation);
		mFriendsLayout = (LinearLayout)findViewById(R.id.id_tab_friends);
		mMineLayout = (LinearLayout)findViewById(R.id.id_tab_mine);
		
		mArticleLayout.setOnClickListener(this);
		mconversationLayout.setOnClickListener(this);
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
		Fragment frag_conversation = new ConversationFragment();
		Fragment frag_friends = new FriendsFragment();
		Fragment frag_mine = new MineFragment();
		mFragments.add(frag_article);
		mFragments.add(frag_conversation);
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
		
		// ��tab������ʱ���޸�top�е����֣�ͬʱ����bottom�еĸ�����
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				int currentTab = mViewPager.getCurrentItem();
				allNormal();	// �Ȱ�bottom�е�ȫ�����Ϊ��ɫ
				switch (currentTab) {
				case 0:
					mArticleBtn.setImageResource(R.drawable.tab_weixin_pressed);
					mTopItem.setText(R.string.tab_text_article);
					break;
				case 1:
					mconversationBtn.setImageResource(R.drawable.tab_find_frd_pressed);
					mTopItem.setText(R.string.tab_text_conversation);				
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
			case R.id.id_tab_conversation:
				mViewPager.setCurrentItem(1);
				mconversationBtn.setImageResource(R.drawable.tab_find_frd_pressed);
				mTopItem.setText(R.string.tab_text_conversation);	
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
	
	// ��bottom�е�����tab��ͼ����Ϊnormal״̬�����ҵ���
	private void allNormal() {
		mArticleBtn.setImageResource(R.drawable.tab_weixin_normal);
		mconversationBtn.setImageResource(R.drawable.tab_find_frd_normal);
		mFriendsBtn.setImageResource(R.drawable.tab_address_normal);
		mMineBtn.setImageResource(R.drawable.tab_settings_normal);
	}
}
