package com.ray.heatdiscuss.articles;

import com.ray.heatdiscuss.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

public class ArticleItemActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 去除应用标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.article_item_layout);
		
		initView();
	}
	
	private void initView() {
		Bundle getBundle = getIntent().getExtras();
		
		String m_article_title = getBundle.getString("m_article_title");
		String m_article_content = getBundle.getString("m_article_content");
		String m_article_authorLogdate = getBundle.getString("m_article_authorLogdate");
		
	}
}
