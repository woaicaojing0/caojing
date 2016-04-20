package com.cj.journeyhelper;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class test extends Activity {
	private RecyclerView mRecyclerView;
	private List<String> mDatas;
	private HomeAdapter mAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zhihu);
		initData();
		mRecyclerView = (RecyclerView) findViewById(R.id.id_recyclerview);
		// mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
		mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,
				StaggeredGridLayoutManager.VERTICAL));
		// mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
		mRecyclerView.setAdapter(mAdapter = new HomeAdapter(mDatas));
		// mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
		// mRecyclerView.addItemDecoration(new ItemDecoration(this,
		// ItemDecoration.VERTICAL_LIST));
		mAdapter.setOnItemClickLitener(new OnItemClickLiteners() {

			@Override
			public void onItemLongClick(View view, int position) {
				// TODO 自动生成的方法存根
				Toast.makeText(test.this, "这是长按按钮" + mDatas.get(position), 1)
						.show();
			}

			@Override
			public void onItemClick(View view, int position) {
				// TODO 自动生成的方法存根
				Toast.makeText(test.this, "这是短按按钮" + mDatas.get(position), 1)
						.show();
			}
		});
	}

	protected void initData() {
		mDatas = new ArrayList<String>();
		for (int i = 'A'; i < 'z'; i++) {
			mDatas.add("" + (char) i);
		}
	}

	public interface OnItemClickLiteners {
		void onItemClick(View view, int position);

		void onItemLongClick(View view, int position);
	}

	class myadapter extends RecyclerView.Adapter<myadapter.MyviewHoder> {

		class MyviewHoder extends ViewHolder {

			public MyviewHoder(View arg0) {
				super(arg0);
				// TODO 自动生成的构造函数存根
			}
		}

		@Override
		public int getItemCount() {
			// TODO 自动生成的方法存根
			return 0;
		}

		@Override
		public void onBindViewHolder(MyviewHoder arg0, int arg1) {
			// TODO 自动生成的方法存根
			
		}

		@Override
		public MyviewHoder onCreateViewHolder(ViewGroup arg0, int arg1) {
			// TODO 自动生成的方法存根
			return null;
		}
	}

	public class HomeAdapter extends
			RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

		private OnItemClickLiteners mOnItemClickLitener;

		public void setOnItemClickLitener(
				OnItemClickLiteners mOnItemClickLitener) {
			this.mOnItemClickLitener = mOnItemClickLitener;
		}

		public HomeAdapter(List<String> list) {
			getRandomHeight(list);
		}

		private List<Integer> heights;

		@Override
		public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
					test.this).inflate(R.layout.item_home, parent, false));
			return holder;
		}

		private void getRandomHeight(List<String> lists) {// 得到随机item的高度
			heights = new ArrayList<>();
			for (int i = 0; i < lists.size(); i++) {
				heights.add((int) (100 + Math.random() * 400));
			}
		}

		@Override
		public void onBindViewHolder(final MyViewHolder holder, int position) {
			ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();// 得到item的LayoutParams布局参数
			params.height = heights.get(position);// 把随机的高度赋予item布局
			holder.itemView.setLayoutParams(params);// 把params设置给item布局
			holder.tv.setText(mDatas.get(position));
			// 如果设置了回调，则设置点击事件
			if (mOnItemClickLitener != null) {
				holder.itemView.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO 自动生成的方法存根
						int pos = holder.getLayoutPosition();
						mOnItemClickLitener.onItemClick(holder.itemView, pos);
					}
				});

				holder.itemView
						.setOnLongClickListener(new OnLongClickListener() {
							@Override
							public boolean onLongClick(View v) {
								int pos = holder.getLayoutPosition();
								mOnItemClickLitener.onItemLongClick(
										holder.itemView, pos);
								return true;
							}
						});
			}
		}

		@Override
		public int getItemCount() {
			return mDatas.size();
		}

		class MyViewHolder extends ViewHolder {

			TextView tv;

			public MyViewHolder(View view) {
				super(view);
				tv = (TextView) view.findViewById(R.id.id_num);
			}
		}
	}

}
