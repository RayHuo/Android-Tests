package com.ray.heatdiscuss.articles;

import java.util.ArrayList;
import java.util.List;

import com.ray.heatdiscuss.ArticleFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class ArticlePageAdapter extends FragmentPagerAdapter{

	private static final String TAG = "ArticlePageAdapter";
	
	private static final int MAX_TAG_SIZE = 5;
	private static final String[] m_article_tags = new String[] {"IT", "历史", "动漫", "NBA", "数学"};
	private static final String[] m_article_urls = new String[] {"tencent.com", 
													"baidu.com", 
													"ali.com", 
													"nba.com", 
													"google.com"};
//	private Fragment[] mFragments;
	private List<Fragment> mFragments;
	
	public ArticlePageAdapter(FragmentManager fm) {
		super(fm);
		mFragments = new ArrayList<Fragment>();
		initFragments();
	}
	
	// 记得不要出现这个函数，因为：
//	@Override
//	public boolean isViewFromObject(View arg0, Object arg1) {
//		// TODO Auto-generated method stub
//		return arg0 == arg1;
//	}
	
	private void initFragments() {
//		mFragments = new Fragment[MAX_TAG_SIZE];
		ArticleFragment frag;
		
		Bundle args;
		for(int i = 0; i < m_article_tags.length; i++) {
			args = new Bundle();
			args.putString("URL", m_article_urls[i]);
			frag = new ArticleFragment();
			frag.setArguments(args);
			mFragments.add(frag);
		}
		
	}

	
	
	@Override  
    public CharSequence getPageTitle(int position) {  
        return m_article_tags[position % m_article_tags.length].toUpperCase();  
    }  
	
	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		Log.i(TAG, "ArticlePageAdapter destroyItem " + position);
		super.destroyItem(container, position, object);
	}	

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		Log.i(TAG, "ArticlePageAdapter instantiateItem " + position);
		return super.instantiateItem(container, position);
	}
	
	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		if (null == mFragments || position < 0 || position >= mFragments.size()) {
			return	null;
		}

		return mFragments.get(position);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
//		Log.i(TAG, "ArticlePageAdapter getCount " + m_article_tags.length);
		return m_article_tags.length;
	}
}
