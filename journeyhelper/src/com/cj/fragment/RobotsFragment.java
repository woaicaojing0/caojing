package com.cj.fragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.cj.Utils.CommonUtils;
import com.cj.Utils.HttpUtils;
import com.cj.adapter.ChatMessageAdapter;
import com.cj.journeyhelper.R;
import com.cj.robotbean.ReturnMsg;
import com.cj.robotbean.ReturnMsg.Type;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class RobotsFragment extends Fragment {
	private Context mcontext;
	private ListView list_mMsgs;
	private EditText mInputMsg;
	private Button btn_mSendMsg;
	private View view;
	private List<ReturnMsg> list_msg = new ArrayList<ReturnMsg>();
	private String toMsg;
	private ChatMessageAdapter adapter;

	public RobotsFragment(Context context) {
		this.mcontext = context;
	}

	private Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			ReturnMsg msg2 = (ReturnMsg) msg.obj;
			list_msg.add(msg2);
			adapter.notifyDataSetChanged();
			list_mMsgs.setSelection(list_msg.size() - 1);
		};
	};

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		view = inflater.inflate(R.layout.robotsfragment_layout, null);
		initView();
		btn_mSendMsg.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				if (CommonUtils.isNetworkAvailable((Activity) mcontext)) {
					toMsg = mInputMsg.getText().toString();
					if (TextUtils.isEmpty(toMsg)) {
						Toast.makeText(mcontext, "发送消息不能为空！",
								Toast.LENGTH_SHORT).show();
						return;
					}
					ReturnMsg msg = new ReturnMsg();
					msg.setDate(new Date());
					msg.setMsg(toMsg);
					msg.setType(Type.OUTCOMING);
					list_msg.add(msg);
					adapter = new ChatMessageAdapter(list_msg, mcontext);
					list_mMsgs.setAdapter(adapter);
					adapter.notifyDataSetChanged();
					list_mMsgs.setSelection(list_msg.size() - 1);
					new Thread() {
						public void run() {
							ReturnMsg returnMsg = HttpUtils.sendMssage(toMsg);
							Message message = new Message();
							message.obj = returnMsg;
							mHandler.sendMessage(message);
						};
					}.start();
					mInputMsg.setText("");
				}
				else{
					Toast.makeText(mcontext, "大兄弟,没网咯", 1);
				}
			}
		});
		return view;
	}

	private void initView() {
		list_mMsgs = (ListView) view.findViewById(R.id.id_listview_msg);
		mInputMsg = (EditText) view.findViewById(R.id.id_input_msg);
		btn_mSendMsg = (Button) view.findViewById(R.id.id_send_msg);
	}

}
