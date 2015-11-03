package com.ray.todolist.views;

import javax.security.auth.PrivateCredentialPermission;

import com.ray.todolist.R;
import com.ray.todolist.db.DataBaseHelper;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class ToDoItemActivity extends Activity {
	
	private DataBaseHelper mDBHelper = null;
	private SQLiteDatabase mSQLDB = null;
	private final String TABLENAME = "to_do_items";
	private static final String[] MALARMTIME = {"不提醒", "提前5分钟", "提前30分钟", "提前1小时", "提前1天", "自定义"};
	
	private ImageView itemBack = null;
	private ImageView itemEdit = null;
	private ImageView itemDone = null;
	private ImageView itemDelete = null;
	
	private RadioGroup itemAttributes = null;
	private RadioButton itemImEm = null;
	private RadioButton itemNoImEm = null;
	private RadioButton itemImNoEm = null;
	private RadioButton itemNoImNoEm = null;
	
	private TextView itemLastEditTime = null;
	private Button itemDeadlineDate = null;
	private Button itemDeadlineTime = null;
	
	private Spinner itemAlarm = null;
	private EditText itemContent = null;
	private EditText itemComment = null;
	private Bundle args = null;
	
	private boolean attributeIpot;
	private boolean attributeEmeg;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.to_do_item_layout);
		
		args = getIntent().getExtras();
		
		initDataBase();
		initView();
		prepareListener();
		
		setAllEditableState(false);
	}
	
	
	private void initDataBase() {
		mDBHelper = new DataBaseHelper(this);
		mSQLDB = mDBHelper.getWritableDatabase();	// 可读可写的数据库实例
	}
	
	private void initView() {
		itemBack = (ImageView) findViewById(R.id.to_do_item_back);
		itemEdit = (ImageView) findViewById(R.id.to_do_item_edit);
		itemDone = (ImageView) findViewById(R.id.to_do_item_done);
		itemDelete = (ImageView) findViewById(R.id.to_do_item_delete);
		
		itemAttributes = (RadioGroup) findViewById(R.id.to_do_item_attribute);
		itemImEm = (RadioButton) findViewById(R.id.to_do_item_imem);
		itemNoImEm = (RadioButton) findViewById(R.id.to_do_item_noimem);
		itemImNoEm = (RadioButton) findViewById(R.id.to_do_item_imnoem);
		itemNoImNoEm = (RadioButton) findViewById(R.id.to_do_item_noimnoem);
		
		itemLastEditTime = (TextView) findViewById(R.id.to_do_item_last_edit_time);
		itemDeadlineDate = (Button) findViewById(R.id.to_do_item_deadline_date);
		itemDeadlineTime = (Button) findViewById(R.id.to_do_item_deadline_time);
		
		itemAlarm = (Spinner) findViewById(R.id.to_do_item_alarm_spinner);
		itemContent = (EditText) findViewById(R.id.to_do_item_edit_content);
		itemComment = (EditText) findViewById(R.id.to_do_item_edit_comment);
		
		
		attributeIpot = args.getBoolean("importance");
		attributeEmeg = args.getBoolean("emergency");
		initAttribute();
	}
	
	
	private void prepareListener() {
		itemBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		
		
		itemEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				setAllEditableState(true);	// 全部改为可编辑
			}
		});
		
		itemDone.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				// TODO Auto-generated method stub
				setAllEditableState(false);	// 全部改为不可编辑
			}
		});
		
		
		itemDelete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				int item_id = args.getInt("to_do_id");
				String[] id = { Integer.toString(item_id) };
				mSQLDB.delete(TABLENAME, "_id=?", id);
				finish();
			}
		});
		
		
		// 选择“重要-紧急”属性
		itemAttributes.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO Auto-generated method stub
				clearRadioButtonText();
				if(checkedId == itemImEm.getId()) {
					itemImEm.setText("重要-紧急");
					attributeIpot = true;
					attributeEmeg = true;
				}
				if(checkedId == itemNoImEm.getId()) {
					itemNoImEm.setText("不重要-紧急");
					attributeIpot = false;
					attributeEmeg = true;
				}
				if(checkedId == itemImNoEm.getId()) {
					itemImNoEm.setText("重要-不紧急");
					attributeIpot = true;
					attributeEmeg = false;
				}
				if(checkedId == itemNoImNoEm.getId()) {
					itemNoImNoEm.setText("不重要-不紧急");
					attributeIpot = false;
					attributeEmeg = false;
				}
				
			}
		});
		
		
		itemLastEditTime.setText("最后修改时间 : " + args.getString("to_do_create_time"));
		
		String deadlineStr = args.getString("to_do_deadline");
		itemDeadlineDate.setText(deadlineStr.substring(0, 10));
		itemDeadlineTime.setText(deadlineStr.substring(11, deadlineStr.length()));
		
		
		ArrayAdapter<String> spinner_adapter = new ArrayAdapter<String>(this, 
				android.R.layout.simple_spinner_item, MALARMTIME);
		spinner_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		itemAlarm.setAdapter(spinner_adapter);
		
		
		itemContent.setText(args.getString("to_do_item_content"));
		itemComment.setText(args.getString("to_do_item_comment"));
	}
	
	
	private void initAttribute() {
		clearRadioButtonText();
		if(attributeIpot == true && attributeEmeg == true) {
			itemImEm.setText("重要-紧急");
		}
		if(attributeIpot == false && attributeEmeg == true) {
			itemNoImEm.setText("不重要-紧急");
		}
		if(attributeIpot == true && attributeEmeg == false) {
			itemImNoEm.setText("重要-不紧急");
		}
		if(attributeIpot == false && attributeEmeg == false) {
			itemNoImNoEm.setText("不重要-不紧急");
		}
	}
	
	private void clearRadioButtonText() {
		itemImEm.setText("");
		itemNoImEm.setText("");
		itemImNoEm.setText("");
		itemNoImNoEm.setText("");
	}
	
	/*
	 * 根据参数 state 来判断全部内容相关控件置为不能编辑(false) 或者 都能编辑(true)
	 * */
	private void setAllEditableState(boolean state) {
		if(state) {	// 全部可编辑
			itemEdit.setVisibility(View.GONE);		
			itemDone.setVisibility(View.VISIBLE);
		}
		else {	// 全部不可编辑
			itemEdit.setVisibility(View.VISIBLE);		
			itemDone.setVisibility(View.GONE);
		}
		
		itemAttributes.setClickable(state);
		itemDeadlineDate.setClickable(state);
		itemDeadlineTime.setClickable(state);
		itemAlarm.setClickable(state);
		itemContent.setFocusable(state);
		itemComment.setFocusable(state);
	}
	
	
	@Override
	public void onDestroy() {
		mSQLDB.close();
		super.onDestroy();
	}
	
}
