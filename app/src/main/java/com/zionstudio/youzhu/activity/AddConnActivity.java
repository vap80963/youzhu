package com.zionstudio.youzhu.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.ui.CommonTop;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/11/11 0011.
 */

public class AddConnActivity extends Activity {

    @Bind(R.id.ct_add_conn)
    CommonTop mCtAddConn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_conn);
        ButterKnife.bind(this);
        initData();
        initView();
    }

    private void initView() {
        Button btn = (Button) mCtAddConn.findViewById(R.id.btn_left);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddConnActivity.this.finish();
            }
        });
    }

    private void initData() {
    }

}
