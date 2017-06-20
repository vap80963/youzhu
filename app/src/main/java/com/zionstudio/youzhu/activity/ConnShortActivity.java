package com.zionstudio.youzhu.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
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
import com.zionstudio.youzhu.MyApplication;
import com.zionstudio.youzhu.R;

import com.zionstudio.youzhu.bean.FriendLabelList;
import com.zionstudio.youzhu.bean.FriendList;
import com.zionstudio.youzhu.bean.Label;
import com.zionstudio.youzhu.bean.MyInfo;
import com.zionstudio.youzhu.bean.PersonInfo;
import com.zionstudio.youzhu.bean.UserInfo;
import com.zionstudio.youzhu.ui.CommonTop;
import com.zionstudio.youzhu.utils.UrlUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by hasee on 11/17/2016.
 */
public class ConnShortActivity extends Activity {

    @Bind(R.id.ct_conn_detail)
    CommonTop mCtConnDetail;
    @Bind(R.id.conn_detail_name)
    TextView mConnDetailName;
    @Bind(R.id.enjoy_btn)
    ImageButton mEnjoyBtn;
    @Bind(R.id.et_phone_short)
    EditText mEtPhoneShort;
    @Bind(R.id.et_email_short)
    EditText mEtEmailShort;
    @Bind(R.id.iv_label_short)
    ImageView mIvLabelShort;
    @Bind(R.id.tv_label_short)
    TextView mTvLabelShort;
    @Bind(R.id.iv_add_short)
    ImageView mIvAddShort;
    @Bind(R.id.fl_short)
    com.liangfeizc.flowlayout.FlowLayout mFlShort;

    private String url = "";
    private PersonInfo mInfo;
    private RequestQueue mQueue;
    private static final String getLabelUrl = UrlUtil.prefixUrl +"/App/Label/getFriendLabelList";
    private static final String deleteFriendLabelUrl = UrlUtil.prefixUrl +"/App/Label/deleteLabel";
    private static final String uploadFriendLabelUrl = UrlUtil.prefixUrl +"/App/Label/addLabel";
    private static final String editFriendInfoUrl = UrlUtil.prefixUrl +"/App/Friend/editFriendInfo";
    private List<Label> mFriednLabelList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn_short);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        Button btnLeft = (Button) mCtConnDetail.findViewById(R.id.btn_left);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnShortActivity.this.finish();
            }
        });

        Button btnRight = (Button) mCtConnDetail.findViewById(R.id.btn_right);
        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mEtPhoneShort.getText().equals(mInfo.getNumber()) || !mEtEmailShort.getText().equals(mInfo.getEmail())
                        || !mConnDetailName.getText().equals(mInfo.getName())) {
                    editInfo();
                    ConnShortActivity.this.finish();
                }
            }
        });

        mIvAddShort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ConnShortActivity.this);
                View view = LayoutInflater.from(ConnShortActivity.this).inflate(R.layout.dialog_addlabel, null, false);
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
                            uploadFriendLabel(label);
                        }
                        ad.dismiss();
                    }
                });
                ad.show();
            }
        });
    }

    private void editInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, editFriendInfoUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("short", "editInfo:" + response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("friendName", mConnDetailName.getText().toString());
                map.put("friendNum", mEtPhoneShort.getText().toString());
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
        mInfo = (PersonInfo) bundle.getSerializable("personInfo");
        mConnDetailName.setText(mInfo.getName());
        mEtPhoneShort.setText(mInfo.getNumber());
        mEtEmailShort.setText(mInfo.getEmail());
        getInfo();
    }

    private void getInfo() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, getLabelUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("short", response);
                Gson gson = new Gson();
                FriendLabelList fl = gson.fromJson(response, FriendLabelList.class);
                mFriednLabelList = fl.getFriendLabelList();
                updateFl();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("friendNum", mInfo.getNumber());
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


    private void updateFl() {
        mFlShort.removeAllViews();
        if (mFriednLabelList != null) {
            for (int i = 0; i < mFriednLabelList.size(); i++) {
                String labelName = mFriednLabelList.get(i).getLabelName();
                final int pos = i;
                View view = LayoutInflater.from(this).inflate(R.layout.item_label, null, false);
                TextView tv = (TextView) view.findViewById(R.id.tv_text_label);
                final ImageView iv = (ImageView) view.findViewById(R.id.iv_close_label);
                iv.setVisibility(View.GONE);
                Log.e("net", mFriednLabelList.get(i).getLabelName());
                tv.setText(mFriednLabelList.get(i).getLabelName());
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
                        removeFriendLabel(String.valueOf(mFriednLabelList.get(pos).getLabelID()));
                    }
                });
                mFlShort.addView(view);
            }
        }
    }

    private void removeFriendLabel(String id) {
        final String delID = id;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, deleteFriendLabelUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("short", response);
                Gson gson = new Gson();
                Map<String, String> result = gson.fromJson(response, Map.class);
                if (result.get("statusCode").equals("140005")) {
                    Toast.makeText(ConnShortActivity.this, "您不能删除好友自己添加的标签", Toast.LENGTH_SHORT).show();
                } else if (result.get("statusCode").equals("000000")) {
                    getInfo();
                    updateFl();
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
                map.put("friendNum", mInfo.getNumber());
                map.put("labelID", delID);
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

    private void uploadFriendLabel(final String label) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, uploadFriendLabelUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("net", "uploadToServer response:" + response);
                        //     Toast.makeText(MyInfoActivity.this, "uploadToServer response:" + response, Toast.LENGTH_SHORT).show();
                        Gson gson = new Gson();
                        Map<String, String> result = new HashMap<>();
                        result = gson.fromJson(response, Map.class);
                        if (result.get("statusCode").equals("000000")) {
                            getInfo();
                            updateFl();
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
                map.put("labelName", label);
                map.put("friendNum", mInfo.getNumber());
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
}
