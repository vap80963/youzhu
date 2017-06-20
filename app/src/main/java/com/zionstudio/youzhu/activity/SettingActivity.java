package com.zionstudio.youzhu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.android.volley.RequestQueue;
import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.ui.CommonTop;
import com.zionstudio.youzhu.ui.ItemView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class SettingActivity extends Activity {


    @Bind(R.id.ct_setting)
    CommonTop mCtSetting;
    @Bind(R.id.itemView_safety)
    ItemView mItemViewSafety;
    @Bind(R.id.itemView_clearCache)
    ItemView mItemViewClearCache;
    @Bind(R.id.itemView_about)
    ItemView mItemViewAbout;
    @Bind(R.id.itemView_exit)
    ItemView mItemViewExit;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        mCtSetting.findViewById(R.id.btn_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SettingActivity.this.finish();
            }
        });

        mItemViewExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, SignInAndUpActivity.class));

                SettingActivity.this.setResult(2);
                SettingActivity.this.finish();
            }
        });
    }

    private void initData() {
    }
}
