package com.huaqin.notes;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;

public class TimeChangeReceiver extends BroadcastReceiver{
	private static final String TAG = "TimeChangeReceiver";
	
	private static final int MSG_DATE_CHANGE = 1 ;
	private static final int MSG_TIME_TICK = 2;
	
	interface TimeChangeListener {
        void onDateChange();
        void onTimePick();
    }
	
	private ArrayList<TimeChangeListener> mListeners = new ArrayList<TimeChangeListener>();
	
	private Handler mHandler = new Handler(){
		@Override
        public void handleMessage(Message msg) {
			switch(msg.what){
				case  MSG_DATE_CHANGE:
					for(TimeChangeListener listener : mListeners){
						listener.onDateChange();
					}
					break;
				
				case MSG_TIME_TICK:
					for(TimeChangeListener listener : mListeners){
						listener.onTimePick();
					}
					break;
				default :
					break;
			}
		}
	};
	
	public void addDateChangeListener(TimeChangeListener listener){
		if(!mListeners.contains(listener)){
			mListeners.add(listener);
		}
	}
	
	public void clearDateChangeListener(){
		mListeners.clear();
	}
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		final String action = intent.getAction();
		//log
		if(Intent.ACTION_TIME_CHANGED.equals(action)
                || Intent.ACTION_TIMEZONE_CHANGED.equals(action)
                || Intent.ACTION_DATE_CHANGED.equals(action)){
			Message msg = mHandler.obtainMessage();
			msg.what = MSG_DATE_CHANGE;
			mHandler.sendMessage(msg);
		}else if(Intent.ACTION_TIME_TICK.equals(action)){
			Message msg = mHandler.obtainMessage();
			msg.what = MSG_TIME_TICK;
			mHandler.sendMessage(msg);
		}
	}
	
	public static TimeChangeReceiver registTimeChangeReceiver(Context context){
		TimeChangeReceiver dateChangeReceiver = new TimeChangeReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(Intent.ACTION_TIME_CHANGED);
		filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
		filter.addAction(Intent.ACTION_DATE_CHANGED);
		filter.addAction(Intent.ACTION_TIME_TICK);
		return dateChangeReceiver;
	}
}
