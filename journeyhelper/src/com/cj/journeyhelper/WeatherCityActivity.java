package com.cj.journeyhelper;

import java.util.ArrayList;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cj.Utils.AsyncDataLoader;
import com.cj.adapter.WeatherCityAdapter;
import com.cj.dao.WeatherCityDao;
import com.cj.fragment.ColorFragment;
import com.cj.fragment.ColorMenuFragment;

public class WeatherCityActivity extends Activity {

	private ListView listview_city_name;
	private static List<String> list;
	private ProgressDialog dialog;
	private AutoCompleteTextView completeTextView;
	private static RefreshCity mcity;

	public static void getcity(RefreshCity city) {
		mcity = city;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.weather_city);
		SharedPreferences preferences = getSharedPreferences("WeatherCity",
				MODE_PRIVATE);
		dialog = new ProgressDialog(this);
		dialog.setTitle("提示");
		dialog.setMessage("正在加载城市.....");
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		listview_city_name = (ListView) findViewById(R.id.weather_city_name);
		completeTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTxt);

		completeTextView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				System.out.println(completeTextView.getText().toString());
				Toast.makeText(WeatherCityActivity.this,
						completeTextView.getText(), 1).show();
				if (WeatherCityDao.GetSelectedCity(completeTextView.getText()
						.toString())) {
				} else {
					int i = WeatherCityDao
							.InsertSelectedCityName(completeTextView.getText()
									.toString());
					System.out.println(i + "");
				}
				Intent intent = new Intent(WeatherCityActivity.this,
						WeatherActivity2.class);
				intent.putExtra("location", completeTextView.getText()
						.toString());
				mcity.refresh();
				startActivity(intent);
			}
		});
		list = new ArrayList<String>();
		new AsyncDataLoader(loadlist).execute();
	}

	private AsyncDataLoader.Callback loadlist = new AsyncDataLoader.Callback() {

		@Override
		public void onStart() {
			WeatherCityDao dao = new WeatherCityDao(WeatherCityActivity.this);
			list = dao.getWeatherCityList();

		}

		@Override
		public void onPrepare() {
			dialog.show();
		}

		@Override
		public void onFinish() {
			dialog.dismiss();
			if (list.get(0).toString().equals("0")) {
				Toast.makeText(WeatherCityActivity.this, "网络异常", 1).show();
			} else {
				WeatherCityAdapter adapter = new WeatherCityAdapter(
						WeatherCityActivity.this, list);
				listview_city_name.setAdapter(adapter);
			}
			ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
					WeatherCityActivity.this,
					android.R.layout.simple_dropdown_item_1line, list);
			completeTextView.setAdapter(arrayAdapter);
		}
	};

	public interface RefreshCity {
		public void refresh();
	}
}
