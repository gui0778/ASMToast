package com.slimo.smatoast.test;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import android.app.Activity;
import android.media.ThumbnailUtils;

public class URLMain extends Activity {

	private void testURL() throws IOException {
		URL url=new URL("http://www.baidu.com");
		HttpURLConnection urlConnection=(HttpURLConnection)url.openConnection();
		OutputStream out=urlConnection.getOutputStream();
	}
}
