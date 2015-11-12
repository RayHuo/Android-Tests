package com.rayhuo.todolist.add;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import com.rayhuo.todolist.R;
import com.rayhuo.todolist.db.DataBaseHelper;
import com.rayhuo.todolist.utils.AlarmActivity;

import java.util.Calendar;

public class AddNotificationActivity extends Activity {
	
	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	private final String TABLENAME = "notifications";
	
	private Button add_btn = null;
	private AlarmManager mAlarmMag = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_notification_layout);
		
		initDataBase();
		initView();
		prepareListener();
		
	}
	
	
	private void initDataBase() {
		mDBHelper = new DataBaseHelper(this);
		mSQLDB = mDBHelper.getWritableDatabase();
	}
	
	
	private void initView() {
		mAlarmMag = (AlarmManager) getSystemService(ALARM_SERVICE);
		add_btn = (Button) findViewById(R.id.add_notify_btn);
	}
	
	
	private void prepareListener() {
		add_btn.setOnClickListener(addClickListener);
	}
	
	
	private OnClickListener addClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View view) {
			// TODO Auto-generated method stub
			Calendar cal = Calendar.getInstance();
			
			new TimePickerDialog(AddNotificationActivity.this, 
					new OnTimeSetListener() {

						@Override
						public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
							// TODO Auto-generated method stub
							// 启动指定组件
			                Intent intent = new Intent(AddNotificationActivity.this, AlarmActivity.class);  
//							Intent intent = new Intent(MainActivity.this, AlarmReceiver.class);  
							// 创建PendingIntent对象，封装Intent
			                PendingIntent pi = PendingIntent.getActivity(AddNotificationActivity.this, 0, intent, 0);  
			                Calendar setCal = Calendar.getInstance();
							// 根据用户选择的时间设置定时器时间
			                setCal.set(Calendar.HOUR_OF_DAY, hourOfDay);  
			                setCal.set(Calendar.MINUTE, minute);
							// 设置AlarmManager将在Calendar对应的时间启动指定组件
			                mAlarmMag.set(AlarmManager.RTC_WAKEUP, setCal.getTimeInMillis(), pi);
							// 显示闹铃设置成功的提示信息
			                Toast.makeText(AddNotificationActivity.this, "闹铃设置成功啦", Toast.LENGTH_SHORT).show();
						}
				
					}, 
					cal.get(Calendar.HOUR_OF_DAY), 
					cal.get(Calendar.MINUTE), 
					true).show();
		}
	};
	
	
	@Override
	public void onDestroy() {
		mSQLDB.close();
		super.onDestroy();
	}
	
}



