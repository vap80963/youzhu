package com.zionstudio.youzhu.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.zionstudio.youzhu.fragment.HomeFragment;
import com.zionstudio.youzhu.fragment.MeFragment;
import com.zionstudio.youzhu.fragment.ConnectionFragment;
import com.zionstudio.youzhu.R;

/**
 * Created by Administrator on 2016/11/8 0008.
 */

public class MainActivity extends FragmentActivity {

    private FragmentTabHost mTabHost;
    private LayoutInflater mLayoutInflater;
    private Class mFragmentArray[] = {HomeFragment.class, ConnectionFragment.class, MeFragment.class};
    private int mTabImageArray[] = {R.drawable.home_selector, R.drawable.connection_selector, R.drawable.me_selector};
    private String mTabTextArray[] = {"首页", "人脉", "我"};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mLayoutInflater = LayoutInflater.from(this);
        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);
        for (int i = 0; i < mFragmentArray.length; i++) {
            TabHost.TabSpec tabSpec = mTabHost.newTabSpec(mTabTextArray[i]).setIndicator(getTabItemView(i));
            mTabHost.addTab(tabSpec, mFragmentArray[i], null);
        }
    }

    private View getTabItemView(int index) {
        View view = mLayoutInflater.inflate(R.layout.tab_item_view, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_tab);
        imageView.setImageResource(mTabImageArray[index]);
        TextView textView = (TextView) view.findViewById(R.id.tv_tab);
        textView.setText(mTabTextArray[index]);
        return view;
    }
}
