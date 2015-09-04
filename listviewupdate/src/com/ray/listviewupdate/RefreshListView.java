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

	View header = null;	// ��Ӧ�Ķ��������ļ�
	int headerHeight;	// ���������ļ��ĸ߶�
	int firstVisibleItem;	// ��ǰ��һ���ɼ���item
	boolean isRemark;		// ��¼��ǰλ���Ƿ�listView�����
	int startY;				// ����ʱ��Yֵ
	int scrollState;		// listview��ǰ����״̬
	IRrefreshListener iRrefreshListener;	// ˢ�����ݵĽӿ�
	
	int state; 					// ��ǰ״̬
	final int NONE = 0;			// ����״̬
	final int PULL = 1;			// ��ʾ����״̬
	final int RELEASE = 2;		// �ͷ�״̬
	final int REFRESHING = 3;	// ˢ��״̬
	
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
	
	// ��ʼ��ҳ�棬��Ӷ��������ļ���Listview��
	public void initView(Context context) {
		LayoutInflater inflater = LayoutInflater.from(context);
		header = inflater.inflate(R.layout.header_layout, null);
		
		measureView(header);
		headerHeight = header.getMeasuredHeight();
		Log.i("RefreshListView", "headerHeight = " + headerHeight);
		topPadding(-headerHeight);
		this.addHeaderView(header);	// ��Ӷ��������ļ�
		this.setOnScrollListener(this);	// �����view����onscrollListener����������
	}
	
	// ֪ͨ�����֣���ռ�õĿ��
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
	
	// ����header���ֵ��ϱ߾�
	private void topPadding(int topPadding) {
		header.setPadding(header.getPaddingLeft(), topPadding, 
				header.getPaddingRight(), header.getPaddingBottom());
		header.invalidate();
	}

	/*
	 * firstVisibleItem ������һ����ʾ��item�±꣬�����1�������ص�һ������ʾ�ڶ���Ϊ��ʼ
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
			// ��ʱ���£���listview�����
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
				// Ȼ��ˢ�²���������
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
	
	// �ж��ƶ������еĲ���
	private void onMove(MotionEvent ev) {
		if(!isRemark) {
			return;
		}
		
		int tempY = (int)ev.getY();		// ��ǰ��Y����
		int space = tempY - startY;		// �ƶ�����
		int topPadding = space - headerHeight;
		switch (state) {
		case NONE:
			// ��ʱ��ʾ�ӵ�һ������������Ҫ��ʾˢ����
			if(space > 0) {
				state = PULL;
				reflashViewByState();
			}
			break;
		case PULL:
			topPadding(topPadding);	// ����ʱ�����ϲ�����padding���ÿհ׵ĵط�����
			// ��������һ�������״̬�޸�Ϊˢ�£��磺��ͷ��Ϊ���ϣ��޸����֡��ɿ�����ˢ�¡�֮��ģ�
			if(space > headerHeight + 30 && scrollState == SCROLL_STATE_TOUCH_SCROLL) {
				state = RELEASE;
				reflashViewByState();
			}
			break;
		case RELEASE:
			topPadding(topPadding);	// ����ʱ�����ϲ�����padding���ÿհ׵ĵط�����
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
	 * ����״̬���ı���ʾҳ��
	 * */
	private void reflashViewByState() {
		TextView tip = (TextView)header.findViewById(R.id.tip);
		ImageView arrow = (ImageView)header.findViewById(R.id.arrow);
		ProgressBar progress = (ProgressBar)header.findViewById(R.id.progress);
		// ��ͷ��ת�Ķ���
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
			topPadding(-headerHeight);	// ����״̬�²���ʾ
			break;
		case PULL:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("��������ˢ��");
			arrow.clearAnimation();
			arrow.setAnimation(anim1);
			break;
		case RELEASE:
			arrow.setVisibility(View.VISIBLE);
			progress.setVisibility(View.GONE);
			tip.setText("�ɿ�����ˢ��");
			arrow.clearAnimation();
			arrow.setAnimation(anim);
			break;
		case REFRESHING:
			topPadding(50);
			arrow.setVisibility(View.GONE);
			progress.setVisibility(View.VISIBLE);
			tip.setText("����ˢ��...");
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
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� hh:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String time = format.format(date);
		lastupdatetime.setText(time);
	}
	
	public void setInterface(IRrefreshListener iRrefreshListener) {
		this.iRrefreshListener = iRrefreshListener;
	}
	
	/*
	 * ˢ�����ݽӿ�
	 * */
	public interface IRrefreshListener {
		public void onRefresh();
	}
}
