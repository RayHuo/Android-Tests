package com.ray.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASENAME = "todolist.db";
	private static final int DATABASEVERSION = 1;
	private static final String ITEMS_TABLENAME = "to_do_items";
	private static final String USERS_TABLENAME = "users";
	
	// 创建数据库
	public DataBaseHelper(Context context) {
		super(context, DATABASENAME, null, DATABASEVERSION);
		// TODO Auto-generated constructor stub
	}

	// 创建相关表格
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		try {
			String createItemsTable = "create table " + ITEMS_TABLENAME + 
					" (item_id integer primary key autoincrement, " +
					"content text, " +
					"create_time datetime default current_timestamp, " +
					"deadline datetime default current_timestamp, " +
					"importance boolean, " +
					"emergency boolean, " +
					"comment text, " +
					"alarm datetime default current_timestamp)";
			db.execSQL(createItemsTable);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}

}
