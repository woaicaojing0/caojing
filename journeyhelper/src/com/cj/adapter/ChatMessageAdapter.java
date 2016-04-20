package com.cj.adapter;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.zip.Inflater;

import com.cj.journeyhelper.R;
import com.cj.robotbean.ReturnMsg;
import com.cj.robotbean.ReturnMsg.Type;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ChatMessageAdapter extends BaseAdapter {

	private List<ReturnMsg> list_msg;
	private Context mcontext;

	public ChatMessageAdapter(List<ReturnMsg> list_msg, Context context) {
		this.list_msg = list_msg;
		this.mcontext = context;

	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return list_msg.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return list_msg.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public int getItemViewType(int position) {
		// TODO 自动生成的方法存根
		if (list_msg.get(position).getType() == Type.INCOMING) {
			return 0;
		}
		return 1;
	}

	@Override
	public int getViewTypeCount() {
		// TODO 自动生成的方法存根
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		ReturnMsg msg = list_msg.get(position);
		ViewHolder viewHolder = null;
		if (convertView == null) {
			if (getItemViewType(position) == 0) {
				convertView = LayoutInflater.from(mcontext).inflate(
						R.layout.item_reciver_msg, null);
				viewHolder = new ViewHolder();
				viewHolder.text_data = (TextView) convertView
						.findViewById(R.id.id_item_reciver_time);
				viewHolder.text_msg = (TextView) convertView
						.findViewById(R.id.id_item_recivermsg_info);
			} else {
				convertView = LayoutInflater.from(mcontext).inflate(
						R.layout.item_send_msg, null);
				viewHolder = new ViewHolder();
				viewHolder.text_data = (TextView) convertView
						.findViewById(R.id.id_item_send_time);
				viewHolder.text_msg = (TextView) convertView
						.findViewById(R.id.id_item_sendmsg_info);
			}
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		viewHolder.text_data.setText(df.format(msg.getDate()));
		viewHolder.text_msg.setText(msg.getMsg());
		return convertView;
	}

	public class ViewHolder {
		private TextView text_msg, text_data;
	}

}
