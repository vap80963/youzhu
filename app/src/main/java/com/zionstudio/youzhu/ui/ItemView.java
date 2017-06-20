package com.zionstudio.youzhu.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zionstudio.youzhu.R;

/**
 * Created by Administrator on 2016/11/10 0010.
 */

public class ItemView extends RelativeLayout {
    private final static String NAMESPACE = "http://schemas.android.com/apk/res-auto";
    private ImageView mIvIcon;
    private TextView mTvTitle;
    private TextView mTvMsg;
    private ImageView mIvForward;

    private int mIcon;
    private String mTitle;
    private String mMsg = "";
    private boolean mNoIcon;
    private int msgColor;

    public ItemView(Context context) {
        this(context, null);
        Log.e("111111", "1");
    }

    public ItemView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e("111111", "11");
        mTitle = attrs.getAttributeValue(NAMESPACE, "title");
        mIcon = attrs.getAttributeResourceValue(NAMESPACE, "icon", -1);
        mMsg = attrs.getAttributeValue(NAMESPACE, "msg");
        msgColor = attrs.getAttributeResourceValue(NAMESPACE, "msgColor", -1);
        mNoIcon = attrs.getAttributeBooleanValue(NAMESPACE, "noIcon", false);
        initView();

    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.e("111111", "111");
        initView();
    }

    private void initView() {
        View.inflate(getContext(), R.layout.item_me, this);
        mTvTitle = (TextView) findViewById(R.id.tv_item_title_me);
        mIvIcon = (ImageView) findViewById(R.id.iv_item_icon_me);
        mTvMsg = (TextView) findViewById(R.id.tv_item_msg_me);
        if (mNoIcon) {
            mIvIcon.setVisibility(GONE);
        } else {
            mIvIcon.setImageResource(mIcon);
        }

        mTvTitle.setText(mTitle);
        mTvMsg.setText(mMsg);
    }

    public void setMsg(String msg){
        mTvMsg.setText(msg);
    }

}
