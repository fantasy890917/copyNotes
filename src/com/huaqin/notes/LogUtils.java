package com.huaqin.notes;

import android.util.Log;

public class LogUtils {
	private static final boolean ENABLE_XLOG = true;
    private static final String LOG_TAG = "TODO";
    
    public static void v(String tag, String msg) {
        if (ENABLE_XLOG) {
            Log.v(LOG_TAG, "<<" + tag + ">>: " + msg);
        } else {
            Log.v(LOG_TAG, tag + ": " + msg);
        }
    }
    
    public static void d(String tag, String msg) {
        if (ENABLE_XLOG) {
        	Log.d(LOG_TAG, "<<" + tag + ">>: " + msg);
        } else {
            Log.d(LOG_TAG, tag + ": " + msg);
        }
    }
    
    public static void e(String tag, String msg) {
        if (ENABLE_XLOG) {
        	Log.e(LOG_TAG, "<<" + tag + ">>: " + msg);
        } else {
            Log.e(LOG_TAG, tag + ": " + msg);
        }
    }
    
    public static void i(String tag, String msg) {
        if (ENABLE_XLOG) {
        	Log.i(LOG_TAG, "<<" + tag + ">>: " + msg);
        } else {
            Log.i(LOG_TAG, tag + ": " + msg);
        }
    }
    
    public static void w(String tag, String msg) {
        if (ENABLE_XLOG) {
        	Log.w(LOG_TAG, "<<" + tag + ">>: " + msg);
        } else {
            Log.w(LOG_TAG, tag + ": " + msg);
        }
    }
}
