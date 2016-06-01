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
	private ExpandableListView expandableListView;//2��listview��ɵĿؼ�
	private static TextView textView;// titlebar����ʾ�ĳ������ƣ���һ�ν�ʱ����weatheractivity���룬�Ժ���Ҫ����textview��

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
		// TODO �Զ����ɵĹ��캯�����
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.weather_layout2, null);
		expandableListView = (ExpandableListView) view
				.findViewById(R.id.weather_expandablelistview);
		dialog = new ProgressDialog(context);
		dialog.setTitle("��ʾ��Ϣ");
		dialog.setMessage("���ڼ������������Ե�......");
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		textView.setText(address);
		WeatherAsynctask asynctask = new WeatherAsynctask(dialog, this, manimation);
		int currentapiVersion = android.os.Build.VERSION.SDK_INT;
		if (currentapiVersion >= 20) {// ��ʾ��׿�汾��5.0���ϵ�
			asynctask.execute(new String(address));
		} else {
			try {
				asynctask.execute(new String(address.getBytes("UTF-8"),
						"ISO8859-1"));
			} catch (UnsupportedEncodingException e) {
				// TODO �Զ����ɵ� catch ��
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

	// ������ȡ��json����
	@Override
	public void GetWeatherInfo(String info) {
		// TODO �Զ����ɵķ������
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
			if (bean.getStatus().toString().equals("ok")) {// ��ȡ������֮��Ҫ���������뵽��Ӧ��textview��ȥ
				ExpandableListviewAdapter adapter = new ExpandableListviewAdapter(
						bean, context);
				expandableListView.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} else {
				Toast.makeText(context, "��֧�ֵ�ǰϵͳ", 1).show();
			}
		} catch (JSONException e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		} catch (Exception e) {
			// TODO �Զ����ɵ� catch ��
			e.printStackTrace();
		}

	}

}
