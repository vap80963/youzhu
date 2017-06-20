package com.zionstudio.youzhu;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.drawee.backends.pipeline.Fresco;


/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class MyApplication extends Application {

    public static String localCookie;
    public static SharedPreferences sp;

    public static final int[] iconList = {R.drawable.person1, R.drawable.person5, R.drawable.person3,
            R.drawable.person4, R.drawable.person2, R.drawable.person6};

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        sp = getSharedPreferences("net", MODE_PRIVATE);
    }

    public Context getMyContext(){
        return getApplicationContext();
    }

}
