package com.slimo.smatoast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import com.slimo.smatoast.service.SMAToastService;
import com.slimo.smatoast.service.SMAToastService.ToastBinder;

import android.R.string;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.text.TextDirectionHeuristic;
import android.util.Log;
import android.view.WindowManager;

public class SMAToastUtil {

	static String TAG=SMAToastUtil.class.getSimpleName();
	static SMAToastUtil instance;
	SMAToastService mService;
	private Context context;
	/***
	 * 
	 * @author slimo
	 *
	 */ 
	public static enum ToastShowDelay
	{
		
		SHOW_SHORT,//1 second default
		SHOW_LONG,//2 second
		SHOW_MAXLONG//3 second
	}
	public static enum ToastShowAlign
	{
		
		SMATOAST_CENTER_IN_PARENT,//
		SMATOAST_CENTER_VER,//
		SMATOAST_CENTER_HOR,
		SMATOAST_TOP,
		SMATOAST_BOTTOM,
		SMATOAST_RIGHT,
		SMATOAST_LEFT
	}
	private SMAToastUtil(Context context) {
		// TODO Auto-generated constructor stub
		this.context=context;
		this.startService();
	}
	private static SMAToastUtil getInstance(Context context)
	{
		if(instance==null)
		{
			instance=new SMAToastUtil(context);
		}
		return instance;
	}
	public static void show(Context context,String message)
	{
		show(context, message, ToastShowDelay.SHOW_SHORT);
		
	}
	public static void show(Context context,String message,ToastShowDelay showDelay)
	{

		getInstance(context).doshow(context, message,showDelay);
		
	}
	public static void show(Context context,String message,ToastShowAlign align,ToastShowDelay showDelay)
	{

		getInstance(context).doshow(context, message,showDelay);
		
	}
	public static void show(Context context,String message,WindowManager.LayoutParams viewlayout,ToastShowDelay showDelay)
	{

		getInstance(context).doshow(context, message,viewlayout,showDelay);
		
	}
	private int getDelay(ToastShowDelay showDelay)
	{
		int delay=1;
		switch (showDelay) {
		case SHOW_SHORT:
			delay=1;
			break;
		case SHOW_LONG:
			delay=2;
			break;
		case SHOW_MAXLONG:
			delay=3;
			break;
		default:
			break;
		}
		return delay;
	}
	private  void doshow(Context context,String message,ToastShowDelay showDelay)
	{
		doshow(context, message, null,showDelay);
	}
	private  void doshow(Context context,String message,WindowManager.LayoutParams viewlayout,ToastShowDelay showDelay)
	{
		int delay=getDelay(showDelay);
		doshow(context, message, viewlayout,delay);
	}
	private  void doshow(Context context,String message,WindowManager.LayoutParams viewlayout,int delay)
	{
		ToastTask task=new ToastTask(context);
		if(viewlayout!=null)
		{
			task.setParams(viewlayout);
		}
		task.setDelay(delay);
		task.setMessage(message);
		if (mService!=null) {
			detectList();
			insertTask(task);
		}
		else{
			list.add(task);
			startService();
		}
	}
	
	private void insertTask(ToastTask task)
	{
		if(mService!=null)
		{
			mService.addTask(task);
		}
	}
	private List<ToastTask> list=new ArrayList<>();
	ServiceConnection connection=new ServiceConnection() {
		
		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			
			Log.e(TAG, "onServiceDisconnected:"+name);
		}
		
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			ToastBinder binder=(ToastBinder)service;
			mService=(SMAToastService) binder.getService();
			Log.e(TAG, "onServiceConnected:"+name);
			detectList();

			
		}
	};
	private void detectList()
	{
		if(list!=null&&list.size()>0)
		{
			List<ToastTask> copyList=new ArrayList<>(list);
			for(ToastTask task:copyList)
			{
				insertTask(task);
				list.remove(task);
				
			}
		}
	}
	private void startService()
	{
		Intent intent=new Intent(context,SMAToastService.class);
		this.context.bindService(intent, connection, Context.BIND_AUTO_CREATE);
	}
}
