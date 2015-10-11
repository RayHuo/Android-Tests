package com.ray.heatdiscuss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class ContactFragment extends Fragment{
	
	private View rootView = null;
	
	private ListView mListView = null;
	private SimpleAdapter mAdapter = null;
	
	private String[] titleStrings = {"星期天", "朝花夕拾", "UA的战略", "这是一次豪赌", "Aldnoah Zero", "席德妮的骑士", "无间道的轮回", "平克敌人", "真的号码", "琅琊榜"};
	private String[] contentStrings = {"文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确", "文章的简要内容如下，请仔细查看并确定信息是否正确"};
	private String[] authorStrings = {"魔声", "Andy", "Nick", "生兴华", "骆越自卫", "AK47", "娃哈哈", "Curry", "Noah", "Tim Doncum"};
	private String[] logdateStrings = {"2015-09-28", "2015-10-01", "2015-10-02", "2015-10-03", "2015-10-04", "2015-10-05", "2015-10-06", "2015-10-07", "2015-10-08", "2015-10-09"};
	private int[] collections = {110, 220, 112, 143, 500, 706, 998, 1120, 584, 334};
	private int[] zans = {234, 400, 88, 355, 677, 876, 1000, 567, 398, 889};
	
	private List<Map<String, Object>> m_articlesList = null;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.contact_layout, container, false);
		initView();
		initListener();
		return rootView;
	}
	
	private void initView() {
		mListView = (ListView) rootView.findViewById(R.id.friends_articles);
	}
	
	private void initListener() {
		m_articlesList = new ArrayList<Map<String,Object>>();
		
		String[] mfrom = {"m_article_title", "m_article_content", "m_article_author", "m_article_logdate", "article_collection_num", "article_zan_num"};
		int[] mto = {R.id.m_article_title, R.id.m_article_content, R.id.m_article_author, R.id.m_article_logdate, R.id.article_collection_num, R.id.article_zan_num};
		
		for(int i = 0; i < 10; i++) {
			Map<String,Object> _map = new HashMap<String, Object>();
			_map.put("m_article_title", titleStrings[i]);
			_map.put("m_article_content", contentStrings[i]);
			_map.put("m_article_author", authorStrings[i]);
			_map.put("m_article_logdate", logdateStrings[i]);
			_map.put("article_collection_num", collections[i]);
			_map.put("article_zan_num", zans[i]);
			m_articlesList.add(_map);
		}
		mAdapter = new SimpleAdapter(getActivity(), m_articlesList, R.layout.article_item, mfrom, mto);
		mListView.setAdapter(mAdapter);
		
		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Map<String,Object> _map = (Map<String, Object>) mAdapter.getItem(position);
				String title = (String) _map.get("m_article_title");
				String author = (String) _map.get("m_article_author");
				Toast.makeText(getActivity(), title + ", " + author, Toast.LENGTH_LONG).show();
			}
		});
		
		
		
	}
}
