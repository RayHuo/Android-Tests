package com.ray.todolist.views;

import java.text.AttributedCharacterIterator.Attribute;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlideMenu extends HorizontalScrollView {

	private int mScreenWidth;	// ��Ļ���
	
	private int mMenuPadding = 50;	// �����˵��о����ұߵĿ��
	
	private int mMenuWidth;		// �˵��Ŀ��
	private int mHalfMenuWidth;	// �˵�һ��Ŀ�ȣ������жϻ�����ʲô�����ʾ�˵�
	
	private boolean once;		// �������� onMeasure ִֻ��һ��
	
	private View arrow = null;
	private Animation myAnimation_Rotate1 = null;
	private Animation myAnimation_Rotate2 = null;
	private boolean flag = false;
	private boolean STATEMAIN = true;	// �е�ʱ�������������Ϊ(��Ϊ���뻹û�дﵽ�л�����)����û�иı������棬���Լ�ͷ��Ӧ�ý��ж���
	
	public SlideMenu(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public SlideMenu(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
		mScreenWidth = getResources().getDisplayMetrics().widthPixels;
		
		myAnimation_Rotate1 = new RotateAnimation(0, 180, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		myAnimation_Rotate1.setDuration(500);
		myAnimation_Rotate1.setFillAfter(true);

		myAnimation_Rotate2 = new RotateAnimation(180, 0, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f, 
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		myAnimation_Rotate2.setDuration(500);
		myAnimation_Rotate2.setFillAfter(true);
	}
	

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(!once) {
			// �������Щ��Ԫ�صĻ�ȡ�����ö��Ǹ���ָ��layout�ṹд��
			LinearLayout wrapper = (LinearLayout) getChildAt(0);
			ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
			ViewGroup content = (ViewGroup) wrapper.getChildAt(1);
			arrow = (View) content.getChildAt(0);
			
			// dp to px
			mMenuPadding = (int) TypedValue.applyDimension(
					TypedValue.COMPLEX_UNIT_DIP, mMenuPadding, 
					content.getResources().getDisplayMetrics());
					
			mMenuWidth = mScreenWidth - mMenuPadding;
			mHalfMenuWidth = mMenuWidth / 2;
			menu.getLayoutParams().width = mMenuWidth;
			content.getLayoutParams().width = mScreenWidth;
			
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		super.onLayout(changed, l, t, r, b);
		if(changed) {
			// ���ز˵�
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}
	
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		
		switch (action) {
			case MotionEvent.ACTION_UP:
				int scrollX = getScrollX();
				if (scrollX > mHalfMenuWidth) {	// Ҫ�л���mainҳ
					this.smoothScrollTo(mMenuWidth, 0);
					flag = false;
					
					// ����Ҫ�л��� mainҳ�����֮ǰ���� mainҳ����ִ�ж���
					if(!STATEMAIN) {
						arrow.clearAnimation();
						arrow.startAnimation(myAnimation_Rotate2);
						STATEMAIN = true;
					}
				}
				else {	// �л���menuҳ
					this.smoothScrollTo(0, 0);
					flag = true;
					
					// ����Ҫ�л��� menu ҳ�����֮ǰ���� menuҳ(����mainҳ)����ִ�ж���
					if(STATEMAIN) {
						arrow.clearAnimation();
						arrow.startAnimation(myAnimation_Rotate1);
						STATEMAIN = false;
					}
					
				}
				return true;
		}
		return super.onTouchEvent(ev);
	}
	
	
	public void toggle() {
		
		if (flag) {
			this.smoothScrollTo(mMenuWidth, 0);			// Ҫ�л���mainҳ
			arrow.clearAnimation();
			arrow.startAnimation(myAnimation_Rotate2);
			flag = false;
			STATEMAIN = true;
		}
		else {
			this.smoothScrollTo(0, 0);					// �л���menuҳ
			arrow.clearAnimation();
			arrow.startAnimation(myAnimation_Rotate1);
			flag = true;
			STATEMAIN = false;
		}
	}
	
}
