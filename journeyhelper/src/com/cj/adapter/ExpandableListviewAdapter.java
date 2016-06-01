package com.cj.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView.FindListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.cj.Utils.WeatherImage;
import com.cj.Utils.CommonUtils;
import com.cj.journeyhelper.R;
import com.cj.weathbean.WeatherBean;

public class ExpandableListviewAdapter extends BaseExpandableListAdapter {

	private WeatherBean bean;
	private Context context;

	public ExpandableListviewAdapter(WeatherBean bean, Context context) {
		this.bean = bean;
		this.context = context;
	}

	@Override
	public int getGroupCount() {
		// TODO 自动生成的方法存根
		return bean.getDaily_forecast().size() - 2;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO 自动生成的方法存根
		return 1;
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO 自动生成的方法存根
		return bean.getDaily_forecast().get(groupPosition).getDate();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return bean.getDaily_forecast().get(groupPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO 自动生成的方法存根
		return groupPosition;
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		// TODO 自动生成的方法存根
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		if (groupPosition == 0) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.weather_layout_top_item, null);
			LinearLayout layout = (LinearLayout) convertView
					.findViewById(R.id.weather_top_Line);
			// 获取除标题外的高度
			Rect outRect = new Rect();
			Activity activity = (Activity) context;
			activity.getWindow().getDecorView()
					.getWindowVisibleDisplayFrame(outRect);
			int statusBarHeight = outRect.top;// 获取标题栏的高度
			// activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT).getDrawingRect(outRect);
			int ss = outRect.height();
			LayoutParams layoutParams = new LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					((ss) / 7) * 2);// 导入上面所述的包
			layout.setBackgroundResource(R.drawable.weather_cicle_today);
			layout.setLayoutParams(layoutParams);
			TextView txt_weather_1tmp = (TextView) convertView
					.findViewById(R.id.txt_weather_1tmp);
			TextView txt_weather_city = (TextView) convertView
					.findViewById(R.id.txt_weather_city);
			TextView txt_weather_1today = (TextView) convertView
					.findViewById(R.id.txt_weather_1today);
			ImageView img_weather_1 = (ImageView) convertView
					.findViewById(R.id.img_weather_top1);
			TextView txt_updattimeTextView = (TextView) convertView
					.findViewById(R.id.text_updatetime);

			try {
				txt_weather_1today.setText(CommonUtils.dayForWeek(bean
						.getDaily_forecast().get(0).getDate()));
				txt_weather_1tmp.setText(bean.getDaily_forecast()
						.get(groupPosition).getTmp().getMin()
						+ "℃ ～ "
						+ bean.getDaily_forecast().get(groupPosition).getTmp()
								.getMax() + "℃");
				String timeString = bean.getBasic().getUpdate().getLoc();
				txt_updattimeTextView.setText("更新于:"+timeString.substring(10, timeString.length()));
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
			txt_weather_city.setText(bean.getBasic().getCity().toString());
			switchweather(img_weather_1, 0);
			// img_weather_1.setImageResource(R.drawable.sun);
		} else {
			convertView = null;
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(
						R.layout.weather_layout_next_item, null);
				RelativeLayout layout = (RelativeLayout) convertView
						.findViewById(R.id.weather_next_Line);
				// 获取除标题外的高度
				Rect outRect = new Rect();
				Activity activity = (Activity) context;
				activity.getWindow().getDecorView()
						.getWindowVisibleDisplayFrame(outRect);
				int ss = outRect.height();
				LayoutParams layoutParams = new LayoutParams(
						android.view.ViewGroup.LayoutParams.MATCH_PARENT,
						(ss) / 7);// 导入上面所述的包
				layout.setBackgroundResource(R.drawable.weather_cicle_today1);
				layout.setLayoutParams(layoutParams);
				holder.imageView = (ImageView) convertView
						.findViewById(R.id.img_weather);
				holder.txt_weather_tmp = (TextView) convertView
						.findViewById(R.id.txt_weather_tmp);
				holder.txt_weather_today = (TextView) convertView
						.findViewById(R.id.txt_weather_today);
				holder.relativeLayout = layout;
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			switch (groupPosition) {
			case 2:
				holder.relativeLayout
						.setBackgroundResource(R.drawable.weather_cicle_today2);
				break;
			case 3:
				holder.relativeLayout
						.setBackgroundResource(R.drawable.weather_cicle_today3);
				break;
			case 4:
				holder.relativeLayout
						.setBackgroundResource(R.drawable.weather_cicle_today4);
				break;
			default:
				break;
			}
			switchweather(holder.imageView, groupPosition);
			// holder.imageView.setImageResource(R.drawable.snowy);
			holder.txt_weather_tmp.setText(bean.getDaily_forecast()
					.get(groupPosition).getTmp().getMin()
					+ "℃ ～ "
					+ bean.getDaily_forecast().get(groupPosition).getTmp()
							.getMax() + "℃");
			try {
				holder.txt_weather_today.setText(CommonUtils.dayForWeek(bean
						.getDaily_forecast().get(groupPosition).getDate()));
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				e.printStackTrace();
			}
		}
		return convertView;
	}

	public void switchweather(ImageView imageView, int groupPosition) {
		// 判断天气情况
		imageView.setImageResource(WeatherImage.WeatherImageByCond_d(Integer
				.valueOf(bean.getDaily_forecast().get(groupPosition).getCond()
						.getCode_d())));
	}

	class ViewHolder {
		public ImageView imageView;
		public TextView txt_weather_today, txt_weather_tmp;
		public RelativeLayout relativeLayout;
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ViewHolder2 holder2 = null;
		if (convertView == null) {
			holder2 = new ViewHolder2();
			convertView = LayoutInflater.from(context).inflate(
					R.layout.weather_layout_child_item, null);
			holder2.textView = (TextView) convertView
					.findViewById(R.id.txt_weather_layout_child);
			holder2.sunrise_time = (TextView) convertView
					.findViewById(R.id.sunrise_time);
			holder2.sunset_time = (TextView) convertView
					.findViewById(R.id.sunset_time);
			holder2.text_shuoming = (TextView) convertView
					.findViewById(R.id.text_shuoming);
			convertView.setTag(holder2);
		} else {
			holder2 = (ViewHolder2) convertView.getTag();
		}
		if (groupPosition == 0) {
			holder2.textView.setText(bean.getSuggestion().getComf().getTxt());
		} else {
			holder2.textView.setText("");
		}
		holder2.sunrise_time.setText(bean.getDaily_forecast()
				.get(groupPosition).getAstro().getSr());
		holder2.sunset_time.setText(bean.getDaily_forecast().get(groupPosition)
				.getAstro().getSs());
		String textshuomingString = null;
		String text_d = bean.getDaily_forecast().get(groupPosition).getCond()
				.getTxt_d();
		String text_n = bean.getDaily_forecast().get(groupPosition).getCond()
				.getTxt_n();
		String fengji = bean.getDaily_forecast().get(groupPosition).getWind()
				.getDir();
		String fengname = bean.getDaily_forecast().get(0).getWind().getSc();
		if (text_d.equals(text_n)) {
			textshuomingString = ("白天到夜里，天气："
					+ text_d
					+ "；最高温度"
					+ bean.getDaily_forecast().get(groupPosition).getTmp()
							.getMax()
					+ "℃，最低温度"
					+ bean.getDaily_forecast().get(groupPosition).getTmp()
							.getMin() + "℃；" + fengname + "   " + fengji);
		} else {
			textshuomingString = ("白天到夜里，天气："
					+ text_d
					+ "转"
					+ text_n
					+ "； 最高温度"
					+ bean.getDaily_forecast().get(groupPosition).getTmp()
							.getMax()
					+ "℃，最低温度"
					+ bean.getDaily_forecast().get(groupPosition).getTmp()
							.getMin() + "℃；" + fengname + "   " + fengji);
		}
		holder2.text_shuoming.setText(textshuomingString);
		return convertView;
	}

	class ViewHolder2 {
		TextView textView, sunrise_time, sunset_time, text_shuoming;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		// TODO 自动生成的方法存根
		return true;
	}

}
