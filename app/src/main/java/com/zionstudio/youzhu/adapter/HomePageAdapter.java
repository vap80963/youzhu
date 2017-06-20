package com.zionstudio.youzhu.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by hasee on 11/11/2016.
 */
public class HomePageAdapter extends FragmentPagerAdapter {

	private List<Fragment> mFragmentList;

	public HomePageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
		super(fm);
		this.mFragmentList = fragmentList;
	}

	@Override
	public Fragment getItem(int position) {
		return mFragmentList.get(position);
	}

	@Override
	public int getCount() {
		return mFragmentList.size();
	}
}
