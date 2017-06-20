package com.zionstudio.youzhu.activity;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.liangfeizc.flowlayout.FlowLayout;
import com.zionstudio.youzhu.MyApplication;
import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.bean.FriendLabelList;
import com.zionstudio.youzhu.bean.FriendList;
import com.zionstudio.youzhu.bean.Label;
import com.zionstudio.youzhu.bean.PersonInfo;
import com.zionstudio.youzhu.utils.UrlUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/9 0009.
 */

public class ConnSearchActivity extends Activity {
    @Bind(R.id.et_conn_search)
    EditText mEtConnSearch;
    @Bind(R.id.flow_layout)
    FlowLayout mFlowLayout;
    @Bind(R.id.iv_back_conn_search)
    ImageView mIvBackConnSearch;
    @Bind(R.id.tv_sear)
    TextView mTvSear;
    @Bind(R.id.rv_search)
    RecyclerView mRvSearch;

    private static final String getLabelUrl = UrlUtil.prefixUrl +"/App/Label/getFriendLabelList";
    private List<PersonInfo> friendList;
    private RequestQueue mQueue;
    private List<Label> mFriednLabelList = new ArrayList<>();
    private List<PersonInfo> mReturnList;
    private List<String> mDatas = new ArrayList<>();
    private List<String> mExistList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_search);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initView() {
        Drawable drawable = getResources().getDrawable(R.drawable.search);
        drawable.setBounds(0, 0, 60, 60);
        mEtConnSearch.setCompoundDrawables(drawable, null, null, null);

        mIvBackConnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnSearchActivity.this.finish();
            }
        });


        mTvSear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("sear", "111");
                String str = mEtConnSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(str)) {
                    searInfo(str);
                }
            }
        });
    }

    private void searInfo(final String str) {
        Log.e("sear", "1111");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UrlUtil.searchUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Log.e("sear", "onResponse: " + response);
                FriendList fl = gson.fromJson(response, FriendList.class);
                if (fl != null) {
                    mReturnList = fl.getFriendLabelList();
                    int num = mReturnList.get(0).getNum();
                    if( num != -1){
                        Toast.makeText(ConnSearchActivity.this, "在您好友的人脉中搜索到" +num +"个结果", Toast.LENGTH_SHORT ).show();
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("labelName", str);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("cookie", MyApplication.sp.getString("cookie_", ""));
                return map;
            }
        };

        mQueue.add(stringRequest);
    }

    private void initData() {
        mQueue = Volley.newRequestQueue(this);
        Bundle bundle = this.getIntent().getExtras();
        friendList = (List<PersonInfo>) bundle.getSerializable("friendList");
        for (int i = 0; i < friendList.size(); i++) {
            getLabel(friendList.get(i).getNumber());
        }
    }

    private boolean isExistString(List<String> list, String str) {
        for (String mStr : list) {
            if (mStr.equals("str")) {
                return true;
            }
        }
        return false;
    }

    private void getLabel(final String num) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getLabelUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("short", response);
                Gson gson = new Gson();
                FriendLabelList fl = gson.fromJson(response, FriendLabelList.class);
                mFriednLabelList = fl.getFriendLabelList();
                if (mFriednLabelList != null) {
                    for (int i = 0; i < mFriednLabelList.size(); i++) {
                        mDatas.add(mFriednLabelList.get(i).getLabelName());
                    }

                    initFl();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("friendNum", num);
                return map;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("cookie", MyApplication.sp.getString("cookie_", ""));
                return map;
            }
        };

        mQueue.add(stringRequest);
    }

    private void initFl() {
        mFlowLayout.removeAllViews();
        for (int i = 0; i < mDatas.size(); i++) {
            if (true) {
                View view = LayoutInflater.from(this).inflate(R.layout.item_label, mFlowLayout, false);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView tv = (TextView) v.findViewById(R.id.tv_text_label);
                        mEtConnSearch.setText(tv.getText().toString());
                    }
                });
                TextView tv = (TextView) view.findViewById(R.id.tv_text_label);
                tv.setText(mDatas.get(i));
                tv.setTextColor(Color.GRAY);
                tv.setBackgroundResource(R.drawable.fl_bg_white);
                mFlowLayout.addView(view);
            }
        }
    }
}
