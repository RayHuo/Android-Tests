package com.ray.heatdiscuss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.R.integer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class FriendsArticleFragment extends Fragment{
	
	private View rootView = null;
	
//	private ListView mListView = null;
	private GridView mListView = null;
	private SimpleAdapter mAdapter = null;
	
	private String[] titleStrings = {"星期天", "朝花夕拾", "UA的战略", "这是一次豪赌", "Aldnoah Zero", "席德妮的骑士", "无间道的轮回", "平克敌人", "真的号码", "琅琊榜"};
	private String[] contentStrings = {"文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", 
									   "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", 
									   "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", 
									   "文章的简要内容如下，请仔细查看并确定信息是否正确"};
	private String[] authorStrings = {"魔声", "Andy", "Nick", "生兴华", "骆越自卫", "AK47", "娃哈哈", "Curry", "Noah", "Tim Doncum"};
	private String[] logdateStrings = {"2015-09-28", "2015-10-01", "2015-10-02", "2015-10-03", "2015-10-04", "2015-10-05", "2015-10-06", "2015-10-07", "2015-10-08", "2015-10-09"};
	private int[] collections = {110, 220, 112, 143, 500, 706, 998, 1120, 584, 334};
	private int[] totalView = {234, 400, 88, 355, 677, 876, 1000, 567, 398, 887};
	private int[] imgStrings = {R.drawable.friends_article_img1, R.drawable.friends_article_img2, R.drawable.friends_article_img3, 
								R.drawable.friends_article_img4, R.drawable.friends_article_img5, R.drawable.friends_article_img6,
								R.drawable.friends_article_img7, R.drawable.friends_article_img8, R.drawable.friends_article_img9,
								R.drawable.friends_article_img10};
	
	
	private List<Map<String, Object>> m_articlesList = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.friends_article_layout, container, false);
		initView();
		initListener();
		return rootView;
	}
	
	private void initView() {
//		mListView = (ListView) rootView.findViewById(R.id.friends_articles);
		mListView = (GridView) rootView.findViewById(R.id.friends_article_gridView);
	}
	
	private void initListener() {
		m_articlesList = new ArrayList<Map<String,Object>>();
		
//		String[] mfrom = {"m_article_title", "m_article_content", "m_article_author", "m_article_logdate", "article_collection_num", "article_zan_num"};
//		int[] mto = {R.id.m_article_title, R.id.m_article_content, R.id.m_article_author, R.id.m_article_logdate, R.id.article_collection_num, R.id.article_zan_num};
//		
//		for(int i = 0; i < 10; i++) {
//			Map<String,Object> _map = new HashMap<String, Object>();
//			_map.put("m_article_title", titleStrings[i]);
//			_map.put("m_article_content", contentStrings[i]);
//			_map.put("m_article_author", authorStrings[i]);
//			_map.put("m_article_logdate", logdateStrings[i]);
//			_map.put("article_collection_num", collections[i]);
//			_map.put("article_zan_num", zans[i]);
//			m_articlesList.add(_map);
//		}
		
		String[] mfrom = {"friends_article_image", "friends_article_author", "friends_article_title", 
						  "friends_article_logdate", "friends_article_total_view" };
		int[] mto = {R.id.friends_article_image, R.id.friends_article_author, R.id.friends_article_title,
					 R.id.friends_article_logdate, R.id.friends_article_total_view };
		
		for(int i = 0; i < 10; i++) {
			Map<String,Object> _map = new HashMap<String, Object>();
			_map.put("friends_article_image", imgStrings[i]);
			_map.put("friends_article_author", authorStrings[i]);
			_map.put("friends_article_title", contentStrings[i]);
			_map.put("friends_article_logdate", logdateStrings[i]);
			_map.put("friends_article_total_view", totalView[i]);
			m_articlesList.add(_map);
		}
		
		mAdapter = new SimpleAdapter(getActivity(), m_articlesList, R.layout.friends_article_item, mfrom, mto);
		mListView.setAdapter(mAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				Map<String,Object> _map = (Map<String, Object>) mAdapter.getItem(position);
//				String title = (String) _map.get("m_article_title");
//				String author = (String) _map.get("m_article_author");
				String title = (String) _map.get("friends_article_author");
				String author = (String) _map.get("friends_article_logdate");
				Toast.makeText(getActivity(), title + ", " + author, Toast.LENGTH_SHORT).show();
			}
		});

	}
}
