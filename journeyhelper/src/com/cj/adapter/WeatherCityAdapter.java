package com.cj.adapter;

import java.util.List;
import java.util.zip.Inflater;

import com.cj.journeyhelper.R;
import com.cj.journeyhelper.WeatherActivity2;
import com.cj.journeyhelper.WeatherCityActivity;

import android.R.integer;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class WeatherCityAdapter extends BaseAdapter {

	private List<String> mlist;
	private Context mtContext;

	public WeatherCityAdapter(Context context, List<String> list) {
		this.mlist = list;
		this.mtContext = context;
	}

	@Override
	public int getCount() {
		return (mlist.size() / 3 + 1);
	}

	@Override
	public Object getItem(int position) {
		return mlist.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mtContext).inflate(
					R.layout.weather_city_item, null);
			holder.text1 = (TextView) convertView.findViewById(R.id.text1);
			holder.text2 = (TextView) convertView.findViewById(R.id.text2);
			holder.text3 = (TextView) convertView.findViewById(R.id.text3);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		int i = mlist.size() / 3;
		int yu = mlist.size() % 3;
		if (position < i) {
			holder.text1.setText(mlist.get((position * 3)));
			holder.text2.setText(mlist.get((position * 3) + 1));
			holder.text3.setText(mlist.get((position * 3) + 2));

			holder.text1.setTag(position);
			holder.text2.setTag(position);
			holder.text3.setTag(position);
		} else if (position == i) {
			switch (yu) {
			case 1:
				holder.text1.setText(mlist.get((position * 3)));

				holder.text1.setTag(position);
				break;
			case 2:
				holder.text1.setText(mlist.get((position * 3)));
				holder.text2.setText(mlist.get((position * 3) + 1));

				holder.text1.setTag(position);
				holder.text2.setTag(position);
				break;
			default:
				break;
			}
		} else {
		}
		final TextView textView1 = holder.text1;
		textView1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				int position = Integer.parseInt(textView1.getTag().toString());
				Intent intent = new Intent(mtContext, WeatherActivity2.class);
				intent.putExtra("location", mlist.get(position * 3)
						.toString());
				mtContext.startActivity(intent);
			}
		});

		final TextView textView2 = holder.text2;
		textView2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				int position = Integer.parseInt(textView2.getTag().toString());;
				Intent intent = new Intent(mtContext, WeatherActivity2.class);
				intent.putExtra("location", mlist.get(position * 3 + 1)
						.toString());
				mtContext.startActivity(intent);
			}
		});

		final TextView textView3 = holder.text3;
		textView3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				int position = Integer.parseInt(textView3.getTag().toString());
				Intent intent = new Intent(mtContext, WeatherActivity2.class);
				intent.putExtra("location", mlist.get(position * 3 + 2)
						.toString());
				mtContext.startActivity(intent);
			}
		});
		return convertView;
	}

	@Override
	public boolean areAllItemsEnabled() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public boolean isEnabled(int position) {
		// TODO 自动生成的方法存根
		return false;
	}

	class ViewHolder {
		public TextView text1, text2, text3;
	}
}
