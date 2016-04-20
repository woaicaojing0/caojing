package com.cj.fragment;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.Toast;

import com.cj.Utils.AsyncDataLoader;
import com.cj.adapter.KnowAdapter;
import com.cj.journeyhelper.KnowDetailActivity;
import com.cj.journeyhelper.R;
import com.cj.knowbean.Knowbean;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class NewsFragment extends Fragment implements
		OnRefreshListener<ExpandableListView> {
	private Context mcontext;
	private PullToRefreshListView listView;
	private String result = null;
	private KnowAdapter adapter;
	private ImageLoader imageLoader;
	private DisplayImageOptions options;
	private Knowbean knowbean;
	private boolean IsfristRefresh = false;

	public NewsFragment(Context context) {
		this.mcontext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		View view = inflater.inflate(R.layout.newsfragment_layout, null);
		listView = (PullToRefreshListView) view
				.findViewById(R.id.knowactivity_listview);
		listView.setOnRefreshListener(new OnRefreshListener<ListView>() {

			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				// TODO �Զ����ɵķ������
				String label = DateUtils.formatDateTime(mcontext,
						System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME
								| DateUtils.FORMAT_SHOW_DATE
								| DateUtils.FORMAT_ABBREV_ALL);

				// Update the LastUpdatedLabel
				refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
				IsfristRefresh = true;
				// Do work to refresh the list here.
				new AsyncDataLoader(loader).execute();
				
			}
		});
		new AsyncDataLoader(loader).execute();
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.imageloading)
				.showImageOnFail(R.drawable.imageerror)
				.showImageOnLoading(R.drawable.imageloading).cacheOnDisk(true)
				.displayer(new RoundedBitmapDisplayer(5))// ͼƬ�Ƿ�ΪԲ��
				.cacheInMemory(true).build();// �Ƿ�ʹ�û���
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO �Զ����ɵķ������
				String titleid = String.valueOf(knowbean.getStories()
						.get(position-1).getId());
				Intent intent = new Intent(mcontext, KnowDetailActivity.class);
				intent.putExtra("id", titleid);
				startActivity(intent);
			}
		});
		return view;
	}

	private AsyncDataLoader.Callback loader = new AsyncDataLoader.Callback() {

		@Override
		public void onStart() {
			// TODO �Զ����ɵķ������
			// BufferedReader reader = null;
			// StringBuffer sbf = new StringBuffer();
			try {
				HttpGet httpGet = new HttpGet(
						"http://news-at.zhihu.com/api/4/news/latest");
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(httpResponse.getEntity());
				} else {

				}
				// URL url = new
				// URL("http://news-at.zhihu.com/api/4/news/latest");
				// HttpURLConnection connection = (HttpURLConnection) url
				// .openConnection();
				// connection.setRequestMethod("GET");
				// connection.connect();
				// InputStream is = connection.getInputStream();
				// reader = new BufferedReader(new InputStreamReader(is,
				// "UTF-8"));
				// String strRead = null;
				// while ((strRead = reader.readLine()) != null) {
				// sbf.append(strRead);
				// sbf.append("\r\n");
				// }
				// reader.close();
				// result = sbf.toString();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		@Override
		public void onPrepare() {
			// TODO �Զ����ɵķ������

		}

		@Override
		public void onFinish() {
			// TODO �Զ����ɵķ������
			// System.out.println(result);
			if (result != null) {
				if (IsfristRefresh) {
					adapter.notifyDataSetChanged();
				} else {

					// Toast.makeText(KnowActivity.this, result, 1).show();
					// result.replace("", "");
					// �����м�������д���
					String backlogJsonStrTmp = result.replace("\\", "");
					Log.v("TAG", result);
					// ������ɺ�ֵ��ȥ
					result = backlogJsonStrTmp;
					// Toast.makeText(mcontext, result, 1).show();
					Log.v("TAG2", result);
					knowbean = new Knowbean();
					Gson gson = new Gson();
					knowbean = gson.fromJson(result, Knowbean.class);
					adapter = new KnowAdapter(mcontext, knowbean, imageLoader,
							options);
					// adapter.notifyDataSetChanged();
					// ListView actualListView = listView.getRefreshableView();
					listView.setAdapter(adapter);
					// actualListView.setAdapter(adapter);
				}
				listView.onRefreshComplete();
			} else {
				Toast.makeText(mcontext, "��������״��", 1).show();
			}

		}
	};

	@Override
	public void onRefresh(PullToRefreshBase<ExpandableListView> refreshView) {
		// TODO �Զ����ɵķ������

	}

}
