package com.rayhuo.todolist.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {

	private static final String DATABASENAME = "todolist.db";
	private static final int DATABASEVERSION = 2;					// 数据库版本，会在构造函数中用到
	private static final String ITEMS_TABLENAME = "to_do_items";
	private static final String NOTIFY_TABLENAME = "notifications";
	private static final String USERS_TABLENAME = "users";
	private static final String MOOD_TABLENAME = "moods";


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
			// 创建“待办事项”数据表
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

			// 创建“日常闹钟”数据表
			String createNotifyTable = "create table " + NOTIFY_TABLENAME + 
					" (notify_id integer primary key autoincrement, " +
					"alarm_time datetime default current_timestamp)";
			db.execSQL(createNotifyTable);

			String createMoodTable = "create table " + MOOD_TABLENAME +
					" (mood_id integer primary key autoincrement, " +
					"mark_time datetime default current_timestamp, " +
					"content text, " +
					"image text)";
			db.execSQL(createMoodTable);

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		/* 创建“心情”数据表，图片从相机或者图库中获取，保存在sd卡或者手机本地存储空间中，
		 * SQLite中保存的是图片的路径，这样数据库的压力才不大
		 */
		if(DATABASEVERSION == 2) {
//			String createMoodTable = "create table " + MOOD_TABLENAME +
//					" (mood_id integer primary key autoincrement, " +
//					"mood_rate integer, " +
//					"mark_time datetime default current_timestamp, " +
//					"content text, " +
//					"image text)";
//			db.execSQL(createMoodTable);
		}

	}

}
