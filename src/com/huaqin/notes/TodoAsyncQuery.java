package com.huaqin.notes;

import android.content.AsyncQueryHandler;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

public class TodoAsyncQuery extends AsyncQueryHandler{

	public TodoAsyncQuery(ContentResolver cr) {
		super(cr);
		// TODO Auto-generated constructor stub
	}


	@Override
	protected void onQueryComplete(int token, Object cookie, Cursor cursor) {
		// TODO Auto-generated method stub
		super.onQueryComplete(token, cookie, cursor);
	}

	@Override
	protected void onInsertComplete(int token, Object cookie, Uri uri) {
		// TODO Auto-generated method stub
		super.onInsertComplete(token, cookie, uri);
	}

	@Override
	protected void onUpdateComplete(int token, Object cookie, int result) {
		// TODO Auto-generated method stub
		super.onUpdateComplete(token, cookie, result);
	}

	@Override
	protected void onDeleteComplete(int token, Object cookie, int result) {
		// TODO Auto-generated method stub
		super.onDeleteComplete(token, cookie, result);
	}

}
