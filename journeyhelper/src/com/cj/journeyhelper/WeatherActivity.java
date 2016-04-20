package com.cj.journeyhelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cj.IInterFace.WeatherInterface;
import com.cj.Utils.CommonUtils;
import com.cj.asynctask.WeatherAsynctask;
import com.cj.weathbean.WeatherBean;
import com.cj.weathbean.listweatherbean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherActivity extends Activity implements WeatherInterface {
	private String TAG = "weather json>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
	private ProgressDialog dialog;
	private WeatherBean bean;
	private TextView txt_weather_1tmp, txt_weather_2tmp, txt_weather_3tmp,
			txt_weather_4tmp, txt_weather_5tmp, txt_weather_1today,
			txt_weather_2today, txt_weather_3today, txt_weather_4today,
			txt_weather_5today;
	private ImageView img_weather_1, img_weather_2, img_weather_3,
			img_weather_4, img_weather_5;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.weather_layout);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar);
		dialog = new ProgressDialog(this);
		dialog.setTitle("提示信息");
		dialog.setMessage("正在加载天气，请稍等......");
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		WeatherAsynctask asynctask = new WeatherAsynctask(dialog, this);
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 20) {// 表示安卓版本是5.0以上的
			asynctask.execute(new String("南京"));
		} else {
			try {
				asynctask.execute(new String("南京".getBytes("UTF-8"),
						"ISO8859-1"));
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		InitText();
	}

	// 解析获取的json数据
	@Override
	public void GetWeatherInfo(String info) {
		// TODO 自动生成的方法存根
		JSONObject jsonObject;
		try {
			jsonObject = new JSONObject(info);
			JSONArray array = jsonObject
					.getJSONArray("HeWeather data service 3.0");
			System.out.println(array.toString());
			String reuslsss = array.get(0).toString();
			Gson gson = new Gson();
			bean = gson.fromJson(array.get(0).toString(), WeatherBean.class);
			System.out.println(bean.toString());
			if (bean.getStatus().toString().equals("ok")) {// 获取到数据之后要将数据填入到相应的textview中去
				bind_txt_toady();
				bind_txt_tmp();
			} else {
				Toast.makeText(WeatherActivity.this, "不支持当前系统", 1).show();
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

	private void bind_txt_toady() {
		try {
			txt_weather_1today.setText(CommonUtils.dayForWeek(bean
					.getDaily_forecast().get(0).getDate()));
			txt_weather_2today.setText(CommonUtils.dayForWeek(bean
					.getDaily_forecast().get(1).getDate()));
			txt_weather_3today.setText(CommonUtils.dayForWeek(bean
					.getDaily_forecast().get(2).getDate()));
			txt_weather_4today.setText(CommonUtils.dayForWeek(bean
					.getDaily_forecast().get(3).getDate()));
			txt_weather_5today.setText(CommonUtils.dayForWeek(bean
					.getDaily_forecast().get(4).getDate()));
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	private void bind_txt_tmp() {
         txt_weather_1tmp.setText( bean.getDaily_forecast().get(0).getTmp().getMin()+"℃ ～ " +bean.getDaily_forecast().get(0).getTmp().getMax()+"℃");
         txt_weather_2tmp.setText( bean.getDaily_forecast().get(1).getTmp().getMin()+"℃ ～ " +bean.getDaily_forecast().get(1).getTmp().getMax()+"℃");
         txt_weather_3tmp.setText( bean.getDaily_forecast().get(2).getTmp().getMin()+"℃ ～ " +bean.getDaily_forecast().get(2).getTmp().getMax()+"℃");
         txt_weather_4tmp.setText( bean.getDaily_forecast().get(3).getTmp().getMin()+"℃ ～ " +bean.getDaily_forecast().get(3).getTmp().getMax()+"℃");
         txt_weather_5tmp.setText( bean.getDaily_forecast().get(4).getTmp().getMin()+"℃ ～ " +bean.getDaily_forecast().get(4).getTmp().getMax()+"℃");
	}

	private void InitText() {
		// 一周的天气情况
		txt_weather_1tmp = (TextView) findViewById(R.id.txt_weather_1tmp);
		txt_weather_2tmp = (TextView) findViewById(R.id.txt_weather_2tmp);
		txt_weather_3tmp = (TextView) findViewById(R.id.txt_weather_3tmp);
		txt_weather_4tmp = (TextView) findViewById(R.id.txt_weather_4tmp);
		txt_weather_5tmp = (TextView) findViewById(R.id.txt_weather_5tmp);
		// 一周的别称 周一周二周三。。。。
		txt_weather_1today = (TextView) findViewById(R.id.txt_weather_1today);
		txt_weather_2today = (TextView) findViewById(R.id.txt_weather_2today);
		txt_weather_3today = (TextView) findViewById(R.id.txt_weather_3today);
		txt_weather_4today = (TextView) findViewById(R.id.txt_weather_4today);
		txt_weather_5today = (TextView) findViewById(R.id.txt_weather_5today);
		// 每天都天气的图片
		img_weather_1 = (ImageView) findViewById(R.id.img_weather_1);
		img_weather_2 = (ImageView) findViewById(R.id.img_weather_2);
		img_weather_3 = (ImageView) findViewById(R.id.img_weather_3);
		img_weather_4 = (ImageView) findViewById(R.id.img_weather_4);
		img_weather_5 = (ImageView) findViewById(R.id.img_weather_5);
	}
}
