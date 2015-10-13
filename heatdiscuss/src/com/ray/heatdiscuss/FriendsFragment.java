package com.ray.heatdiscuss;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

public class FriendsFragment extends Fragment{
	
	private View rootView = null;
	private RelativeLayout m_conversation_layout = null;
	private RelativeLayout m_contact_layout = null;
	private ConversationFragment m_conversation_fragment = null;
	private ContactFragment m_contact_fragment = null;
	
	private final String CONVERSATION_FRAGMENT_TAG = "CONVERSATION_FRAGMENT";
	private final String CONTACT_FRAGMENT_TAG = "CONTACT_FRAGMENT";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.friends_layout, container, false);
		initView();
		initListener();
		return rootView;
	}
	
	
	private void initView() {
		m_conversation_layout = (RelativeLayout) rootView.findViewById(R.id.friends_conversation_layout);
		m_contact_layout = (RelativeLayout) rootView.findViewById(R.id.friends_contact_layout);
		
		m_conversation_fragment = new ConversationFragment();
		m_contact_fragment = new ContactFragment();
		
		try {
			FragmentManager m_fm = getChildFragmentManager();
			FragmentTransaction m_ft = m_fm.beginTransaction();
			
			// 往指定layout中加入对应的fragment
			Fragment tmp_converFrag = m_fm.findFragmentByTag(CONVERSATION_FRAGMENT_TAG);
			if(tmp_converFrag == null) {
				if(!m_conversation_fragment.isAdded()) {
					m_ft.add(R.id.friends_conversation_layout, m_conversation_fragment, CONVERSATION_FRAGMENT_TAG);
				}
			}
			
			Fragment tmp_contactFrag = m_fm.findFragmentByTag(CONTACT_FRAGMENT_TAG);
			if(tmp_contactFrag == null) {
				if(!m_contact_fragment.isAdded()) {
					m_ft.add(R.id.friends_contact_layout, m_contact_fragment, CONTACT_FRAGMENT_TAG);
				}
			}
			
			m_ft.commitAllowingStateLoss();
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	
	private void initListener() {
		
	}
	
	
	public void switchToPage(int pageID) {
		switch(pageID) {
			case 0:
				// 显示“会话”
				m_conversation_layout.setVisibility(View.VISIBLE);
				m_contact_layout.setVisibility(View.INVISIBLE);
				break;
			case 1:
				// 显示“通讯录”
				m_conversation_layout.setVisibility(View.INVISIBLE);
				m_contact_layout.setVisibility(View.VISIBLE);
				break;
			default:
				break;
		}
	}
}
