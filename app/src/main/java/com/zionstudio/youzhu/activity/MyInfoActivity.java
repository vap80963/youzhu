package com.zionstudio.youzhu.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.google.gson.JsonObject;
import com.liangfeizc.flowlayout.FlowLayout;
import com.zionstudio.youzhu.MyApplication;
import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.bean.MyInfo;
import com.zionstudio.youzhu.bean.MyLabel;
import com.zionstudio.youzhu.ui.CommonTop;
import com.zionstudio.youzhu.utils.MyUtils;
import com.zionstudio.youzhu.utils.UrlUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/13 0013.
 */

public class MyInfoActivity extends Activity {
    public static final String uploadUrl = UrlUtil.prefixUrl +"/App/User/addUserLabel";
    public static final String removeUrl = UrlUtil.prefixUrl +"/App/Label/deleteLabel";
    public static final String getMyInfoUrl = UrlUtil.prefixUrl +"/App/User/getUserInfo";
    @Bind(R.id.ct_myinfo)
    CommonTop mCtMyinfo;
    @Bind(R.id.iv_touxiang_myinfo)
    ImageView mIvTouxiangMyinfo;
    @Bind(R.id.tv_name_myinfo)
    TextView mTvNameMyinfo;
    @Bind(R.id.tv_company_myinfo)
    TextView mTvCompanyMyinfo;
    @Bind(R.id.tv_position_myinfo)
    TextView mTvPositionMyinfo;
    @Bind(R.id.rl_info_myinfo)
    RelativeLayout mRlInfoMyinfo;
    @Bind(R.id.tv_contact_myinfo)
    TextView mTvContactMyinfo;
    @Bind(R.id.tv_number_myinfo)
    TextView mTvNumberMyinfo;
    @Bind(R.id.tv_email_myinfo)
    TextView mTvEmailMyinfo;
    @Bind(R.id.rl_me_info)
    RelativeLayout mRlMeInfo;
    @Bind(R.id.tv_fl_myinfo)
    TextView mTvFlMyinfo;
    @Bind(R.id.fl_myinfo)
    FlowLayout mFlMyinfo;
    @Bind(R.id.iv_add_myinfo)
    ImageView mIvAddMyinfo;

    private List<MyLabel> mLabelList = new ArrayList<>();
    private RequestQueue mQueue;
    private MyInfo mInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mQueue = Volley.newRequestQueue(this);
//        for(int i = 0;i<mLabelList.size();i++){
//
//        }
    }

    private void updateFL() {

        mFlMyinfo.removeAllViews();
        for (int i = 0; i < mLabelList.size(); i++) {
            final int pos = i;
            View view = LayoutInflater.from(this).inflate(R.layout.item_label, null, false);
            TextView tv = (TextView) view.findViewById(R.id.tv_text_label);
            final ImageView iv = (ImageView) view.findViewById(R.id.iv_close_label);
            iv.setVisibility(View.GONE);
            Log.e("net", mLabelList.get(i).getLabelName());
            tv.setText(mLabelList.get(i).getLabelName());
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (iv.getVisibility() == View.GONE) {
                        iv.setVisibility(View.VISIBLE);
                    } else {
                        iv.setVisibility(View.GONE);
                    }
                }
            });

            iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    removeFromServer(String.valueOf(mLabelList.get(pos).getLabelID()));
                }
            });
            mFlMyinfo.addView(view);
        }


    }

    private void initView() {
        getMyInfo();
        Button btn = (Button) mCtMyinfo.findViewById(R.id.btn_left);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyInfoActivity.this.finish();
            }
        });

        mIvAddMyinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MyInfoActivity.this);
                View view = LayoutInflater.from(MyInfoActivity.this).inflate(R.layout.dialog_addlabel, null, false);
                builder.setView(view);
                final Dialog ad = builder.create();

                final EditText et = (EditText) view.findViewById(R.id.et_add_dialog);
                Button btn = (Button) view.findViewById(R.id.btn_confirm_addlabel);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String label = et.getText().toString();
                        if (!label.trim().equals("")) {
                            //    mLabelList.add(label);
                            //    initFL();
                            uploadToServer(label);
                        }
                        ad.dismiss();
                    }
                });
                ad.show();
            }
        });
    }

    private void removeFromServer(final String labelID) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, removeUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("net", "remove onResponse: " + response);
                        getMyInfo();
                        updateFL();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Log.e("net", "onErrorResponse: " +error.getMessage().toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                String num = MyUtils.myNum;
//                try {
//                    num = URLEncoder.encode("15521177687", "utf-8");
//                } catch (UnsupportedEncodingException e) {
//                    e.printStackTrace();
//                }
                map.put("friendNum", num);
                map.put("labelID", labelID);
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

    private void uploadToServer(final String label) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("net", "uploadToServer response:" + response);
                        //     Toast.makeText(MyInfoActivity.this, "uploadToServer response:" + response, Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        Map<String, String> result = new HashMap<>();
                        result = gson.fromJson(response, Map.class);
                        if (result.get("statusCode").equals("000000")) {
                            getMyInfo();
                            updateFL();
                        } else {

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("net", "error occurred");
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                Log.e("net", "label:" + label);
                map.put("labelName", label);
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

    public void getMyInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getMyInfoUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                Log.e("net", response);
                mInfo = gson.fromJson(response, MyInfo.class);
                if (mInfo.getUserInfo() != null) {
                    mLabelList = mInfo.getUserInfo().getLabelList();
                    updateFL();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("cookie", MyApplication.sp.getString("cookie_", ""));
                return map;
            }
        };

        mQueue.add(stringRequest);
    }

}
