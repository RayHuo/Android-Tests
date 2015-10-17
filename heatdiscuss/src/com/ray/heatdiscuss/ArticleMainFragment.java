package com.ray.heatdiscuss;

import com.ray.heatdiscuss.articles.ArticlePageAdapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.viewpagerindicator.*;

public class ArticleMainFragment extends Fragment{
	
	private static final String TAG = "ArticleMainFragment";
	
	private View rootView = null;
	
	private TabPageIndicator mIndicator = null;
	private ViewPager mViewPager = null;
	private ArticlePageAdapter mAPageAdapter = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		if (getLayoutTheme() > 0) {
			final Context contextThemeWrapper = new ContextThemeWrapper(getActivity(), getLayoutTheme());
			LayoutInflater inflater2 = inflater.cloneInContext(contextThemeWrapper);
			inflater = inflater2;
		}
		
		rootView = inflater.inflate(R.layout.article_main_layout, container, false);
		initView();
		return rootView;
	} 
	
	protected int getLayoutTheme() {
		return R.style.Theme_PageIndicatorDefaults;
	}
	
	public void initView() {
		mIndicator = (TabPageIndicator) rootView.findViewById(R.id.article_indicator);
		mViewPager = (ViewPager) rootView.findViewById(R.id.article_viewpager);
		
		mAPageAdapter = new ArticlePageAdapter(getFragmentManager());
		mViewPager.setAdapter(mAPageAdapter);
		mViewPager.setOffscreenPageLimit(0);
		
		mIndicator.setViewPager(mViewPager, 0);
		mIndicator.setOnPageChangeListener(new MineVDPageChangeListener());
		mViewPager.setCurrentItem(0);
	}
	
	private class MineVDPageChangeListener implements ViewPager.OnPageChangeListener {		
		@Override
		public void onPageScrollStateChanged(int position) {
		}
		
		@Override
		public void onPageScrolled(int position, float arg1, int offset) {
		}
		
		@Override
		public void onPageSelected(int position) {
			Toast.makeText(getActivity(), "µ±Ç°£º" + position, Toast.LENGTH_SHORT).show();
		}
	}
}
