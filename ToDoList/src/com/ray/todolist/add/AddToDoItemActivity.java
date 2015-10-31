package com.ray.todolist.add;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.ray.todolist.MainActivity;
import com.ray.todolist.R;
import com.ray.todolist.db.DataBaseHelper;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.TextView;

public class AddToDoItemActivity extends Activity {
	
	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	private final String TABLENAME = "to_do_items";
	
	private static final String[] MALARMTIME = {"������", "��ǰ5����", "��ǰ30����", "��ǰ1Сʱ", "��ǰ1��", "�Զ���"};
	
	private ImageView addToDoBack = null;
	private ImageView addToDoSave = null;
	private RadioGroup attributeGroup = null;
	private RadioButton imemRadioButton = null;
	private RadioButton noimemRadioButton = null;
	private RadioButton imnoemRadioButton = null;
	private RadioButton noimnoemRadioButton = null;
	private TextView createTime = null;
	private Button deadlineDate = null;
	private Button deadlineTime = null;
	private Spinner alarmSpinner = null;
	private EditText itemContent = null;
	private EditText itemComment = null;
	
	private boolean attributeIpot;
	private boolean attributeEmeg;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.add_to_do_item_layout);
		
		initDataBase();
		initView();
		prepareListener();
		
	}
	
	
	private void initDataBase() {
		mDBHelper = new DataBaseHelper(this);
		mSQLDB = mDBHelper.getWritableDatabase();
	}
	
	
	
	private void initView() {
		addToDoBack = (ImageView) findViewById(R.id.add_to_do_back);
		addToDoSave = (ImageView) findViewById(R.id.add_to_do_save);
		
		attributeGroup = (RadioGroup) findViewById(R.id.add_to_do_attribute);
		imemRadioButton = (RadioButton) findViewById(R.id.add_to_do_imem);
		noimemRadioButton = (RadioButton) findViewById(R.id.add_to_do_noimem);
		imnoemRadioButton = (RadioButton) findViewById(R.id.add_to_do_imnoem);
		noimnoemRadioButton = (RadioButton) findViewById(R.id.add_to_do_noimnoem);
		
		createTime = (TextView) findViewById(R.id.add_to_do_item_create_time);
		deadlineDate = (Button) findViewById(R.id.add_to_do_item_deadline_date);
		deadlineTime = (Button) findViewById(R.id.add_to_do_item_deadline_time);
		
		alarmSpinner = (Spinner) findViewById(R.id.add_to_do_alarm_spinner);
		
		itemContent = (EditText) findViewById(R.id.add_to_do_item_content);
		itemComment = (EditText) findViewById(R.id.add_to_do_item_comment);
	}
	
	
	
	private void prepareListener() {
		// ���˰�ť
		addToDoBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		// ���水ť
		addToDoSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ContentValues cv = new ContentValues();
				cv.put("content", itemContent.getText().toString());
//				cv.put("create_time", createTime.getText().toString());	 // �������Ĭ�ϵ�ǰʱ�䣬���Բ���Ҫ��ӵ����ݿ�
				cv.put("deadline", deadlineDate.getText().toString() + " " + deadlineTime.getText().toString());
				cv.put("importance", attributeIpot);
				cv.put("emergency", attributeEmeg);
				cv.put("comment", itemComment.getText().toString());
				
				SimpleDateFormat sDateFormat2 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				cv.put("alarm", sDateFormat2.format(new Date()));

				mSQLDB.insert(TABLENAME, null, cv);
				
//				finish();	// Ȼ�󷵻ص���һ��ҳ��
				Intent mIntent = new Intent(AddToDoItemActivity.this, MainActivity.class);
				startActivity(mIntent);
				System.exit(0);		// Ҫ�����������ᵼ�·��غ�ҳ��������
			}
		});
		
		
		// ѡ����Ҫ-����������
		attributeGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				clearRadioButtonText();
				if(checkedId == imemRadioButton.getId()) {
					imemRadioButton.setText("��Ҫ-����");
					attributeIpot = true;
					attributeEmeg = true;
				}
				if(checkedId == noimemRadioButton.getId()) {
					noimemRadioButton.setText("����Ҫ-����");
					attributeIpot = false;
					attributeEmeg = true;
				}
				if(checkedId == imnoemRadioButton.getId()) {
					imnoemRadioButton.setText("��Ҫ-������");
					attributeIpot = true;
					attributeEmeg = false;
				}
				if(checkedId == noimnoemRadioButton.getId()) {
					noimnoemRadioButton.setText("����Ҫ-������");
					attributeIpot = false;
					attributeEmeg = false;
				}
				
			}
		});
		
		
		// ���ô���ʱ��
		SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
		Date now = new Date();
		String date = sDateFormat.format(now);
		createTime.setText("����ʱ�� : " + date);
		
		long nextDay = now.getTime() + (24 * 60 * 60 * 1000);
		Date nextDate = new Date(nextDay);
		String nextDateStr = sDateFormat.format(nextDate);
		int space = nextDateStr.indexOf(" ");
		String deadlineDateStr = nextDateStr.substring(0, space);
		String deadlineTimeStr = nextDateStr.substring(space + 1);
		deadlineDate.setText(deadlineDateStr);
		deadlineTime.setText(deadlineTimeStr);
		
		deadlineDate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();  
                new DatePickerDialog(AddToDoItemActivity.this, 
                		new OnDateSetListener() {
							
							@Override
							public void onDateSet(DatePicker view, int year, int monthOfYear,
									int dayOfMonth) {
								// TODO Auto-generated method stub
								deadlineDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
							}
						}, 
                		c.get(Calendar.YEAR), 
                		c.get(Calendar.MONTH), 
                		c.get(Calendar.DAY_OF_MONTH)).show();
             
			}
		});
		
		deadlineTime.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar c = Calendar.getInstance();  
				new TimePickerDialog(AddToDoItemActivity.this, 
						new OnTimeSetListener() {
							
							@Override
							public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
								// TODO Auto-generated method stub
								deadlineTime.setText(hourOfDay + ":" + minute + ":00");
							}
						}, 
						c.get(Calendar.HOUR_OF_DAY), 
						c.get(Calendar.MINUTE), 
						true).show();
			}
		});
		
		// ���� spinner 
		ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item, MALARMTIME);
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		alarmSpinner.setAdapter(spinner_adapter);

	}
	
	
	
	private void clearRadioButtonText() {
		imemRadioButton.setText("");
		noimemRadioButton.setText("");
		imnoemRadioButton.setText("");
		noimnoemRadioButton.setText("");
	}
	
	
	@Override
	// ��׽���ؼ�������Ȼ���������
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// ����ҳ�����ؼ���ֱ���˳�����
		if(keyCode == KeyEvent.KEYCODE_BACK) {
			System.exit(0);
            return true;
		}

		return super.onKeyDown(keyCode, event);
	}
	
	
	@Override
	public void onDestroy() {
		mSQLDB.close();
		super.onDestroy();
	}
}
