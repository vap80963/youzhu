package com.zionstudio.youzhu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.zionstudio.youzhu.utils.UrlUtil;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hasee on 11/16/2016.
 */
public class NewConnectionActivity extends Activity implements View.OnClickListener {

	private static final String TAG = "NewConnectionActivity";

	private Response.Listener listener;
	private Response.ErrorListener errorListener;
	private com.zionstudio.youzhu.ui.CommonTop ctcontact;

	private ImageView newcontactheadimg;
	private EditText newcontactname;
	private EditText newcontactcellphone;
	private EditText newcontactcompany;
	private EditText newcontactaddress;
	private EditText newcontactinformation;
	private EditText newcontactassosicate;
	private EditText newcontactextra;
	private EditText newcontactlabel;
	private Button backBtn;
	private Button finishBtn;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_new_connection);
		initialize();

		backBtn.setOnClickListener(this);
		finishBtn.setOnClickListener(this);
	}

	private void initialize() {

		newcontactheadimg = (ImageView) findViewById(R.id.new_contact_head_img);
		newcontactname = (EditText) findViewById(R.id.new_contact_name);
		newcontactcellphone = (EditText) findViewById(R.id.new_contact_cellphone);
		newcontactcompany = (EditText) findViewById(R.id.new_contact_company);
		newcontactaddress = (EditText) findViewById(R.id.new_contact_address);
		newcontactinformation = (EditText) findViewById(R.id.new_contact_information);
		newcontactassosicate = (EditText) findViewById(R.id.new_contact_assosicate);
		newcontactextra = (EditText) findViewById(R.id.new_contact_extra);

		ctcontact = (CommonTop) findViewById(R.id.ct_contact);
		backBtn = (Button) ctcontact.findViewById(R.id.btn_left);
		finishBtn = (Button) ctcontact.findViewById(R.id.btn_right);

	}

	@Override
	public void onClick(View view) {
		switch (view.getId()){
			case R.id.btn_left:
				NewConnectionActivity.this.finish();
				break;
			case R.id.btn_right:
				RequestQueue mQueue = Volley.newRequestQueue(this);
				StringRequest mStrRequest = new StringRequest(Request.Method.POST, UrlUtil.addFriendInfoUrl,
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								Log.e(TAG, "Response.Listener<String> = " + response);
								Gson gson = new Gson();
								Map<String,String> mResult = gson.fromJson(response,Map.class);
								String stausCode = mResult.get("stausCode");
								Log.e(TAG, "onResponse: " + mResult.get("stausCode"));
								if(!newcontactname.getText().toString().equals("") && !newcontactcellphone.getText().toString().equals("")){
									switch (stausCode){
										case "000000": //处理成功
											NewConnectionActivity.this.finish();
											break;
										case "120001": //手机号格式非法
											Toast.makeText(NewConnectionActivity.this,"请输入正确的手机号码",Toast.LENGTH_SHORT).show();
											break;
										case "150001": //好友名字长度非法
											Toast.makeText(NewConnectionActivity.this,"请输入正确的姓名",Toast.LENGTH_SHORT).show();
											break;
										case "150002": //好友性别格式非法
											break;
										case "150003": //新增好友信息失败，好友已存在
											Toast.makeText(NewConnectionActivity.this,"该好友已存在",Toast.LENGTH_SHORT).show();
											break;
									}
								}else {
									Toast.makeText(NewConnectionActivity.this,"请输入姓名和手机号码",Toast.LENGTH_SHORT).show();
								}
							}
						},
						new Response.ErrorListener() {
							@Override
							public void onErrorResponse(VolleyError error) {
								Log.e(TAG, "Response.ErrorListener" + error.getMessage(), error);
							}
						}) {
					@Override
					protected Map<String, String> getParams() throws AuthFailureError {
						Map<String, String> map = new HashMap<>();
						String friendAddress = newcontactaddress.getText().toString();
//								String friendLabelName = newcontactlabel.getText().toString();
						String friendName = newcontactname.getText().toString();
						String friendNum = newcontactcellphone.getText().toString();
						Log.e(TAG,"friendAddress=" + friendAddress + ";friendName=" + friendName + "friendNum=" + friendNum);
						map.put("friendAddress", friendAddress);
//								map.put("friendLabelName", friendLabelName);
						map.put("friendName", friendName);
						map.put("friendNum", friendNum);
						map.put("friendSex", "男");
						return map;
					}

					@Override
					public Map<String, String> getHeaders() throws AuthFailureError {
						Map<String,String> map = new HashMap<>();
						map.put("cookie", MyApplication.sp.getString("cookie_",""));
						return map;
					}
				};
				mQueue.add(mStrRequest);
				break;
			default:
				break;
		}
	}


}
