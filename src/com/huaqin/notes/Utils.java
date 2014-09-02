package com.huaqin.notes;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Locale;

import android.content.Context;
import android.database.Cursor;
import android.text.format.Time;

import com.huaqin.notes.provider.TodosDatabaseHelper.TodoColumn;

public class Utils {
	private static final String TAG = "TAG";
	
	public static final int OPERATOR_NONE = 0;
	
	public static final int DATE_NO_EXPIRE = -1;
    public static final int DATE_EXPIRED = 0;
    public static final int DATE_NOT_EXPIRE = 11;
    
	public static final int DATE_TYPE_DUE = 1;
    public static final int DATE_TYPE_COMPLETE = 2;
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
	
	public static String getDateText(Context context, long dateMillis, int type){
		String timeFormat = null;
		boolean is24HourMode = get24HourMode(context);
		if(type == DATE_TYPE_DUE) {// get dueDate
			if(dateMillis < 0){
				return context.getResources().getString(R.string.no_expire_date);
			}
			timeFormat = context.getResources().getString(R.string.due_day_time_format);
			
		} else if (type == DATE_TYPE_COMPLETE) {// get complete date
			if(dateMillis < 0){
				return null;
			}
			timeFormat = context.getResources().getString(R.string.done_time_format);
		} else {// no this type return null.
			LogUtils.w(TAG, "format date failed.No this type:" + type);
			return null;
		}
		
		if("en".equals(Locale.getDefault().getLanguage()) && !is24HourMode && type == DATE_TYPE_COMPLETE){
            Date date = new Date(dateMillis);
            String date_display= new SimpleDateFormat("yyyy-MM-dd hh:mm a").format( date);
            return date_display;
        }
		
		Time time = new Time();
		time.set(dateMillis);
		
		return time.format(timeFormat);
	}
	
	public static boolean get24HourMode(Context context){
		return android.text.format.DateFormat.is24HourFormat(context);
	}
}
