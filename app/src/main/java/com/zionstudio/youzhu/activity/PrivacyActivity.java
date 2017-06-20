package com.zionstudio.youzhu.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.zionstudio.youzhu.ui.CommonTop;
import com.zionstudio.youzhu.ui.ItemView;
import com.zionstudio.youzhu.utils.UrlUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.zionstudio.youzhu.activity.PrivacyActivity.mPrivacyUrl;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class PrivacyActivity extends Activity {
    @Bind(R.id.itv_myprofile_privacy)
    ItemView mItvMyprofilePrivacy;
    @Bind(R.id.itv_sendmsg_privacy)
    ItemView mItvSendmsgPrivacy;
    @Bind(R.id.ct_privacy)
    CommonTop mCtPrivacy;

    public static final String mPrivacyUrl = UrlUtil.prefixUrl +"/App/Power/setPower";
    private RequestQueue mQueue;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mQueue = Volley.newRequestQueue(this);
        mSp = getSharedPreferences("setting", MODE_PRIVATE);
    }

    private void initView() {
        Button btnBack = (Button) mCtPrivacy.findViewById(R.id.btn_left);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PrivacyActivity.this.finish();
            }
        });
        mItvMyprofilePrivacy.setOnClickListener(new MyPrivacyOnClickListener(mItvMyprofilePrivacy, this, mQueue));
    //    mItvSendmsgPrivacy.setOnClickListener(new MyPrivacyOnClickListener(mItvSendmsgPrivacy, this, mQueue));
        mItvMyprofilePrivacy.setMsg(mSp.getString("privacy", "仅自己可见"));
        mItvSendmsgPrivacy.setMsg("所有人");
    }
}

class MyPrivacyOnClickListener implements View.OnClickListener {
    @Bind(R.id.rb_all_authority)
    RadioButton mRbAllAuthority;
    @Bind(R.id.rb_partial_authority)
    RadioButton mRbPartialAuthority;
    @Bind(R.id.rb_myself_authority)
    RadioButton mRbMyselfAuthority;
    @Bind(R.id.rg_authority)
    RadioGroup mRgAuthority;
    private ItemView mItv;
    private Context mContext;
    private int mPrivacyMode;
    private RequestQueue mQueue;
    private SharedPreferences sp;

    public MyPrivacyOnClickListener(ItemView itv, Context context, RequestQueue queue) {
        mItv = itv;
        mContext = context;
        mQueue = queue;
        sp = mContext.getSharedPreferences("setting", MODE_PRIVATE);
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_authority, null, false);
        builder.setView(view);
        final Dialog dialog = builder.create();
        RadioButton rbAll = (RadioButton) view.findViewById(R.id.rb_all_authority);
        RadioButton rbFriends = (RadioButton) view.findViewById(R.id.rb_partial_authority);
        RadioButton rbMyself = (RadioButton) view.findViewById(R.id.rb_myself_authority);
        RadioGroup rg = (RadioGroup) view.findViewById(R.id.rg_authority);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_all_authority:
                        mPrivacyMode = 1;
                        break;
                    case R.id.rb_partial_authority:

                        mPrivacyMode = 2;
                        break;
                    case R.id.rb_myself_authority:

                        mPrivacyMode = 0;
                        break;
                }

                unloadPrivacyToServer(mPrivacyMode);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private void unloadPrivacyToServer(final int mode) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, mPrivacyUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("net", "unloadPrivacyToServer:" + response);
                Gson gson = new Gson();
                Map<String, String> result = new HashMap<>();
                result = gson.fromJson(response, Map.class);
                if (result.get("statusCode").equals("000000")){
                    Toast.makeText(mContext, "设置成功", Toast.LENGTH_SHORT).show();
                    switch (mode) {
                        case 0:
                            sp.edit().putString("privacy", "仅自己可见").commit();
                            mItv.setMsg("仅自己可见");
                            break;
                        case 1:
                            sp.edit().putString("privacy", "所有人可见").commit();
                            mItv.setMsg("所有人可见");
                            break;
                        case 2:
                            sp.edit().putString("privacy", "部分人可见").commit();
                            mItv.setMsg("部分人可见");
                            break;
                    }
                } else {
                    Toast.makeText(mContext, "设置失败，稍后重试", Toast.LENGTH_SHORT).show();
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
                map.put("type", String.valueOf(mode));
                if (mode == 2) {
                    //加入phoneNum
                }
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
