package com.cj.journeyhelper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckedTextView;

import com.cj.Utils.SystemBarTintManager;
import com.cj.fragment.IndexFragment;
import com.cj.fragment.JokeFragment;
import com.cj.fragment.NewsFragment;
import com.cj.fragment.RobotsFragment;

public class MainActivity extends ActionBarActivity implements OnClickListener {
	private IndexFragment indexFragment;
	private NewsFragment newsFragment;
	private JokeFragment jokeFragment;
	private RobotsFragment robotsFragment;
	private Fragment mNowFragment;
	private CheckedTextView cktxtview_index, cktxtview_news, cktxtview_joke,
			cktxtview_robots;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
		// 去除标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		// 创建状态栏的管理实例
		SystemBarTintManager tintManager = new SystemBarTintManager(this);
		// 激活状态栏设置
		tintManager.setStatusBarTintEnabled(true);
		// 激活导航栏设置
		tintManager.setNavigationBarTintEnabled(true);
		// 设置一个颜色给系统栏
		tintManager.setTintColor(Color.parseColor("#0082b5"));
		Init();
	}

	private void Init() {
		indexFragment = new IndexFragment(MainActivity.this);
		newsFragment = new NewsFragment(MainActivity.this);
		jokeFragment = new JokeFragment(MainActivity.this);
		robotsFragment = new RobotsFragment(MainActivity.this);
		mNowFragment = indexFragment;
		changeFragment(indexFragment);
		cktxtview_index = (CheckedTextView) findViewById(R.id.bottom_index);
		cktxtview_news = (CheckedTextView) findViewById(R.id.bottom_news);
		cktxtview_joke = (CheckedTextView) findViewById(R.id.bottom_joke);
		cktxtview_robots = (CheckedTextView) findViewById(R.id.bottom_robots);
		cktxtview_index.setChecked(true);
		cktxtview_index.setOnClickListener(this);
		cktxtview_news.setOnClickListener(this);
		cktxtview_joke.setOnClickListener(this);
		cktxtview_robots.setOnClickListener(this);
	}

	public void changeFragment(android.support.v4.app.Fragment fragment) {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.main_middle, fragment);
		transaction.commit();
	}

	/** 修改显示的内容 不会重新加载 **/
	public void switchContent(Fragment to) {
		if (mNowFragment != to) {
			FragmentTransaction transaction = getSupportFragmentManager()
					.beginTransaction();
			// transaction.replace(R.id.main_content, to).commit();
			if (!to.isAdded()) { // 先判断是否被add过
				transaction.hide(mNowFragment).add(R.id.main_middle, to)
						.commit(); // 隐藏当前的fragment，add下一个到Activity中
			} else {
				transaction.hide(mNowFragment).show(to).commit(); // 隐藏当前的fragment，显示下一个
			}
		}
		mNowFragment = to;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO 自动生成的方法存根
		switch (v.getId()) {
		case R.id.bottom_index:
			switchContent(indexFragment);
			Clean();
			cktxtview_index.setChecked(true);
			break;
		case R.id.bottom_news:
			switchContent(newsFragment);
			Clean();
			cktxtview_news.setChecked(true);
			break;
		case R.id.bottom_joke:
			switchContent(jokeFragment);
			Clean();
			cktxtview_joke.setChecked(true);
			break;
		case R.id.bottom_robots:
			switchContent(robotsFragment);
			Clean();
			cktxtview_robots.setChecked(true);
			break;
		default:
			break;
		}
	}

	public void Clean() {
		cktxtview_index.setChecked(false);
		cktxtview_news.setChecked(false);
		cktxtview_joke.setChecked(false);
		cktxtview_robots.setChecked(false);
	}

}
