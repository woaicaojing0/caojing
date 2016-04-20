package com.cj.adapter;

import com.baidu.location.a.e;
import com.cj.journeyhelper.R;
import com.cj.knowbean.Knowbean;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class KnowAdapter extends  BaseAdapter {

	private Knowbean mknowbean;
	private Context mcontext;
	private ImageLoader mimageLoader;
	private DisplayImageOptions moptions;

	public KnowAdapter(Context context, Knowbean knowbean,
			ImageLoader imageLoader, DisplayImageOptions options) {
		this.mcontext = context;
		this.mknowbean = knowbean;
		this.mimageLoader = imageLoader;
		this.moptions = options;
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return mknowbean.getStories().size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return mknowbean.getStories().get(position).getId();
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
			convertView = LayoutInflater.from(mcontext).inflate(
					R.layout.knowactivity_list_item, null);
			holder.imageView_left = (ImageView) convertView
					.findViewById(R.id.imagevie_left);
			holder.textView_title = (TextView) convertView
					.findViewById(R.id.textview_title);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.textView_title.setText(mknowbean.getStories().get(position)
				.getTitle());
		Log.v("TAGANDURL", mknowbean.getStories().get(position).getImages()
				.get(0).toString());
		mimageLoader.displayImage(mknowbean.getStories().get(position)
				.getImages().get(0).toString(), holder.imageView_left,moptions);

		return convertView;
	}

	class ViewHolder {
		private TextView textView_title;
		private ImageView imageView_left;
	}
}
