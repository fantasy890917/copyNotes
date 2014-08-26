package com.huaqin.notes;

import com.huaqin.notes.provider.TodosDatabaseHelper.TodoColumn;

import android.database.Cursor;

public class Utils {
	private static final String TAG = "TAG";
	
	/**
	 * return column value by name.
	 * @param name
	 * @param cursor
	 * @return
	 */
	public static String getColumnByName(String name, Cursor cursor){
		int index =  cursor.getColumnIndex(name);
		if(index == -1){
			LogUtils.d(TAG, "Column Name " + name + "is not found");
		}
		String content;
		if(name.equals(TodoColumn.ID)){
			content =""+cursor.getInt(index);
			return content;
		}
		content = cursor.getString(index);
		return content;
	}
}
