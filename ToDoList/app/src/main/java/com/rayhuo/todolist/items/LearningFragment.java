package com.rayhuo.todolist.items;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rayhuo.todolist.R;

public class LearningFragment extends Fragment {
	
	private View rootView = null;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.learning_layout, container, false);
		
		return rootView;
	}
}
