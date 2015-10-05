package com.ray.heatdiscuss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.ImageView.ScaleType;

public class ArticleFragment extends Fragment {
	
	private static final String	TAG	= "ArticleFragment";
	
	private View rootView = null;			// 提取这个fragment对应显示的view
	private ViewPager mViewPager = null;	// 在页面顶部显示最新推荐文章的viewpager
	private ListView mListView = null;		// 显示文章的listview
	
	private ImageView mACircle1 = null;		// 为mViewPager切换页面时做标志
	private ImageView mACircle2 = null;
	private ImageView mACircle3 = null;
	
	// 临时使用的变量，为了显示viewPager和listview的数据
	private List<Map<String, Object>> articles = new ArrayList<Map<String, Object>>();
	private List<ImageView> new_articles = new ArrayList<ImageView>();
	private int[] article_images = {R.drawable.article01, R.drawable.article02, R.drawable.article03};
	
	private ImageView testImage = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.article_layout, container, false);
		initView();
		initListener();
		initArticles();
		return rootView;
	}
	
	private void initView() {
		mViewPager = (ViewPager)rootView.findViewById(R.id.newArticlesViewpager);
		mListView = (ListView)rootView.findViewById(R.id.newArticlesList);
		
		mACircle1 = (ImageView)rootView.findViewById(R.id.mAcircle1);
		mACircle2 = (ImageView)rootView.findViewById(R.id.mAcircle2);
		mACircle3 = (ImageView)rootView.findViewById(R.id.mAcircle3);
	
		testImage = (ImageView)rootView.findViewById(R.id.test_image);
		testImage.setImageResource(R.drawable.article02);
	}
	
	private void initListener() {
		final Context tContext = getActivity();
		mViewPager.setAdapter(new PagerAdapter() {
			
			@Override
			public Object instantiateItem(ViewGroup container, int position) {
				ImageView m_ImageView = new ImageView(tContext);
				m_ImageView.setImageResource(article_images[position]);
				m_ImageView.setScaleType(ScaleType.FIT_CENTER);		// 按照指定大小缩放显示
				container.addView(m_ImageView);
				new_articles.add(m_ImageView);
				
				return m_ImageView;
			}
			
			@Override
			public void destroyItem(ViewGroup container, int position, Object object) {
				container.removeView(new_articles.get(position));
			}
			
			@Override
			public boolean isViewFromObject(View arg0, Object arg1) {
				// TODO Auto-generated method stub
				return arg0 == arg1;
			}
			
			@Override
			public int getCount() {
				// TODO Auto-generated method stub
				return new_articles.size();
			}
		});
		
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener() {

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
				resetNewArticleCircles();
				int currentItem = mViewPager.getCurrentItem();
				switch (currentItem) {
				case 0:
					mACircle1.setImageResource(R.drawable.circle_black);
					break;
				case 1:
					mACircle2.setImageResource(R.drawable.circle_black);				
					break;
				case 2:
					mACircle3.setImageResource(R.drawable.circle_black);
					break;
				default:
					break;
				}
			}
			
		});
		
	}
	
	private void resetNewArticleCircles() {
		mACircle1.setImageResource(R.drawable.circle_white);
		mACircle2.setImageResource(R.drawable.circle_white);
		mACircle3.setImageResource(R.drawable.circle_white);
	}
	
	
	private void initArticles() {
		
		String[] mfrom = {"m_article_title", "m_article_content", "m_article_authorLogdate"};		// 对于的字段名
		int[] mto = {R.id.m_article_title, R.id.m_article_content, R.id.m_article_authorLogdate};	// 对应 R.layout.article_item 中的id
		
		Map<String, Object> tmp_Map = null;
		for(int i = 0; i < 8; i++) {
			tmp_Map = new HashMap<String, Object>();
			tmp_Map.put("m_article_title", "题目" + i);
			tmp_Map.put("m_article_content", "文章简要内容测试, 文章简要内容测试.");
			tmp_Map.put("m_article_authorLogdate", "Jack  2015-10-01");
			articles.add(tmp_Map);
		}
		SimpleAdapter mAdapter = new SimpleAdapter(getActivity(), articles, 
				R.layout.article_item, mfrom, mto);
		mListView.setAdapter(mAdapter);

	}
		
}
