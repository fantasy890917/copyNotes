package com.huaqin.notes.provider;

import com.huaqin.notes.LogUtils;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TodosDatabaseHelper extends SQLiteOpenHelper{

	private static final String TAG = "TodosDatabaseHelper";
		
	private static final String DATABASE_NAME = "todos.db";
		
	private static final int DAY_IN_SECONDS = 24 * 60 * 60 ;
		
	static final int  DATABASE_VERSION = 308 ;
		
	private static 	TodosDatabaseHelper mSingleton = null ;
	
	public interface Tables {
		String TODOS = "Todos";
	}
	
	public interface TodoColumn {
		String ID ="_id";
		String TITLE ="title";
		String DESCRIPTION = "description";
		String STATUS = "status";
		String DUE_DATE ="dueDate";
		String CREATE_TIME = "create_time";
		String COMPLETE_TIME ="complete_time";
	}
	
	public static synchronized TodosDatabaseHelper getInstance(Context context){
		if(mSingleton == null){
			mSingleton = new TodosDatabaseHelper(context);
		}
		return mSingleton ;
	}
	
	private TodosDatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

/**
 * 2 methods to implement from SQLiteOpenHelper:
	- 
	 android.database.sqlite.SQLiteOpenHelper.onCreate()
	- 
	 android.database.sqlite.SQLiteOpenHelper.onUpgrade()
 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		LogUtils.d(TAG,"onCreate()");
		createTable(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onOpen(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		super.onOpen(db);
	}
	
	/**
	 * create table todos with sql
	 */
	private void createTable(SQLiteDatabase db){
		db.execSQL("CREATE TABLE "+Tables.TODOS+" ( "+
				TodoColumn.ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
				TodoColumn.TITLE + " TEXT," +
				TodoColumn.DESCRIPTION + " TEXT," +
				TodoColumn.STATUS + " INTEGER NOT NULL DEFAULT 0," +
				TodoColumn.DUE_DATE + " INTEGER NOT NULL DEFAULT 0," +
				// create time in millis
                TodoColumn.CREATE_TIME + " INTEGER," +
                // complete time in millis
                TodoColumn.COMPLETE_TIME + " INTEGER" +
				");");
	}
}
