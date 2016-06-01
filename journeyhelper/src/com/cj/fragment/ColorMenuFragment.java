package com.cj.fragment;

import java.util.Arrays;
import java.util.List;

import com.cj.adapter.WeatherMenuAdapter;
import com.cj.dao.WeatherCityDao;
import com.cj.journeyhelper.R;
import com.cj.journeyhelper.WeatherActivity2;
import com.cj.journeyhelper.WeatherCityActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ColorMenuFragment extends Fragment implements com.cj.journeyhelper.WeatherCityActivity.RefreshCity {

	private ListView list_city;
	private TextView txt_edit;
	private ImageButton txt_add_city;
	private Context context;
	private List<String> list;
	private WeatherMenuAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left_menu, null);
		txt_edit = (TextView) view.findViewById(R.id.txt_menu_edit);
		txt_add_city = (ImageButton) view.findViewById(R.id.txt_add_city);
		list_city = (ListView) view.findViewById(R.id.list_menu_city);
		txt_edit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(context, "由于开发者懒，暂时还没开发这个功能", 1).show();
			}
		});
		txt_add_city.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				WeatherCityActivity.getcity(ColorMenuFragment.this);
				Intent intent = new Intent(context, WeatherCityActivity.class);


				startActivity(intent);
				// Toast.makeText(context, "由于开发者懒，暂时还没开发这个功能", 1).show();
			}
		});
		WeatherCityDao cityDao = new WeatherCityDao(context);
		list = cityDao.getSelectedCityName();
		// String[] citys;
		// citys =(String[]) city.toArray();
		// String[] citys = { "南京", "苏州", "无锡", "上海", "泰州", "徐州", "哈尔滨" };
		// final List<String> list = Arrays.asList(citys);
		adapter = new WeatherMenuAdapter(list, context);
		list_city.setAdapter(adapter);

		list_city.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO 自动生成的方法存根
				Fragment newContent = null;
				newContent = new ColorFragment(list.get(position), context,
						null, null);
				if (newContent != null) {
					switchFragment(newContent);
				}
			}
		});
		return view;
	}

	public ColorMenuFragment(Context context) {
		this.context = context;
	}

	// 切换Fragment视图内ring
	public void switchFragment(Fragment fragment) {
		if (getActivity() == null)
			return;

		if (getActivity() instanceof WeatherActivity2) {
			WeatherActivity2 fca = (WeatherActivity2) getActivity();
			fca.switchContent(fragment);
		}
	}

	@Override
	public void refresh() {
		WeatherCityDao cityDao = new WeatherCityDao(context);
		list = cityDao.getSelectedCityName();
		adapter = new WeatherMenuAdapter(list, context);
		list_city.setAdapter(adapter);
		adapter.notifyDataSetChanged();

	}

}
