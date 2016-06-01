package com.cj.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.cj.journeyhelper.R;
import com.cj.journeyhelper.WeatherActivity2;

public class IndexFragment extends Fragment implements OnClickListener {
	private Context mcontext;
	private Button btn_weather, btn_view, btn_transportation, btn_more;

	public IndexFragment(Context context) {
		this.mcontext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		View view = inflater.inflate(R.layout.indexfragment_layout, null);
		btn_weather = (Button) view.findViewById(R.id.btn_weather);
		btn_view = (Button) view.findViewById(R.id.btn_view);
		btn_transportation = (Button) view
				.findViewById(R.id.btn_transportation);
		btn_more = (Button) view.findViewById(R.id.btn_more);
		btn_weather.setOnClickListener(this);
		btn_view.setOnClickListener(this);
		btn_transportation.setOnClickListener(this);
		btn_more.setOnClickListener(this);
		return view;

	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.btn_weather:
			Intent intent = new Intent(mcontext,WeatherActivity2.class);
			startActivity(intent);
			break;
		case R.id.btn_view:

			break;
		case R.id.btn_transportation:

			break;
		case R.id.btn_more:

			break;
		default:
			break;
		}

	}
}
