package com.cj.journeyhelper;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.cj.Utils.AsyncDataLoader;
import com.cj.knowbean.Knowdetailbean;
import com.google.gson.Gson;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebSettings.LayoutAlgorithm;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class KnowDetailActivity extends Activity {

	private String result = null;
	private WebView webview;
	private  TextView txt_detailName;
	private String id = null;
	private Knowdetailbean knowdetailbean;
	private ImageView imageView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.knowdetail_layout);
		webview = (WebView) findViewById(R.id.webview);
		txt_detailName = (TextView) findViewById(R.id.text_knoedetail);
//		imageView = findViewById(R.id.te);iamgeview_back
		imageView =   (ImageView) findViewById(R.id.iamgeview_back);
		imageView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				finish();
			}
		});
		//这个属性可以让webview只显示一列，也就是自适应页面大小 不能左右滑动
		webview.getSettings().setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		webview.getSettings().setJavaScriptEnabled(true);
		Intent intent = getIntent();
		id = intent.getStringExtra("id");
		if (id == null || id.length() == 0) {
		} else {
			new AsyncDataLoader(callback).execute();
		}
	}

	private AsyncDataLoader.Callback callback = new AsyncDataLoader.Callback() {

		@Override
		public void onStart() {
			// TODO 自动生成的方法存根
			BufferedReader reader = null;
			StringBuffer sbf = new StringBuffer();
			try {
				URL url = new URL("http://news-at.zhihu.com/api/4/news/"
						+ id);
				HttpURLConnection connection = (HttpURLConnection) url
						.openConnection();
				connection.setRequestMethod("GET");
				connection.connect();
				InputStream is = connection.getInputStream();
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				String strRead = null;
				while ((strRead = reader.readLine()) != null) {
					sbf.append(strRead);
					sbf.append("\r\n");
				}
				reader.close();
				result = sbf.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onPrepare() {
			// TODO 自动生成的方法存根

		}

		@Override
		public void onFinish() {
			// TODO 自动生成的方法存根
			if (result != null) {
				knowdetailbean = new Knowdetailbean();
				Gson gson = new Gson();
				knowdetailbean = gson.fromJson(result, Knowdetailbean.class);
				//引用固定的css样式。
			    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/css/news.css\" type=\"text/css\">";
                String html = "<html><head>" + css + "</head><body>" + knowdetailbean.getBody() + "</body></html>";
                html = html.replace("<div class=\"img-place-holder\">", "");
//                webview.loadUrl("file:///android_asset/news.css");
				webview.loadDataWithBaseURL("x-data://base", html, "text/html",
						"UTF-8", null);
				txt_detailName.setText(knowdetailbean.getTitle().toString());
			} else {

			}
		}
	};
}
