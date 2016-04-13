package com.slimo.smatoast;

import android.util.Log;


public class LogUtil {
	private static boolean isDebug=false;
	public static void e(String TAG,String msg)
	{
		if(isDebug)
		{
			Log.e(TAG, msg);
		}
	}
	public static void w(String TAG,String msg)
	{
		if(isDebug)
		{
			Log.e(TAG, msg);
		}
	}
	public static void d(String TAG,String msg)
	{
		if(isDebug)
		{
			Log.e(TAG, msg);
		}
	}
	public static void i(String TAG,String msg)
	{
		if(isDebug)
		{
			Log.e(TAG, msg);
		}
	}
	public static void v(String TAG,String msg)
	{
		if(isDebug)
		{
			Log.e(TAG, msg);
		}
	}
	public static void e(String TAG,String msg,Exception e)
	{
		if(isDebug)
		{
			Log.e(TAG, msg,e);
		}
	}
}
