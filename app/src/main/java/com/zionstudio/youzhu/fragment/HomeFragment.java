package com.zionstudio.youzhu.fragment;

import android.content.res.Resources;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.adapter.HomePageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 11/11/2016.
 */
public class HomeFragment extends Fragment {

	private TextView mSquareTv;
	private TextView mMessageTv;
	private LinearLayout mSquareLL,mMessageLL;
	private ViewPager mViewPager;
	private List<Fragment> mFragmentList;

	private ImageView ivBottomLine;
	private int currIndex = 0;
	private int bottomLineWidth;
	private double offset = 0;
	private int position_one;
	public final static int num = 2 ;

	Fragment homeSquareFragment;
	Fragment homeMessageFragment;
	private int screenW;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_home,container,false);
		initWidth(view);
		initTextView(view);
		initViewPager(view);

		return view;

	}

	private void initTextView(View view) {
		mSquareTv = (TextView) view.findViewById(R.id.id_square_tv);
		mMessageTv = (TextView) view.findViewById(R.id.id_message_tv);

		mSquareTv.setOnClickListener(new MyOnclickListener(0));
		mMessageTv.setOnClickListener(new MyOnclickListener(1));
	}

	private void initWidth(View view) {

		ivBottomLine = (ImageView)view.findViewById(R.id.tab_line_igv);
		int imgW = ivBottomLine.getLayoutParams().width;
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;
		offset = imgW;
		Log.i("screenW",String.valueOf(screenW));
		Log.i("bmpW",String.valueOf(screenW));
		Log.i("offset",String.valueOf(offset));
		//imgageview设置平移，使下划线平移到初始位置（平移一个offset）
		Matrix matrix = new Matrix();
		matrix.postTranslate((float) offset, 0);
		ivBottomLine.setImageMatrix(matrix);

	}

	private void initViewPager(View view) {
		mViewPager = (ViewPager) view.findViewById(R.id.home_viewpager);

		mFragmentList = new ArrayList<Fragment>();

		homeSquareFragment = new HomeSquareFragment();
		homeMessageFragment = new HomeMessageFragment();

		mFragmentList.add(homeSquareFragment);
		mFragmentList.add(homeMessageFragment);

		mViewPager.setAdapter(new HomePageAdapter(getChildFragmentManager(),mFragmentList));
		mViewPager.addOnPageChangeListener(new MyPageChangeListener());
		mViewPager.setCurrentItem(0);

	}


	public class MyOnclickListener implements View.OnClickListener{

		private int index;

		public MyOnclickListener(int i) {
			index = i;
		}

		@Override
		public void onClick(View view) {
			mViewPager.setCurrentItem(index);
//			Toast.makeText(getActivity(),"这是第" + index + "个页面",Toast.LENGTH_SHORT).show();
		}
	}

	public class MyPageChangeListener implements ViewPager.OnPageChangeListener{

//		private double one = offset*2 + screenW; //两个相邻页面的偏移量

		@Override
		public void onPageSelected(int arg0) {
			Log.i("aaaaaaaaaaaaa",String.valueOf(arg0));
			Log.i("one",String.valueOf(offset));
			Resources resources;
			Animation animation = new TranslateAnimation((int)(currIndex*offset+screenW*0.25),(int)(arg0*offset),0,0);//平移动画
			switch (arg0){
				case 1:
					mMessageTv.setTextColor(getResources().getColor(R.color.white));
					mSquareTv.setTextColor(getResources().getColor(R.color.textGray));
					break;
				case 0:
					mSquareTv.setTextColor(getResources().getColor(R.color.white));
					mMessageTv.setTextColor(getResources().getColor(R.color.textGray));
					break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);//动画终止时停留在最后一帧，不然会回到没有执行前的状态
			animation.setDuration(300);//动画持续时间0.2秒
			ivBottomLine.startAnimation(animation);//是用ImageView来显示动画的
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//			TranslateAnimation animation = new TranslateAnimation()
		}

		@Override
		public void onPageScrollStateChanged(int state) {

		}
	}
}
