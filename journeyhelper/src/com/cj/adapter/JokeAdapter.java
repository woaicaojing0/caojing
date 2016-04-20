package com.cj.adapter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.w3c.dom.Text;

import com.ant.liao.GifView;
import com.ant.liao.GifView.GifImageType;
import com.baidu.location.a.h;
import com.cj.JokeBean.Contentlist;
import com.cj.JokeBean.Root;
import com.cj.Utils.ScreenUtils;
import com.cj.journeyhelper.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.imageaware.ImageAware;

import android.R.fraction;
import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaMetadataRetriever;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.opengl.Visibility;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.PluginState;
import android.webkit.WebView;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

public class JokeAdapter extends BaseAdapter implements OnClickListener {

	private Context mcontext;
	private Root mroot;
	private ImageLoader mimageLoader;
	private DisplayImageOptions moptions, touxiang;
	private ArrayList<Contentlist> contentlist;
	private Callback mcallback;

	public interface Callback {
		public void onclick(View v);
	}

	public JokeAdapter(Context context, Root root, ImageLoader imageLoader,
			DisplayImageOptions options, Callback callback) {
		this.mcontext = context;
		this.mroot = root;
		this.mimageLoader = imageLoader;
		this.moptions = options;
		this.mcallback = callback;
		contentlist = new ArrayList<>();
		contentlist = (ArrayList<Contentlist>) mroot.getShowapi_res_body()
				.getPagebean().getContentlist();
		touxiang = new DisplayImageOptions.Builder()
				.showImageForEmptyUri(R.drawable.imageloading)
				.showImageOnFail(R.drawable.imageerror)
				.showImageOnLoading(R.drawable.imageloading).cacheOnDisk(true)
				.displayer(new CircleBitmapDisplayer())// ͼƬ�Ƿ�ΪԲ��
				.cacheInMemory(true).build();// �Ƿ�ʹ�û���
	}

	@Override
	public int getCount() {
		// TODO �Զ����ɵķ������
		return mroot.getShowapi_res_body().getPagebean().getContentlist()
				.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO �Զ����ɵķ������
		return mroot.getShowapi_res_body().getPagebean().getContentlist()
				.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO �Զ����ɵķ������
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO �Զ����ɵķ������
		ViewHolder hoder = null;
		if (convertView == null) {
			hoder = new ViewHolder();
			convertView = LayoutInflater.from(mcontext).inflate(
					R.layout.joke_listview_item, null);
			hoder.joke_image_issuer = (ImageView) convertView
					.findViewById(R.id.joke_image_issuer);
			hoder.joke_txt_issuer = (TextView) convertView
					.findViewById(R.id.joke_txt_issuer);
			hoder.joke_txt_time = (TextView) convertView
					.findViewById(R.id.joke_txt_time);
			hoder.joke_txt_title = (TextView) convertView
					.findViewById(R.id.joke_txt_title);
			hoder.joke_surfaceview_media = (VideoView) convertView
					.findViewById(R.id.joke_surfaceview_media);
			hoder.joke_surfaceview_txt = (TextView) convertView
					.findViewById(R.id.joke_surfaceview_text);
			hoder.joke_surfaceview_imageview = (ImageView) convertView
					.findViewById(R.id.joke_surfaceview_imageview);
			hoder.joke_surfaceview_webview = (WebView) convertView
					.findViewById(R.id.joke_sufaceview_webview);
			hoder.frame_media = (FrameLayout) convertView
					.findViewById(R.id.frame_media);
			hoder.frame_placeholder = (FrameLayout) convertView
					.findViewById(R.id.frame_placeholder);
			convertView.setTag(hoder);
		} else {
			hoder = (ViewHolder) convertView.getTag();
		}
		String type = contentlist.get(position).getType();
		mimageLoader.displayImage(contentlist.get(position).getProfile_image(),
				hoder.joke_image_issuer, touxiang);// ����ͷ��
		hoder.joke_txt_issuer.setText(contentlist.get(position).getName());// ��������
		hoder.joke_txt_time.setText(contentlist.get(position).getCreate_time());// ����ʱ��
		String ssString = contentlist.get(position).getText().toString();
		// �����Ҫע����ǽ�����������Ŀ�Ǵ��л��кͿո���ģ�������Ҫ�����з��Ϳո�ȥ��
		String string = ssString.replace(" ", "").replaceAll("\r|\n", "");
		hoder.joke_txt_title.setText(string);// ��Ŀ
		switch (type) {
		case "10":// ͼƬ+gif
			Gone(hoder.joke_surfaceview_txt, hoder.joke_surfaceview_imageview,
					hoder.frame_media, hoder.joke_surfaceview_webview);
			// hoder.joke_surfaceview_imageview.setMaxHeight(Integer
			// .valueOf(contentlist.get(position).getHeight()));
			// hoder.joke_surfaceview_imageview.setMaxWidth(Integer
			// .valueOf(contentlist.get(position).getWidth()));
			final String ss = contentlist.get(position).getImage0().toString()
					.replace(" ", "").replaceAll("\r|\n", "");
			String giforimg = ss.substring(ss.length() - 3, ss.length());
			if (giforimg.equals("gif")) {
				hoder.joke_surfaceview_webview.setVisibility(View.VISIBLE);
				hoder.joke_surfaceview_webview.loadUrl(ss);
				WebSettings settings = hoder.joke_surfaceview_webview
						.getSettings();
				settings.setUseWideViewPort(false);
				Log.d("caojing", ss);

			} else {
				hoder.joke_surfaceview_imageview.setVisibility(View.VISIBLE);
				ImageSize imageSize = new ImageSize(Integer.valueOf(contentlist
						.get(position).getWidth()), Integer.valueOf(contentlist
						.get(position).getHeight()));
				// �������һ������Ҫ�����ڲ�֪��ͼƬ��Ⱥ͸߶ȵ�����£���ͼƬ��ָ������ڳ�����ͬʱ�߶�����Ӧ,
				int screenWidth = ScreenUtils.getScreenWidth(mcontext);
				ViewGroup.LayoutParams lp = hoder.joke_surfaceview_imageview
						.getLayoutParams();
				lp.width = screenWidth;
				lp.height = LayoutParams.WRAP_CONTENT;
				hoder.joke_surfaceview_imageview.setLayoutParams(lp);

				hoder.joke_surfaceview_imageview.setMaxWidth(screenWidth);
				hoder.joke_surfaceview_imageview.setMaxHeight(screenWidth * 5); // ������ʵ���Ը���������������������Ϊ����ȵ�5��
				// over

				mimageLoader.displayImage(ss, hoder.joke_surfaceview_imageview,
						moptions);
				final Bitmap bitmap = ImageLoader.getInstance().loadImageSync(
						ss);
				BitmapDrawable drawable = (BitmapDrawable) hoder.joke_surfaceview_imageview
						.getDrawable();
				hoder.joke_surfaceview_imageview
						.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO �Զ����ɵķ������
								v.setTag(bitmap);// ��ͼƬ��bitmapͨ���ص���������fragment��ʾ
								mcallback.onclick(v);
							}
						});

			}
			break;
		case "29":// ����
			Gone(hoder.joke_surfaceview_txt, hoder.joke_surfaceview_imageview,
					hoder.frame_media, hoder.joke_surfaceview_webview);
			break;
		case "31":// ����
			Gone(hoder.joke_surfaceview_txt, hoder.joke_surfaceview_imageview,
					hoder.frame_media, hoder.joke_surfaceview_webview);
			hoder.joke_surfaceview_webview.setVisibility(View.VISIBLE);
			String sss = contentlist.get(position).getVoiceuri().toString()
					.replace(" ", "").replaceAll("\r|\n", "");
			hoder.joke_surfaceview_webview.loadUrl(sss);
			break;
		case "41":// ��Ƶ
			Gone(hoder.joke_surfaceview_txt, hoder.joke_surfaceview_imageview,
					hoder.frame_media, hoder.joke_surfaceview_webview);
			hoder.frame_media.setVisibility(View.VISIBLE);
			String ssss = contentlist.get(position).getVideo_uri().toString()
					.replace(" ", "").replaceAll("\r|\n", "");
			Log.d("caojing", ssss);
			// VideoView��MediaController���й���
			Uri uri = Uri.parse(ssss);
			hoder.joke_surfaceview_media.setVideoURI(uri);
			// hoder.joke_surfaceview_media.setVideoPath(file.getAbsolutePath());
			// ��VideiView��ȡ����
			hoder.joke_surfaceview_media.requestFocus();
			final VideoView sVideoView = hoder.joke_surfaceview_media;
			hoder.frame_media.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO �Զ����ɵķ������
					if (sVideoView.isPlaying()) {
						sVideoView.pause();
					} else {
						sVideoView.start();
					}
				}
			});
			final FrameLayout frame_placeholder = hoder.frame_placeholder;
			hoder.joke_surfaceview_media
					.setOnPreparedListener(new OnPreparedListener() {

						@Override
						public void onPrepared(MediaPlayer mp) {
							// TODO �Զ����ɵķ������
							frame_placeholder.setVisibility(View.GONE);
						}
					});
			frame_placeholder.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO �Զ����ɵķ������
					frame_placeholder.setVisibility(View.GONE);
					if (sVideoView.isPlaying()) {
						sVideoView.pause();
					} else {
						sVideoView.start();
					}
				}
			});
			break;

		default:
			break;
		}
		return convertView;
	}

	private class ViewHolder {
		// �����ߵ�����������ʱ�䡢�������⡢��typeΪ���ӵ�ʱ��
		public TextView joke_txt_issuer, joke_txt_time, joke_txt_title,
				joke_surfaceview_txt;
		public ImageView joke_image_issuer, joke_surfaceview_imageview;// �����ߵ�ͷ��,��typeΪͼƬʱ
		public VideoView joke_surfaceview_media;
		public WebView joke_surfaceview_webview;
		public FrameLayout frame_media, frame_placeholder;
	}

	private void Gone(TextView joke_surfaceview_txt,
			ImageView joke_surfaceview_imageview, FrameLayout frame_media,
			WebView joke_surfaceview_webview) {
		joke_surfaceview_txt.setVisibility(View.GONE);
		joke_surfaceview_imageview.setVisibility(View.GONE);
		frame_media.setVisibility(View.GONE);
		joke_surfaceview_webview.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
		// TODO �Զ����ɵķ������
		mcallback.onclick(v);
	}

	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}
