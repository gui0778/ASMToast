package com.slimo.smatoast.demo;

import com.slimo.pahpmqttdemo.R;
import com.slimo.smatoast.SMAToastUtil;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	private Button mShowBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mShowBtn=(Button)findViewById(R.id.activity_main_show_toast);
		mShowBtn.setOnClickListener(this);
		//ToastCtrler.show(this, "你好呀");
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		SMAToastUtil.show(this, "Hello world smatoast");
	}
}
