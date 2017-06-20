package com.zionstudio.youzhu.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.adapter.ContactsAdapter;
import com.zionstudio.youzhu.bean.Cts;
import com.zionstudio.youzhu.ui.CommonTop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class ContactsActivity extends Activity {


    @Bind(R.id.ct_contacts)
    CommonTop mCtContacts;
    @Bind(R.id.rlv_cts)
    RecyclerView mRlvCts;
    @Bind(R.id.cb_all)
    RadioButton mCbAll;
    @Bind(R.id.cb_none)
    RadioButton mCbNone;
    @Bind(R.id.btn_confirm_contacts)
    Button mBtnConfirmContacts;

    private Cts mCts = new Cts();
    //    private ArrayList<HashMap<String, String>> mCtsList;
    private LinearLayoutManager mLlMgr;
    private ContactsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initData() {
        mCts.setCtsList(readContacts());
    }

    private void initView() {
        mCbAll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCbAll.isChecked()) {
                    for (int i = 0; i < mCts.getSize(); i++) {
                        mCts.setChecked(i);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        mCbNone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (mCbNone.isChecked()) {
                    for (int i = 0; i < mCts.getSize(); i++) {
                        mCts.setUnChecked(i);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }
        });

        mBtnConfirmContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();

                ArrayList<HashMap<String, String>> ctsData = mCts.getCtsList();


                ArrayList<HashMap<String, String>> checkedCtsData = new ArrayList<HashMap<String, String>>();

                boolean[] isCheckedList = mAdapter.getCheckedList();

                for(int i = 0;i<mCts.getSize();i++){
                    if(isCheckedList[i] == true){
                        checkedCtsData.add(ctsData.get(i));
                    }
                }

                bundle.putSerializable("checkedCtsData", checkedCtsData);

                Intent intent = new Intent();
                intent.putExtras(bundle);
                ContactsActivity.this.setResult(1,intent);

                ContactsActivity.this.finish();
            }
        });

        mAdapter = new ContactsAdapter(this, mCts);
        mRlvCts.setAdapter(mAdapter);
        mLlMgr = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRlvCts.setLayoutManager(mLlMgr);

        Button btn = (Button) mCtContacts.findViewById(R.id.btn_left);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContactsActivity.this.finish();
            }
        });
    }

    public ArrayList<HashMap<String, String>> readContacts() {
        Cursor cursor = null;
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();

        try {
            cursor = getContentResolver().query(
                    ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
            while (cursor.moveToNext()) {
                HashMap<String, String> map = new HashMap<String, String>();
                String name = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String number = cursor.getString(cursor.getColumnIndex(
                        ContactsContract.CommonDataKinds.Phone.NUMBER));
                map.put("name", name);
                map.put("phone", number);
                list.add(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
        return list;
    }
}
