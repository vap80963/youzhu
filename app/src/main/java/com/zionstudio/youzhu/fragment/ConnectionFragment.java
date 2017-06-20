package com.zionstudio.youzhu.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
import com.zionstudio.youzhu.activity.AddConnActivity;
import com.zionstudio.youzhu.activity.ConnSearchActivity;
import com.zionstudio.youzhu.activity.ConnShortActivity;
import com.zionstudio.youzhu.activity.ContactsActivity;
import com.zionstudio.youzhu.activity.NewConnectionActivity;
import com.zionstudio.youzhu.adapter.ConnectionAdapter;


import com.zionstudio.youzhu.bean.FriendList;
import com.zionstudio.youzhu.bean.PersonInfo;
import com.zionstudio.youzhu.bean.UserInfo;
import com.zionstudio.youzhu.utils.UrlUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.zionstudio.youzhu.MyApplication.iconList;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class ConnectionFragment extends Fragment {


    @Bind(R.id.ll_search)
    LinearLayout mLlSearch;
    @Bind(R.id.recyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.btn_add_conn)
    Button mBtnAddConn;

    private static final int REQUEST_CONNSHORT = 1;
    private List<PersonInfo> mDatas = new ArrayList<>();
    private ConnectionAdapter mAdapter;
    private View rootView = null;
    android.os.Handler mHandler = new android.os.Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    mDatas.clear();
                    mDatas.addAll((List<PersonInfo>) msg.getData().getSerializable("mDatas"));
                    mAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_connection, container, false);
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

    private void initData() {
        getFriendInfoFromServer();
    }

    private void initView() {
        mLlSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ConnectionFragment.this.getContext(), ConnSearchActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("friendList", (Serializable) mDatas);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        mAdapter = new ConnectionAdapter(this.getContext(), mDatas);
        mAdapter.setOnClickItemListener(new ConnectionAdapter.MyItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent = new Intent(ConnectionFragment.this.getContext(), ConnShortActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("personInfo", mDatas.get(position));
                intent.putExtras(bundle);
                startActivityForResult(intent, REQUEST_CONNSHORT);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
    }

    @OnClick({R.id.btn_add_conn})
    public void addConnection(View view) {
        ImageView iv;

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
        builder.setItems(getResources().getStringArray(R.array.conn_array), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        startActivity(new Intent(ConnectionFragment.this.getContext(), NewConnectionActivity.class));
                        break;
                    case 1:
                        startActivityForResult(new Intent(ConnectionFragment.this.getContext(), ContactsActivity.class), 1);
                        break;
                    default:
                        dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private Map<String, String> getFriendInfoFromServer() {
        Map<String, String> mMap = new HashMap<>();
        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                UrlUtil.getFriendListUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        List<PersonInfo> returnDatas = new ArrayList<>();
                        Log.e("net", response);
                        Gson gson = new Gson();
                        FriendList fl = gson.fromJson(response, FriendList.class);
                        returnDatas = fl.getFriendList();
                        if (returnDatas != null) {
                            Message msg = new Message();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("mDatas", (Serializable) returnDatas);
                            msg.setData(bundle);
                            msg.what = 1;
                            mHandler.sendMessage(msg);
                        }
//                        mAdapter.notifyAll();
//                        String name;
//                        for (int i = 0; i < mDatas.size(); i++) {
//                            name =
//                            mDatas.add(new PersonInfo(iconList[i++ % iconList.length], name, "18878788899"));
//                        }
//                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                map.put("cookie", MyApplication.sp.getString("cookie_", ""));
                return map;
            }
        };
        mQueue.add(stringRequest);
        return mMap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    Bundle bundle = data.getExtras();
                    ArrayList<HashMap<String, String>> mapList = new ArrayList<>();
                    String friendName;
                    String friendNum;
                    ArrayList<HashMap<String, String>> ctsList = (ArrayList<HashMap<String, String>>) bundle.get("checkedCtsData");
                    for (int i = 0; i < ctsList.size(); i++) {
                        friendName = ctsList.get(i).get("name");
                        friendNum = ctsList.get(i).get("phone");
                        HashMap<String, String> map = new HashMap<>();
                        PersonInfo p = new PersonInfo(iconList[i % iconList.length], ctsList.get(i).get("name"), ctsList.get(i).get("phone"));
                        map.put("friendName", friendName);
                        map.put("friendNum", friendNum);
                        map.put("friendSex", "男");
                        mapList.add(map);
                        upLoadContactsToServer(mapList.get(i));
                        mDatas.add(p);
                    }

                    mAdapter.notifyDataSetChanged();
                }
                break;
        }
    }

    public void upLoadContactsToServer(final HashMap<String, String> map) {

        RequestQueue mQueue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                UrlUtil.addFriendInfoUrl,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.e("upload", response);
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}
