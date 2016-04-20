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
		// TODO 自动生成的方法存根
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
				// 线程池加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				// 降低线程的优先级保证主UI线程不受太大影响
				.denyCacheImageMultipleSizesInMemory()
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())
				.memoryCache(new UsingFreqLimitedMemoryCache(5 + 1024 * 1024))
				// 建议内存设在5-10M,可以有比较好的表现
				.memoryCacheSize(5 * 1024 * 1024)
				.diskCacheSize(50 * 1024 * 1024)
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.diskCacheFileCount(100)
				// 缓存的文件数量
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
