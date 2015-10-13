package com.ray.heatdiscuss;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ConversationFragment extends Fragment{
	
	private View rootView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.conversation_layout, container, false);
		initView();
		initListener();
		return rootView;
	}
	
	private void initView() {
		
	}
	
	
	private void initListener() {
		
	}
	
}
