package com.cj.fragment;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.cj.JokeBean.Root;
import com.cj.Utils.AsyncDataLoader;
import com.cj.Utils.MyApplication;
import com.cj.adapter.JokeAdapter;
import com.cj.adapter.JokeAdapter.Callback;
import com.cj.journeyhelper.MainActivity;
import com.cj.journeyhelper.R;
import com.cj.view.DragImageView;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.display.BitmapDisplayer;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;

public class JokeFragment extends Fragment implements Callback {
	private Context mcontext;
	private SwipeRefreshLayout swipeRefreshLayout;
	private ListView listView;
	private String result;// �õ���Json�ַ���
	private ImageLoader imageLoader;
	private DisplayImageOptions options;

	public JokeFragment(Context context) {
		this.mcontext = context;
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		View view = inflater.inflate(R.layout.jokefragment_layout, null);
		swipeRefreshLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.joke_swipeRefresh);
		listView = (ListView) view.findViewById(R.id.joke_listview);
		swipeRefreshLayout.setColorSchemeResources(
				android.R.color.holo_blue_light,
				android.R.color.holo_red_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_green_light);
		swipeRefreshLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO �Զ����ɵķ������
				new AsyncDataLoader(loader).execute();
				swipeRefreshLayout.setRefreshing(false);// ˢ�����
			}
		});
		BitmapDisplayer bitmapDisplayer = new FadeInBitmapDisplayer(500);
		new AsyncDataLoader(loader).execute();
		imageLoader = ImageLoader.getInstance();
		options = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.imageloading)
				.showImageOnFail(R.drawable.imageerror)
				.showImageOnLoading(R.drawable.imageloading).cacheOnDisk(true)
				.displayer(bitmapDisplayer)// ͼƬ�Ƿ�ΪԲ��
				.cacheInMemory(true).build();// �Ƿ�ʹ�û���
		return view;
	}

	private AsyncDataLoader.Callback loader = new AsyncDataLoader.Callback() {

		@Override
		public void onStart() {
			// TODO �Զ����ɵķ������
			try {
				String URl = MyApplication.Joke_WEB_URL;
				// ��ȡ��ǰʱ��
				Date now = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat(
						"yyyyMMddHHmmss");
				String showapi_timestamp = dateFormat.format(now);
				String address = URl + "?showapi_appid="
						+ MyApplication.Joke_showapi_appid
						+ "&showapi_timestamp=" + showapi_timestamp
						+ "&showapi_sign=" + MyApplication.Joke_showapi_sign;
				Log.v("TAG", address);
				HttpGet httpGet = new HttpGet(address);
				HttpResponse httpResponse = new DefaultHttpClient()
						.execute(httpGet);
				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(httpResponse.getEntity());
				} else {

				}
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
			if (result != null) {
				Log.d("caojing", result);
				try {
					Gson gson = new Gson();
					Root root = new Root();
					root = gson.fromJson(result, Root.class);
					String code = String.valueOf(root.getShowapi_res_code());
					if (code.equals("0"))// �����ɹ��������ݷ���
					{
						JokeAdapter adapter = new JokeAdapter(mcontext, root,
								imageLoader, options, JokeFragment.this);
						listView.setAdapter(adapter);
						adapter.notifyDataSetChanged();
					} else if (code.equals("-1")) {// ����ʧ�ܣ�ʧ��ԭ��{
						Toast.makeText(mcontext, root.getShowapi_res_error(), 1)
								.show();
					} else {
					}

				} catch (Exception e) {
					// TODO: handle exception
					Toast.makeText(mcontext, "����JSON����", 1).show();
				}
			}
		}
	};
	private int window_width, window_height;// �ؼ����
	private DragImageView dragImageView;// �Զ���ؼ�
	private int state_height;// ״̬���ĸ߶�
	private ViewTreeObserver viewTreeObserver;
	@Override
	public void onclick(View v) {
		// TODO �Զ����ɵķ������
		Bitmap bitmap = (Bitmap) v.getTag();
		if(bitmap == null)
		{
			Toast.makeText(mcontext, "null", 1).show();
		}
		else {
			Toast.makeText(mcontext, "123", 1).show();
			Dialog dialog = new Dialog(mcontext,
					R.style.Dialog_Fullscreen);
			dialog.setContentView(R.layout.imageviewbig);
			dragImageView = (DragImageView) dialog
					.findViewById(R.id.div_main);
			// ����ͼƬ
			WindowManager manager = ((Activity) mcontext).getWindowManager();
			Point size = new Point();
			manager.getDefaultDisplay().getSize(size);
			window_width = size.x;
			window_height = size.y;
			dragImageView = (DragImageView) dialog.findViewById(R.id.div_main);
//			Bitmap bmp = BitmapUtil.ReadBitmapById(MainActivity.this, R.drawable.caojing,
//					window_width, window_height);
			// ����ͼƬ
			dragImageView.setImageBitmap(bitmap);
			dragImageView.setmActivity((Activity) mcontext);// ע��Activity.
			/** ����״̬���߶� **/
			viewTreeObserver = dragImageView.getViewTreeObserver();
			viewTreeObserver
					.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

						@Override
						public void onGlobalLayout() {
							if (state_height == 0) {
								// ��ȡ״�����߶�
								Rect frame = new Rect();
								((Activity) mcontext).getWindow()
										.getDecorView()
										.getWindowVisibleDisplayFrame(frame);
								state_height = frame.top;
								dragImageView.setScreen_H(window_height
										- state_height);
								dragImageView.setScreen_W(window_width);
							}

						}
					});
			dialog.show();
		}
//		Dialog dialog  = new Dialog(mcontext, theme)
//		if (v.getTag().equals(R.id.joke_surfaceview_imageview)) {
//			Toast.makeText(mcontext, "123", 1).show();
//		}
	


	}
}
