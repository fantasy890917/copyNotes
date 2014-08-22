package com.huaqin.notes.provider;

import com.huaqin.notes.provider.TodosDatabaseHelper.Tables;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;


public class TodoProvider extends ContentProvider {
	public static final String TAG = "TodoProvider";
	public static final String AUTHORITY = "com.huaqin.todos";
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	private Context mContext;
	private TodosDatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	
	static{
		URI_MATCHER.addURI(AUTHORITY, "todos", ALLROWS);
		URI_MATCHER.addURI(AUTHORITY, "todos/#", SINGLE_ROW);
	}
	/**
	 * 6 methods to implement from ContentProvider:
	- android.content.ContentProvider.onCreate()
	- android.content.ContentProvider.query()
	- android.content.ContentProvider.getType()
	- android.content.ContentProvider.insert()
	- android.content.ContentProvider.delete()
	- android.content.ContentProvider.update()
	 */
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		return initialize();
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		
		final SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
		String groupBy = null ;
		String limit = null ;
		
		queryBuilder.setTables(Tables.TODOS);
		final int match =  URI_MATCHER.match(uri);
		switch(match){
		case ALLROWS:
			
		}
		return null;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch(URI_MATCHER.match(uri)){
			case ALLROWS:
				return "vnd.android.cursor.dir/todo";
			case SINGLE_ROW:
				return "vnd.android.cursor.dir/todo";
			default :
				throw new IllegalArgumentException("Unsupported URI: "+ uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		final int match = URI_MATCHER.match(uri);
		
		return null;
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	/**
	 * 构造底层数据库
	 * @return true
	 */
	private boolean initialize(){
		mContext = getContext();
		mDbHelper = TodosDatabaseHelper.getInstance(mContext);
		mDb = mDbHelper.getWritableDatabase();
		return true;
	}
}
