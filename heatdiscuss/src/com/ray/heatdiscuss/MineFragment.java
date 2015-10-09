package com.ray.heatdiscuss;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MineFragment extends Fragment{
	
	private View rootView = null;
	
	private ListView mine_listview = null;
	private TextView mine_nickname = null;
	private TextView mine_sign = null;
	private TextView mine_tag = null;
	private ImageView mine_icon = null;
	
	private int[] list_images = {R.drawable.mine_concern, R.drawable.mine_articles, R.drawable.mine_message, R.drawable.mine_collection, R.drawable.mine_zan};
	private String[] list_tagName = {"关注标签", "札记", "消息", "收藏", "赞"};
	
	private SimpleAdapter mAdapter = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.mine_layout, container, false);
		initView();
		initListener();
		return rootView;
	}
	
	
	private void initView() {
		mine_icon = (ImageView) rootView.findViewById(R.id.mine_icon);
		mine_listview = (ListView) rootView.findViewById(R.id.mine_info_list);
		
		mine_nickname = (TextView) rootView.findViewById(R.id.mine_nickname);
		mine_sign = (TextView) rootView.findViewById(R.id.mine_sign);
		mine_tag = (TextView) rootView.findViewById(R.id.mine_tag);
		
		mine_nickname.setText("RayHuo");
		mine_sign.setText("coding hard~~~");
		mine_tag.setText("数学, 历史, NBA, IT, 动漫");
	}
	
	
	private void initListener() {
		String[] mfrom = {"m_tag_image", "m_tag_name"};
		int[] mto = {R.id.m_tag_image, R.id.m_tag_name};
		
		List<Map<String, Object>> m_tag_list = new ArrayList<Map<String,Object>>();
		for(int i = 0; i < 5; i++) {
			Map<String, Object> _map = new HashMap<String, Object>();
			_map.put("m_tag_image", list_images[i]);
			_map.put("m_tag_name", list_tagName[i]);
			m_tag_list.add(_map);
		}
		mAdapter = new SimpleAdapter(getActivity(), m_tag_list, R.layout.mine_tag_item, mfrom, mto);
		mine_listview.setAdapter(mAdapter);
		
		mine_listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				// TODO Auto-generated method stub
				Map<String, Object> _map = (Map<String, Object>) mAdapter.getItem(position);
				String name = (String) _map.get("m_tag_name");
				Toast.makeText(getActivity(), name, Toast.LENGTH_LONG).show();
			}
		});
	}
}
