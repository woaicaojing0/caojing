package com.cj.fragment;

import java.io.UnsupportedEncodingException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.cj.IInterFace.WeatherInterface;
import com.cj.adapter.ExpandableListviewAdapter;
import com.cj.asynctask.WeatherAsynctask;
import com.cj.journeyhelper.R;
import com.cj.journeyhelper.WeatherActivity2.RefreshWeather;
import com.cj.weathbean.WeatherBean;
import com.google.gson.Gson;

public class ColorFragment extends Fragment implements WeatherInterface {
  
	private Animation manimation;
	private int mColorRes = -1;
	private String address;
	private Context context;
	private WeatherBean bean;
	private ProgressDialog dialog;
	private ExpandableListView expandableListView;//2个listview组成的控件
	private static TextView textView;// titlebar中显示的城市名称，第一次进时，由weatheractivity传入，以后不需要传入textview。

	public ColorFragment(String address, Context context, TextView textView,Animation animation) {
		this.address = address;
		this.context = context;
		this.manimation = animation;
		if (textView == null) {
		} else {
			ColorFragment.textView = textView;
		}
	}

	public ColorFragment() {
		// TODO 自动生成的构造函数存根
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.weather_layout2, null);
		expandableListView = (ExpandableListView) view
				.findViewById(R.id.weather_expandablelistview);
		dialog = new ProgressDialog(context);
		dialog.setTitle("提示信息");
		dialog.setMessage("正在加载天气，请稍等......");
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		textView.setText(address);
		WeatherAsynctask asynctask = new WeatherAsynctask(dialog, this, manimation);
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 20) {// 表示安卓版本是5.0以上的
			asynctask.execute(new String(address));
		} else {
			try {
				asynctask.execute(new String(address.getBytes("UTF-8"),
						"ISO8859-1"));
			} catch (UnsupportedEncodingException e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return view;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mColorRes", mColorRes);
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
				ExpandableListviewAdapter adapter = new ExpandableListviewAdapter(
						bean, context);
				expandableListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(context, "不支持当前系统", 1).show();
			}
		} catch (JSONException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}

	}

}
