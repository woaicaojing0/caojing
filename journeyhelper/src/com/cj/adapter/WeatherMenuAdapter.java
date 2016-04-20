package com.cj.adapter;

import java.util.List;

import com.cj.journeyhelper.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeatherMenuAdapter extends BaseAdapter {
	private List<String> list;
	private Context context;

	public WeatherMenuAdapter(List<String> list, Context context) {
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.menu_city_list_item, null);
			holder.textView = (TextView) convertView
					.findViewById(R.id.menu_city_list_item_txt);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView.setText(list.get(position).toString());
		return convertView;
	}

	class ViewHolder {
		protected TextView textView;
	}

}
