package com.slimo.smatoast;

import java.io.Serializable;

import android.R;
import android.annotation.SuppressLint;
import android.content.ContentProvider;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.drawable.GradientDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

@SuppressLint("RtlHardcoded")
public class ToastTask implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6587383096185755124L;
	private static final String TAG = null;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public long getAddtime() {
		return addtime;
	}
	public void setAddtime(long addtime) {
		this.addtime = addtime;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getDelay() {
		return delay;
	}
	public void setDelay(int delay) {
		this.delay = delay;
	}
	public int getRequestCode() {
		return requestCode;
	}
	public void setRequestCode(int requestCode) {
		this.requestCode = requestCode;
	}
	public LayoutParams getParams() {
		return params;
	}
	public void setParams(LayoutParams params) {
		this.params = params;
	}
	public long getShowtime() {
		return showtime;
	}
	public void setShowtime(long showtime) {
		this.showtime = showtime;
	}
	private Context context;
	private int status;//0 default 1 showed
	private long addtime=System.currentTimeMillis();
	private long showtime;
	private String message;
	private int type;
	private int delay=1;
	private int requestCode=0;
	private LayoutParams params;
	private TextView view;
	private FrameLayout rootView;
	public TextView getView() {
		return view;
	}
	public void setView(TextView view) {
		this.view = view;
	}
	public ToastTask(Context context)
	{
		this.context=context;
		initView();
	}
	@SuppressLint("NewApi")
	private void initView()
	{
		rootView=new FrameLayout(context);
		view=new TextView(context);
		int strokeWidth = 0; // 边框宽度
	    int roundRadius = 7; // 圆角半径
	    int strokeColor = Color.parseColor("#88000000");//边框颜色
	    int fillColor = Color.parseColor("#88000000");//内部填充颜色
	    int padding=15;
	    GradientDrawable gd = new GradientDrawable();//创建drawable
	    gd.setColor(fillColor);
	    gd.setCornerRadius(roundRadius);
	    gd.setStroke(strokeWidth, strokeColor);
	    view.setBackground(gd);
		//view.setBackgroundResource(com.slimo.pahpmqttdemo.R.drawable.corner);;
		view.setTextColor(Color.WHITE);
		view.setPadding(padding, padding, padding, padding);
		RelativeLayout.LayoutParams viewLayout=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
		viewLayout.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
		rootView.addView(view,viewLayout);
		initBottomParam();

	}
	private void initParam()
	{
		//init center
		params = new WindowManager.LayoutParams();  
        //获取的是WindowManagerImpl.CompatModeWrapper  
        Log.e(TAG, "params--->" + params);  
        //设置window type  
        params.type = LayoutParams.TYPE_PHONE;   
        //设置图片格式，效果为背景透明  
        params.format = PixelFormat.RGBA_8888;   
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）  
        params.flags = LayoutParams.FLAG_NOT_FOCUSABLE;        
        //调整悬浮窗显示的停靠位置为屏幕剧中  
        params.gravity = Gravity.CENTER;         
        // 以屏幕左上角为原点，设置x、y初始值，相对于gravity   
  
        //设置悬浮窗口长宽数据    
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;  
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
	}
	private void initBottomParam()
	{
		//init center
		params = new WindowManager.LayoutParams();  
        //获取的是WindowManagerImpl.CompatModeWrapper  
        //设置window type  
        params.type = LayoutParams.TYPE_PHONE;   
        //设置图片格式，效果为背景透明  
        params.format = PixelFormat.RGBA_8888;   
        //设置浮动窗口不可聚焦（实现操作除浮动窗口外的其他可见窗口的操作）  
        params.flags = LayoutParams.FLAG_NOT_FOCUSABLE;        
        //调整悬浮窗显示的停靠位置为屏幕剧中  
        params.gravity = Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL;
        //配合bottom，距离下方
        params.y=100; 
  
        //设置悬浮窗口长宽数据    
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;  
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
	}
	public View getDrawView()
	{
		view.setText(this.message);
		return rootView;
	}
	public View getContentView()
	{
		return view;
	}
}