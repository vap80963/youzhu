package com.zionstudio.youzhu.activity;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.zionstudio.youzhu.MyApplication;
import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.net.CookiePostRequest;
import com.zionstudio.youzhu.ui.CommonTop;
import com.zionstudio.youzhu.utils.MyUtils;
import com.zionstudio.youzhu.utils.UrlUtil;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/14 0014.
 */

public class SignInActivity extends Activity {


    @Bind(R.id.ct_signin)
    CommonTop mCtSignin;
    @Bind(R.id.et_username)
    EditText mEtUsername;
    @Bind(R.id.et_password_)
    EditText mEtPassword;
    @Bind(R.id.btn_signin)
    Button mBtnSignin;
    @Bind(R.id.fl_shadow)
    FrameLayout mFlShadow;

    private String mUserName = "";
    private String mPassword = "";
    private RequestQueue mQueue;
    private SharedPreferences mUserInfo;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mQueue = Volley.newRequestQueue(this);

        mUserInfo = getSharedPreferences("userInfo", 0);
        mEditor = mUserInfo.edit();

        mEtUsername.setText(mUserInfo.getString("userName", ""));
        mEtPassword.setText(mUserInfo.getString("password", ""));

    }

    private void initView() {
        Button btnLeft = (Button) mCtSignin.findViewById(R.id.btn_left);
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignInActivity.this.finish();
            }
        });
        mBtnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mUserName = mEtUsername.getText().toString();
                mPassword = mEtPassword.getText().toString();

                if (mUserName.equals("") || mPassword.equals("")) {
                    MyUtils.makeToast(SignInActivity.this, "请输入用户名和密码");
                } else {
                    //展示shadowView
                    mFlShadow.setVisibility(View.VISIBLE);
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mFlShadow, "alpha", 0.0f, 0.7f);
                    animator.setDuration(300);
                    animator.start();

                    checkUser();
                }
            }
        });
    }

    private boolean checkUser() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginPhoneNum", mUserName);
        map.put("loginPassword", MyUtils.getMD5String(mPassword).toLowerCase());

        final CookiePostRequest request = new CookiePostRequest(UrlUtil.loginUrl, map, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Gson gson = new Gson();
                Map<String, String> mResult = gson.fromJson(response.toString(), Map.class);

                if (mResult.get("statusCode").equals("000000")) {
                    MyUtils.myNum = mUserName;
                    mFlShadow.setVisibility(View.GONE);
                    SignInActivity.this.setResult(2);

                    mEditor.putString("userName", mUserName);
                    mEditor.putString("password", mPassword);
                    mEditor.commit();

                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                    SignInActivity.this.finish();
                } else {
                    ObjectAnimator animator = ObjectAnimator.ofFloat(mFlShadow, "alpha", 0.0f, 0.7f);
                    animator.setDuration(300);
                    animator.start();
                    mFlShadow.setVisibility(View.GONE);
                    Toast.makeText(SignInActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                String localCookieStr = MyApplication.sp.getString("cookie_", "");

                if (!localCookieStr.equals("")) {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Cookie", localCookieStr);//设置session
                    MyApplication.localCookie = localCookieStr;
                    MyApplication.sp.edit().putString("cookie_", localCookieStr);
                    return headers;
                } else {
                    return super.getHeaders();
                }
            }
        };

        mQueue.add(request);
        return true;
    }
}
