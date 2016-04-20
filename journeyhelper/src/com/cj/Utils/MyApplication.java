package com.cj.Utils;

import java.io.File;

import com.baidu.apistore.sdk.ApiStoreSDK;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.download.ImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;

import android.app.Application;
import android.content.Context;
import android.text.StaticLayout;

public class MyApplication extends Application {
	public static String baidu_key = "4d63d29bfd8b6f6640e7d3164f453558";
	public static String Weather_APIKEY = "59ef8e748dbb4a5cb0f1af7c6f43f2ae";
	public static String Joke_showapi_appid = "12042";
	public static String Joke_showapi_sign = "55cea51f69b64e3cb3abee57b574b292";
	public static String Joke_WEB_URL="http://route.showapi.com/255-1";

	@Override
	public void onCreate() {
		// TODO �Զ����ɵķ������
		super.onCreate();
		ApiStoreSDK.init(this, baidu_key);
		InitImageLoader(getApplicationContext());
	}

	private void InitImageLoader(Context context) {
		File cacheDir = StorageUtils.getOwnCacheDirectory(context,
				"journyhelper/Cache");
		ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(
				context)
				.memoryCacheExtraOptions(400, 800)
				.threadPoolSize(3)
				// �̳߳ؼ��ص�����
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// �����̵߳����ȼ���֤��UI�̲߳���̫��Ӱ��
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.memoryCache(new UsingFreqLimitedMemoryCache(5 + 1024 * 1024))
				// �����ڴ�����5-10M,�����бȽϺõı���
				.memoryCacheSize(5 * 1024 * 1024)
				.diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCacheFileCount(100)
				// ������ļ�����
				.diskCache(new UnlimitedDiskCache(cacheDir))
				.imageDownloader(
						new BaseImageDownloader(context, 5 * 1000, 30 * 1000))// connectTimeout
																				// (5
																				// s),
																				// readTimeout
																				// (30
																				// s)
				.writeDebugLogs().build();
		ImageLoader.getInstance().init(configuration);
	}
}
