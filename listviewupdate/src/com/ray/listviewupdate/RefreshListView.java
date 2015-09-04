package com.ray.listviewupdate;

import java.sql.Date;
import java.text.SimpleDateFormat;

import android.R.integer;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewDebug.IntToString;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;

public class RefreshListView extends ListView implements OnScrollListener {

	View header = null;	// 对应的顶部布局文件
	int headerHeight;	// 顶部布局文件的高度
	int firstVisibleItem;	// 当前第一个可见的item
	boolean isRemark;		// 记录当前位置是否listView的最顶端
	int startY;				// 按下时的Y值
	int scrollState;		// listview当前滚动状态
	IRrefreshListener iRrefreshListener;	// 刷新数据的接口
	
	int state; 					// 当前状态
	final int NONE = 0;			// 正常状态
	final int PULL = 1;			// 提示下拉状态
	final int RELEASE = 2;		// 释放状态
	final int REFRESHING = 3;	// 刷新状态
	
	public RefreshListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	
	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	
	public RefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		initView(context);
	}
	
	// 初始化页面，添加顶部布局文件到Listview中
	public void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.header_layout, null);
		
		measureView(header);
		headerHeight = header.getMeasuredHeight();
		Log.i("RefreshListView", "headerHeight = " + headerHeight);
		topPadding(-headerHeight);
		this.addHeaderView(header);	// 添加顶部布局文件
		this.setOnScrollListener(this);	// 给这个view设置onscrollListener（监听器）
	}
	
	// 通知父布局，其占用的宽高
	private void measureView(View view) {
		ViewGroup.LayoutParams p = view.getLayoutParams();
		if(p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}
		
		int width = ViewGroup.getChildMeasureSpec(0, 0, p.width);
		int height;
		int tempHeight = p.height;
		if(tempHeight > 0) {
			height = MeasureSpec.makeMeasureSpec(tempHeight, MeasureSpec.EXACTLY);
		}
		else {
			height = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
		}
		view.measure(width, height);
	}
	
	// 设置header布局的上边距
	private void topPadding(int topPadding) {
		header.setPadding(header.getPaddingLeft(), topPadding, 
				header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();
	}

	/*
	 * firstVisibleItem 决定第一个显示的item下标，如果是1，就隐藏第一个，显示第二个为开始
	 * */
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		this.firstVisibleItem = firstVisibleItem;
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		this.scrollState = scrollState;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 此时按下，在listview的最顶端
			if(firstVisibleItem == 0) {
				isRemark = true;
				startY = (int)ev.getY();
			}
			break;
		case MotionEvent.ACTION_MOVE:
			onMove(ev);
			break;
		case MotionEvent.ACTION_UP:
			if(state == RELEASE) {
				state = REFRESHING;
				// 然后刷新并加载数据
				reflashViewByState();
				iRrefreshListener.onRefresh();
//				reflashComplete();
			}else if(state == PULL) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
		return super.onTouchEvent(ev);
	}
	
	// 判断移动过程中的操作
	private void onMove(MotionEvent ev) {
		if(!isRemark) {
			return;
		}
		
		int tempY = (int)ev.getY();		// 当前的Y坐标
		int space = tempY - startY;		// 移动距离
		int topPadding = space - headerHeight;
		switch (state) {
		case NONE:
			// 此时表示从第一项往下拉，需要提示刷新了
			if(space > 0) {
				state = PULL;
				reflashViewByState();
			}
			break;
		case PULL:
			topPadding(topPadding);	// 下来时，不断补充其padding，让空白的地方出来
			// 当下来到一定距离后，状态修改为刷新（如：箭头改为向上，修改文字“松开即可刷新”之类的）
			if(space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				state = RELEASE;
				reflashViewByState();
			}
			break;
		case RELEASE:
			topPadding(topPadding);	// 下来时，不断补充其padding，让空白的地方出来
			if(space < headerHeight + 30) {
				state = PULL;
				reflashViewByState();
			}else if(space <= 0) {
				state = NONE;
				isRemark = false;
				reflashViewByState();
			}
			break;
		}
	}
	
	/*
	 * 根据状态，改变显示页面
	 * */
	private void reflashViewByState() {
		TextView tip = (TextView)header.findViewById(R.id.tip);
		ImageView arrow = (ImageView)header.findViewById(R.id.arrow);
		ProgressBar progress = (ProgressBar)header.findViewById(R.id.progress);
		// 箭头旋转的动画
		RotateAnimation anim = new RotateAnimation(0, 180, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim.setDuration(500);
		anim.setFillAfter(true);
		RotateAnimation anim1 = new RotateAnimation(180, 0, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		anim1.setDuration(500);
		anim1.setFillAfter(true);
		
		switch (state) {
		case NONE:
			arrow.clearAnimation();
			topPadding(-headerHeight);	// 正常状态下不显示
			break;
		case PULL:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("下来可以刷新");
			arrow.clearAnimation();
			arrow.setAnimation(anim1);
			break;
		case RELEASE:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("松开可以刷新");
			arrow.clearAnimation();
			arrow.setAnimation(anim);
			break;
		case REFRESHING:
			topPadding(50);
			arrow.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("正在刷新...");
			arrow.clearAnimation();
			break;
		default:
			break;
		}
	}
	
	
	public void reflashComplete() {
		state = NONE;
		isRemark = false;
		reflashViewByState();
		TextView lastupdatetime = (TextView)header.findViewById(R.id.lastupdate_time);
		SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = format.format(date);
		lastupdatetime.setText(time);
	}
	
	public void setInterface(IRrefreshListener iRrefreshListener) {
		this.iRrefreshListener = iRrefreshListener;
	}
	
	/*
	 * 刷新数据接口
	 * */
	public interface IRrefreshListener {
		public void onRefresh();
	}
}
