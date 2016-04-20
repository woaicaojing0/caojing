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
				.displayer(new CircleBitmapDisplayer())// 图片是否为圆角
				.cacheInMemory(true).build();// 是否使用缓存
	}

	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return mroot.getShowapi_res_body().getPagebean().getContentlist()
				.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return mroot.getShowapi_res_body().getPagebean().getContentlist()
				.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
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
				hoder.joke_image_issuer, touxiang);// 作者头像
		hoder.joke_txt_issuer.setText(contentlist.get(position).getName());// 作者名称
		hoder.joke_txt_time.setText(contentlist.get(position).getCreate_time());// 发布时间
		String ssString = contentlist.get(position).getText().toString();
		// 这边需要注意的是解析出来的题目是带有换行和空格符的，这里需要将换行符和空格去掉
		String string = ssString.replace(" ", "").replaceAll("\r|\n", "");
		hoder.joke_txt_title.setText(string);// 题目
		switch (type) {
		case "10":// 图片+gif
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
				// 最近碰到一个需求，要求是在不知道图片宽度和高度的情况下，让图片在指定宽度内充满，同时高度自适应,
				int screenWidth = ScreenUtils.getScreenWidth(mcontext);
				ViewGroup.LayoutParams lp = hoder.joke_surfaceview_imageview
						.getLayoutParams();
				lp.width = screenWidth;
				lp.height = LayoutParams.WRAP_CONTENT;
				hoder.joke_surfaceview_imageview.setLayoutParams(lp);

				hoder.joke_surfaceview_imageview.setMaxWidth(screenWidth);
				hoder.joke_surfaceview_imageview.setMaxHeight(screenWidth * 5); // 这里其实可以根据需求而定，我这里测试为最大宽度的5倍
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
								// TODO 自动生成的方法存根
								v.setTag(bitmap);// 将图片的bitmap通过回调函数传给fragment显示
								mcallback.onclick(v);
							}
						});

			}
			break;
		case "29":// 段子
			Gone(hoder.joke_surfaceview_txt, hoder.joke_surfaceview_imageview,
					hoder.frame_media, hoder.joke_surfaceview_webview);
			break;
		case "31":// 声音
			Gone(hoder.joke_surfaceview_txt, hoder.joke_surfaceview_imageview,
					hoder.frame_media, hoder.joke_surfaceview_webview);
			hoder.joke_surfaceview_webview.setVisibility(View.VISIBLE);
			String sss = contentlist.get(position).getVoiceuri().toString()
					.replace(" ", "").replaceAll("\r|\n", "");
			hoder.joke_surfaceview_webview.loadUrl(sss);
			break;
		case "41":// 视频
			Gone(hoder.joke_surfaceview_txt, hoder.joke_surfaceview_imageview,
					hoder.frame_media, hoder.joke_surfaceview_webview);
			hoder.frame_media.setVisibility(View.VISIBLE);
			String ssss = contentlist.get(position).getVideo_uri().toString()
					.replace(" ", "").replaceAll("\r|\n", "");
			Log.d("caojing", ssss);
			// VideoView与MediaController进行关联
			Uri uri = Uri.parse(ssss);
			hoder.joke_surfaceview_media.setVideoURI(uri);
			// hoder.joke_surfaceview_media.setVideoPath(file.getAbsolutePath());
			// 让VideiView获取焦点
			hoder.joke_surfaceview_media.requestFocus();
			final VideoView sVideoView = hoder.joke_surfaceview_media;
			hoder.frame_media.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
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
							// TODO 自动生成的方法存根
							frame_placeholder.setVisibility(View.GONE);
						}
					});
			frame_placeholder.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 自动生成的方法存根
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
		// 发布者的姓名、发布时间、发布标题、当type为段子的时候
		public TextView joke_txt_issuer, joke_txt_time, joke_txt_title,
				joke_surfaceview_txt;
		public ImageView joke_image_issuer, joke_surfaceview_imageview;// 发布者的头像,当type为图片时
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
		// TODO 自动生成的方法存根
		mcallback.onclick(v);
	}

	public byte[] Bitmap2Bytes(Bitmap bm) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
		return baos.toByteArray();
	}
}
