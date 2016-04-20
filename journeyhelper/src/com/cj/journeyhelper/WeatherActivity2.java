package com.cj.journeyhelper;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.cj.fragment.ColorMenuFragment;
import com.cj.weathbean.WeatherBean;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class WeatherActivity2 extends SlidingFragmentActivity {
	private String TAG = "weather json>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>";
	private ProgressDialog dialog;
	private Fragment mContent;
	private LocationClient mLocationClient = null;
	private boolean islocation = false;
	public static String locationcity = "南京";
	private BDLocationListener myListener = new MyLocationListener();
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		// bundle = savedInstanceState;
		// 设置主视图界面
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		initSlidingMenu(savedInstanceState);
		dialog = new ProgressDialog(WeatherActivity2.this);
		dialog.setTitle("提示信息");
		dialog.setMessage("正在定位，请稍等......");
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		textView = (TextView) findViewById(R.id.location_address);// titlbar中的城市名称
		ImageButton button = (ImageButton) findViewById(R.id.titlebar_imgbutton);// 左边抽屉图标
		final Animation animation = new RotateAnimation(0f, 360f,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		animation.setRepeatCount(-1);
		animation.setDuration(800);
		AccelerateDecelerateInterpolator accelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
		animation.setInterpolator(accelerateDecelerateInterpolator);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(WeatherActivity2.this, "124441", 1).show();
				toggle();
				animation.cancel();

			}
		});
		final ImageButton button_refresh = (ImageButton) findViewById(R.id.titlebar_imgbutton_refresh);// 右边刷新图标
		button_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO 自动生成的方法存根
				Toast.makeText(WeatherActivity2.this, "124441", 1).show();
				button_refresh.startAnimation(animation);
			}
		});
		// mMenu = (SlidingMenu) findViewById(R.id.id_menu);

		mLocationClient = new LocationClient(getApplicationContext()); // 声明LocationClient类
		mLocationClient.registerLocationListener(myListener); // 注册监听函数
		initLocation();
		mLocationClient.start();
		// mLocationClient.requestLocation();//
		// start之后会默认发起一次定位请求，开发者无须判断isstart并主动调用request
	}

	/**
	 * 初始化滑动菜单
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// 如果保存的状态不为空则得到ColorFragment，否则实例化ColorFragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			setContentView(R.layout.content_frame);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar);

		// 设置滑动菜单视图界面
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.menu_frame,
						new ColorMenuFragment(WeatherActivity2.this)).commit();

		// 设置滑动菜单的属性值
		getSlidingMenu().setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		getSlidingMenu().setShadowWidthRes(R.dimen.shadow_width);
		getSlidingMenu().setShadowDrawable(R.drawable.shadow);
		getSlidingMenu().setBehindOffsetRes(R.dimen.slidingmenu_offset);
		getSlidingMenu().setFadeDegree(0.35f);

	}

	public void switchContent(Fragment fragment) {
		mContent = fragment;
		getSupportFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment).commit();
		getSlidingMenu().showContent();
	}

	class MyLocationListener implements BDLocationListener {

		@Override
		public void onReceiveLocation(BDLocation location) {
			// TODO 自动生成的方法存根
			StringBuffer sb = new StringBuffer(256);
			String city = null;
			islocation = true;
			city = location.getCity();
			if (city == null) {
				Toast.makeText(WeatherActivity2.this, "定位失败", 1).show();
			} else {
				locationcity = city.substring(0, city.indexOf("市"));
			}
			// 重新覆盖fragment中的
			mContent = new com.cj.fragment.ColorFragment(locationcity,
					WeatherActivity2.this, textView);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, mContent).commit();
			// dialog.dismiss();
		}
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// 可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");// 可选，默认gcj02，设置返回的定位结果坐标系
		int span = 1000;
		option.setScanSpan(0);// 可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);// 可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);// 可选，默认false,设置是否使用gps
		option.setLocationNotify(true);// 可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);// 可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);// 可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);// 可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);// 可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);// 可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

}
