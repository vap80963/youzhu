package com.zionstudio.youzhu.ui;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zionstudio.youzhu.R;

import org.w3c.dom.Text;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class CommonTop extends RelativeLayout {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res-auto";

    private Button btnLeft;
    private TextView tvTitle;
    private Button btnRight;

    private int leftBtn;
    private String title;
    private String rightBtn;
    private int rightBtnRes;
    private boolean noTitle;
    private boolean noLeftBtn;
    private boolean noRightBtn;

    public CommonTop(Context context) {
        this(context, null);
    }

    public CommonTop(Context context, AttributeSet attrs) {
        super(context, attrs);

        leftBtn = attrs.getAttributeResourceValue(NAMESPACE, "leftBtn", -1);
        title = attrs.getAttributeValue(NAMESPACE, "topTitle");
        rightBtn = attrs.getAttributeValue(NAMESPACE, "rightBtn");
        noTitle = attrs.getAttributeBooleanValue(NAMESPACE, "noTitle", false);
        noLeftBtn = attrs.getAttributeBooleanValue(NAMESPACE, "noLeftBtn", false);
        noRightBtn = attrs.getAttributeBooleanValue(NAMESPACE, "noRightBtn", false);
        rightBtnRes = attrs.getAttributeResourceValue(NAMESPACE, "rightBtnRes", -1);
        initView();
    }

    public CommonTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private void initView() {
        View.inflate(getContext(), R.layout.top_common, this);
        btnLeft = (Button) findViewById(R.id.btn_left);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        btnRight = (Button) findViewById(R.id.btn_right);

        if (noLeftBtn) {
            btnLeft.setVisibility(GONE);
        } else {
            btnLeft.setBackgroundResource(leftBtn);
        }

        if (noTitle) {
            tvTitle.setVisibility(GONE);
        } else {
            tvTitle.setText(title);
        }

        if (noRightBtn) {
            btnRight.setVisibility(GONE);
        } else {
            btnRight.setText(rightBtn);
        }

        if(rightBtnRes != -1){
            btnRight.setBackgroundResource(rightBtnRes);
        }
    }
}

