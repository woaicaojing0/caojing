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
	public static String locationcity = "�Ͼ�";
	private BDLocationListener myListener = new MyLocationListener();
	private TextView textView;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO �Զ����ɵķ������
		super.onCreate(savedInstanceState);
		// bundle = savedInstanceState;
		// ��������ͼ����
		requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		initSlidingMenu(savedInstanceState);
		dialog = new ProgressDialog(WeatherActivity2.this);
		dialog.setTitle("��ʾ��Ϣ");
		dialog.setMessage("���ڶ�λ�����Ե�......");
		dialog.setCancelable(false);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		textView = (TextView) findViewById(R.id.location_address);// titlbar�еĳ�������
		ImageButton button = (ImageButton) findViewById(R.id.titlebar_imgbutton);// ��߳���ͼ��
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
				// TODO �Զ����ɵķ������
				Toast.makeText(WeatherActivity2.this, "124441", 1).show();
				toggle();
				animation.cancel();

			}
		});
		final ImageButton button_refresh = (ImageButton) findViewById(R.id.titlebar_imgbutton_refresh);// �ұ�ˢ��ͼ��
		button_refresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO �Զ����ɵķ������
				Toast.makeText(WeatherActivity2.this, "124441", 1).show();
				button_refresh.startAnimation(animation);
			}
		});
		// mMenu = (SlidingMenu) findViewById(R.id.id_menu);

		mLocationClient = new LocationClient(getApplicationContext()); // ����LocationClient��
		mLocationClient.registerLocationListener(myListener); // ע���������
		initLocation();
		mLocationClient.start();
		// mLocationClient.requestLocation();//
		// start֮���Ĭ�Ϸ���һ�ζ�λ���󣬿����������ж�isstart����������request
	}

	/**
	 * ��ʼ�������˵�
	 */
	private void initSlidingMenu(Bundle savedInstanceState) {
		// ��������״̬��Ϊ����õ�ColorFragment������ʵ����ColorFragment
		if (savedInstanceState != null)
			mContent = getSupportFragmentManager().getFragment(
					savedInstanceState, "mContent");
		if (mContent == null)
			setContentView(R.layout.content_frame);
		getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
				R.layout.titlebar);

		// ���û����˵���ͼ����
		setBehindContentView(R.layout.menu_frame);
		getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.menu_frame,
						new ColorMenuFragment(WeatherActivity2.this)).commit();

		// ���û����˵�������ֵ
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
			// TODO �Զ����ɵķ������
			StringBuffer sb = new StringBuffer(256);
			String city = null;
			islocation = true;
			city = location.getCity();
			if (city == null) {
				Toast.makeText(WeatherActivity2.this, "��λʧ��", 1).show();
			} else {
				locationcity = city.substring(0, city.indexOf("��"));
			}
			// ���¸���fragment�е�
			mContent = new com.cj.fragment.ColorFragment(locationcity,
					WeatherActivity2.this, textView);
			getSupportFragmentManager().beginTransaction()
					.replace(R.id.content_frame, mContent).commit();
			// dialog.dismiss();
		}
	}

	private void initLocation() {
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy);// ��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");// ��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span = 1000;
		option.setScanSpan(0);// ��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);// ��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);// ��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);// ��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);// ��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(true);// ��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(false);// ��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��
		option.SetIgnoreCacheException(false);// ��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);// ��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
		mLocationClient.setLocOption(option);
	}

}
