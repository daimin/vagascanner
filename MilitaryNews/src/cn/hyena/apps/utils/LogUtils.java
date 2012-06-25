package cn.hyena.apps.utils;

import android.util.Log;

public class LogUtils {
	public static boolean isPrint = true;
	public static boolean isRecord = false;
	
	public static void d(String TAG,String message) {
		d(TAG,message, null);
	}
	
	public static void d(String TAG,String message, Throwable t) {
		if (isPrint) {
			if (message != null) {
				Log.d(TAG, message);
				fileWrite("log","Debug:"+ message);
			}
			if (t != null) {
				Log.d(TAG, t.toString());
				fileWrite("log","Debug:"+ t.toString());
			}
		}
	}
	
	
	public static void i(String TAG,String message) {
		i(TAG,message, null);
	}
	
	public static void i(String TAG,String message, Throwable t) {
		if (isPrint) {
			if (message != null) {
				Log.i(TAG, message);
				fileWrite("log","Debug:" + message);
			}
			if (t != null) {
				Log.i(TAG, t.toString());
				fileWrite("log","Debug:" + t.toString());
			}
		}
	}
	
	public static void e(String TAG,String message) {
		e(TAG,message, null);
	}
	
	public static void e(String TAG,String message, Throwable t) {
		if (isPrint) {
			if (message != null) {
				Log.e(TAG, message);
				fileWrite("log","Debug:" + message);
			}
			if (t != null) {
				Log.e(TAG, t.toString());
				fileWrite("log","Debug:" + t.toString());
			}
		}
	}
	
	public static void v(String TAG,String message) {
		v(TAG,message, null);
	}
	
	public static void v(String TAG,String message, Throwable t) {
		if (isPrint) {
			if (message != null) {
				Log.v(TAG, message);
				fileWrite("log","Debug:" + message);
			}
			if (t != null) {
				Log.v(TAG, t.toString());
				fileWrite("log","Debug:" + t.toString());
			}
		}
	}
	
	public static void w(String TAG,String message) {
		w(TAG,message, null);
	}
	
	public static void w(String TAG,String message, Throwable t) {
		if (isPrint) {
			if (message != null) {
				Log.w(TAG, message);
				fileWrite("log","Debug:" + message);
			}
			if (t != null) {
				Log.w(TAG, t.toString());
				fileWrite("log","Debug:" + t.toString());
			}
		}
	}
	
	//将日志写入文件
	public static void fileWrite(String filename,String message) {
		if(isRecord){
			if (message != null){
				FileUtils.fileWrite("/sdcard/MilitaryNews/"+filename+".txt", message);
			}
		}
	}	
}
