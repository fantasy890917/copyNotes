package com.huaqin.notes;

import java.util.HashMap;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

public class TodoAsyncQuery extends AsyncQueryHandler{
	private static final String TAG =  "TodoAsyncQuery" ;
	public static final Uri TODO_URI = Uri.parse("content://com.huaqin.todos/todos");
	private static HashMap<Context,TodoAsyncQuery> sInstance = new HashMap<Context,TodoAsyncQuery>();
	
	/**
	 * It suggest to use ApplicationContext to create a TodoAsyncQuery. If the context is not
	 *  ApplicaitonContext, it must be freed if the context is not used.
	 * @param context
	 * @return
	 */
	public static TodoAsyncQuery getInstance(Context context){
		if(!sInstance.containsKey(context)){
			TodoAsyncQuery aysnQuery = new TodoAsyncQuery(context);
			sInstance.put(context, aysnQuery);
		}
		return sInstance.get(context);
	}
	
	/**
	 * Remove the context if we don't need it.
	 * @param context
	 */
	public static void free(Context context){
		if(sInstance.containsKey(context)){
			sInstance.remove(context);
		}
	}
	
	/**
	 *  Free all contexts.
	 */
	public static void freeAll(){
		sInstance.clear();
	}
	
	public TodoAsyncQuery(Context context) {
		super(context.getContentResolver());
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
		// TODO Auto-generated method stub
		LogUtils.d(TAG, "onQueryComplete token=" + token);
		if(cookie instanceof QueryListener){
			QueryListener listener = (QueryListener) cookie ;
			listener.onQueryComplete(token, cursor);
		}else{
			cursor.close();
            LogUtils.e(TAG, "cookie is another object: " + cookie);
		}
	}

	@Override
	protected void onInsertComplete(int token, Object cookie, Uri uri) {
		// TODO Auto-generated method stub
		LogUtils.d(TAG, "onInsertComplete token=" + token + " uri=" + uri);
		if(cookie instanceof QueryListener){
			QueryListener listener = (QueryListener) cookie ;
			listener.onInsertComplete(token, uri);
		}
	}

	@Override
	protected void onUpdateComplete(int token, Object cookie, int result) {
		// TODO Auto-generated method stub
		LogUtils.d(TAG, "onUpdateComplete token=" + token + " result=" + result);
		if(cookie instanceof QueryListener){
			QueryListener listener = (QueryListener) cookie ;
			listener.onUpdateComplete(token, result);
		}
	}

	@Override
	protected void onDeleteComplete(int token, Object cookie, int result) {
		// TODO Auto-generated method stub
		 LogUtils.d(TAG, "onDeleteComplete token=" + token + " result="  + result);
		 if(cookie instanceof QueryListener){
			QueryListener listener = (QueryListener) cookie ;
			listener.onDeleteComplete(token, result);
		 }
	}

}
