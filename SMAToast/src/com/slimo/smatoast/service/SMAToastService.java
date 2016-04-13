package com.slimo.smatoast.service;


import java.util.LinkedList;
import java.util.Queue;

import com.slimo.smatoast.ToastCallBack;
import com.slimo.smatoast.ToastTask;
import android.annotation.SuppressLint;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

@SuppressLint("HandlerLeak")
public class SMAToastService extends Service {

	private static final String TAG = null;
	WindowManager mWindowManager;
	private boolean isStop=false;
	private AlphaAnimation mShowAnimation;
	private AlphaAnimation mHideAnimation;
	private Queue<ToastTask> taskList;
	private String mWaitStr=new String("Slimo");
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return new ToastBinder();
	}
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		init();
	}
	private void init()
	{
		taskList=new LinkedList<>();
		mShowAnimation=new AlphaAnimation(0.0f, 1.0f);   
		mShowAnimation.setDuration(500); 
		mHideAnimation= new AlphaAnimation(1.0f, 0.0f);  
		mHideAnimation.setDuration(500);   
		mHideAnimation.setFillAfter(true);
		mHideAnimation.setFillBefore(false);
		new ToastThread().start();
		initLayout(this);
	}
	public class ToastBinder extends Binder
	{
		public Service getService()
		{
			return SMAToastService.this;
		}
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		return super.onStartCommand(intent, flags, startId);
	}
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}
	@Override
	public boolean onUnbind(Intent intent) {
		// TODO Auto-generated method stub
		return super.onUnbind(intent);
	}
	public void addTask(ToastTask task)
	{
		taskList.add(task);
		this.carryOn();
	}
	private void initLayout(Context context)
	{

        //获取的是WindowManagerImpl.CompatModeWrapper  
		mWindowManager = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);  

  	
	}
	class ToastThread extends Thread
	{
		public void run()
		{
			while(!isStop)
			{
				if(taskList.isEmpty())
				{
					waitForState(2*1000);
				}else{
					ToastTask task=taskList.peek();
					if(task.getStatus()==1)
					{
						long now=System.currentTimeMillis();
						long showtime=task.getShowtime();
						long d=(now-showtime);
						d=d-task.getDelay()*1000+1000;
						if(d>-200)
						{
							hide(task);
						}

					}else if(task.getStatus()==0)
					{
						task.setStatus(1);
						show(task);
					}
			
					waitForState(500);
					
				}
			}
		}
	}
	private void show(ToastTask task)
	{
		Message msg=new Message();
		msg.what=HANDLER_MSG_WHAT_SHOW;
		msg.obj=task;
		mHandler.sendMessageDelayed(msg, 100);
	}
	private void doshow(ToastTask task)
	{
		task.setShowtime(System.currentTimeMillis());
		View rootview=task.getDrawView();
		mWindowManager.addView(rootview, task.getParams());
		View contentview=task.getContentView();
		contentview.startAnimation(mShowAnimation);
		
	}
	private void hide(ToastTask task)
	{
		Message msg=new Message();
		msg.what=HANDLER_MSG_WHAT_REMOVE;
		msg.obj=task;
		mHandler.sendMessageDelayed(msg, 100);
	}
	@SuppressLint("NewApi")
	private void dohide(ToastTask task)
	{
		taskList.remove(task);
		final View rootview=task.getDrawView();
		View contentview=task.getContentView();
		mHideAnimation.setAnimationListener(new Animation.AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				mWindowManager.removeView(rootview);
			}
		});
		contentview.startAnimation(mHideAnimation);


	}
	private final static int HANDLER_MSG_WHAT_SHOW=1;
	private final static int HANDLER_MSG_WHAT_REMOVE=2;

	Handler mHandler=new Handler()
	{

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			//super.handleMessage(msg);
			switch (msg.what) {
			case HANDLER_MSG_WHAT_SHOW:
			{
				ToastTask task=(ToastTask)msg.obj;
				if(task!=null)
				{
					doshow(task);
				}
			}
				break;
			case HANDLER_MSG_WHAT_REMOVE:
			{
				ToastTask task=(ToastTask)msg.obj;
				if(task!=null)
				{
					dohide(task);
				}
			}
				break;
			default:
				break;
			}
		}
		
		
	};

	private void carryOn()
	{
		synchronized (mWaitStr) {
			mWaitStr.notifyAll();
			
		}
	}
	private void waitForState(int millsecond)
	{
		synchronized (mWaitStr) {
			try {
				mWaitStr.wait(millsecond);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	public void show(Context context,String msg)
	{
		
	}
	public void show(Context context,String msg,ToastCallBack callBack)
	{
		
	}
	public void show(Context context,String msg,LayoutParams params)
	{
		
	}
	public void show(Context context,String msg,LayoutParams params,ToastCallBack callBack)
	{
		
	}

}
