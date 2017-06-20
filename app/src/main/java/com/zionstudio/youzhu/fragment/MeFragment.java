package com.zionstudio.youzhu.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.activity.MyInfoActivity;
import com.zionstudio.youzhu.activity.PrivacyActivity;
import com.zionstudio.youzhu.activity.SettingActivity;
import com.zionstudio.youzhu.ui.ItemView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class MeFragment extends Fragment {

    private static final int SETTINGACTIVITY_REQUESTCODE = 1;
    private static final int EXIT_RESULTCODE = 2;
    @Bind(R.id.iv_top_touxiang)
    ImageView mIvTopTouxiang;
    @Bind(R.id.tv_top_name)
    TextView mTvTopName;
    @Bind(R.id.rl_me_info)
    RelativeLayout mRlMeInfo;
    @Bind(R.id.itv_vipcenter)
    ItemView mItvVipcenter;
    @Bind(R.id.itv_visitor)
    ItemView mItvVisitor;
    @Bind(R.id.itv_collection)
    ItemView mItvCollection;
    @Bind(R.id.itv_setting)
    ItemView mItvSetting;
    @Bind(R.id.tv_top_company)
    TextView mTvTopCompany;
    @Bind(R.id.tv_top_position)
    TextView mTvTopPosition;
    @Bind(R.id.tv_privacy)
    TextView mTvPrivacy;

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_me, container, false);
            ButterKnife.bind(this, rootView);
            initData();
            initView();
        }
        ViewGroup parent = (ViewGroup) rootView.getParent();
        if (parent != null) {
            parent.removeView(rootView);
        }
        ButterKnife.bind(this, rootView);
        return rootView;

    }

    @OnClick({R.id.rl_me_info, R.id.itv_collection, R.id.itv_setting, R.id.itv_vipcenter, R.id.itv_visitor, R.id.tv_privacy})
    public void onItemViewClick(View view) {
        switch (view.getId()) {
            case R.id.rl_me_info:
                startActivity(new Intent(getContext(), MyInfoActivity.class));
                break;
            case R.id.itv_collection:
                break;
            case R.id.itv_setting:
                startActivityForResult(new Intent(getContext(), SettingActivity.class), SETTINGACTIVITY_REQUESTCODE);
                break;
            case R.id.itv_vipcenter:
                break;
            case R.id.itv_visitor:
                break;
            case R.id.tv_privacy:
                startActivity(new Intent(getContext(), PrivacyActivity.class));
                break;
        }
    }

    private void initView() {
    }

    private void initData() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SETTINGACTIVITY_REQUESTCODE:
                if (resultCode == EXIT_RESULTCODE) {
                    this.getActivity().finish();
                }
                break;
        }
    }
}
