package com.huaqin.notes.provider;

import java.util.HashMap;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

import com.huaqin.notes.provider.TodosDatabaseHelper.Tables;
import com.huaqin.notes.provider.TodosDatabaseHelper.TodoColumn;


public class TodoProvider extends ContentProvider {
	public static final String TAG = "TodoProvider";
	public static final String AUTHORITY = "com.huaqin.todos";
	private static final int ALLROWS = 1;
	private static final int SINGLE_ROW = 2;
	private Context mContext;
	private TodosDatabaseHelper mDbHelper;
	private SQLiteDatabase mDb;
	
	private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	private static final HashMap<String, String> TODO_PROJECTTION_MAP;
	static{
		URI_MATCHER.addURI(AUTHORITY, "todos", ALLROWS);
		URI_MATCHER.addURI(AUTHORITY, "todos/#", SINGLE_ROW);
		TODO_PROJECTTION_MAP = new HashMap<String, String>();
        TODO_PROJECTTION_MAP.put(TodoColumn.ID, TodoColumn.ID);
        TODO_PROJECTTION_MAP.put(TodoColumn.TITLE, TodoColumn.TITLE);
        TODO_PROJECTTION_MAP.put(TodoColumn.DESCRIPTION, TodoColumn.DESCRIPTION);
        TODO_PROJECTTION_MAP.put(TodoColumn.STATUS, TodoColumn.STATUS);
        TODO_PROJECTTION_MAP.put(TodoColumn.DUE_DATE, TodoColumn.DUE_DATE);
        TODO_PROJECTTION_MAP.put(TodoColumn.CREATE_TIME, TodoColumn.CREATE_TIME);
        TODO_PROJECTTION_MAP.put(TodoColumn.COMPLETE_TIME, TodoColumn.COMPLETE_TIME);
	}
	private static final String GENERIC_ID = "_id";
    protected static final String SQL_WHERE_ID = GENERIC_ID + "=?";
    
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
		queryBuilder.setProjectionMap(TODO_PROJECTTION_MAP);
		final int match =  URI_MATCHER.match(uri);
		switch(match){
			case ALLROWS:
				//selection = appendAccountFromParameterToSelection(selection, uri);
				break;
			case SINGLE_ROW:
				//selectionArgs = insertSelectionArg(selectionArgs, uri.getPathSegments().get(1));
				queryBuilder.appendWhere(SQL_WHERE_ID);
			default:
	            throw new IllegalArgumentException("Unknown URL " + uri);
		}
		return queryBuilder.query(mDb, projection, selection, selectionArgs, groupBy, null, sortOrder);
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
		long id = 0;
		String nullColumnHack = null;
		switch(match){
			case ALLROWS:
				id = mDb.insert(Tables.TODOS, nullColumnHack, values);
				break;
			case SINGLE_ROW:
				throw new UnsupportedOperationException("Cannot insert into that URL: " + uri);
			default:
                throw new IllegalArgumentException("Unknown URL " + uri);
		}
		
		if(id < 0){
			return null ;
		}
		return ContentUris.withAppendedId(uri, id);
	}

	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		final int match = URI_MATCHER.match(uri);
		switch(match){
			case ALLROWS:
				mDb.delete(Tables.TODOS, selection, selectionArgs);
	
			case SINGLE_ROW:
				long id = ContentUris.parseId(uri);
				return mDb.delete(Tables.TODOS, SQL_WHERE_ID, new String[] {String.valueOf(id)});
			default:
	            throw new IllegalArgumentException("Unknown URL " + uri);
		}

	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		final int match = URI_MATCHER.match(uri);
		switch (match){
			 case ALLROWS:
				 return mDb.update(Tables.TODOS, values,selection, selectionArgs);
			 case SINGLE_ROW:
				 long id = ContentUris.parseId(uri);
				 return mDb.update(Tables.TODOS, values, SQL_WHERE_ID, new String[] {String.valueOf(id)});
		}
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
