package com.huaqin.notes;

import android.database.Cursor;
import android.net.Uri;

public interface QueryListener {
	
	void startQuery(String selection);
	
	void onQueryComplete(int token, Cursor cursor);
	
	void startDelete(TodoInfo info);
	
	void onDeleteComplete(int token, int result);
	
	void startUpdate(TodoInfo info);

    void onUpdateComplete(int token, int result);

    void startInsert(TodoInfo info);

    void onInsertComplete(int token, Uri uri);
}
