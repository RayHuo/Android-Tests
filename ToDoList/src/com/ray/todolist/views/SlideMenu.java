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

	private int mScreenWidth;	// 屏幕宽度
	
	private int mMenuPadding = 50;	// 侧栏菜单中距离右边的宽度
	
	private int mMenuWidth;		// 菜单的宽度
	private int mHalfMenuWidth;	// 菜单一半的宽度，用于判断滑动到什么情况显示菜单
	
	private boolean once;		// 用于限制 onMeasure 只执行一次
	
	private View arrow = null;
	private Animation myAnimation_Rotate1 = null;
	private Animation myAnimation_Rotate2 = null;
	private boolean flag = false;
	private boolean STATEMAIN = true;	// 有的时候进行了拖拉行为(因为距离还没有达到切换条件)，但没有改变主界面，所以箭头不应该进行动画
	
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
			// 下面的这些子元素的获取和设置都是根据指定layout结构写的
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
			// 隐藏菜单
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
				if (scrollX > mHalfMenuWidth) {	// 要切换到main页
					this.smoothScrollTo(mMenuWidth, 0);
					flag = false;
					
					// 现在要切换到 main页，如果之前不在 main页，则执行动画
					if(!STATEMAIN) {
						arrow.clearAnimation();
						arrow.startAnimation(myAnimation_Rotate2);
						STATEMAIN = true;
					}
				}
				else {	// 切换到menu页
					this.smoothScrollTo(0, 0);
					flag = true;
					
					// 现在要切换到 menu 页，如果之前不在 menu页(即在main页)，则执行动画
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
			this.smoothScrollTo(mMenuWidth, 0);			// 要切换到main页
			arrow.clearAnimation();
			arrow.startAnimation(myAnimation_Rotate2);
			flag = false;
			STATEMAIN = true;
		}
		else {
			this.smoothScrollTo(0, 0);					// 切换到menu页
			arrow.clearAnimation();
			arrow.startAnimation(myAnimation_Rotate1);
			flag = true;
			STATEMAIN = false;
		}
	}
	
}
