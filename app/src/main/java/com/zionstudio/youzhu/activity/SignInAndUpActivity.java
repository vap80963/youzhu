package com.zionstudio.youzhu.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.zionstudio.youzhu.R;

/**
 * Created by Administrator on 2016/11/7 0007.
 */

public class SignInAndUpActivity extends Activity {

    private static final int SIGNINACTIVITY_REQUESCODE = 1;
    private static final int SIGNIN_SUCCEEDED = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_signinandup);
    }

    public void signIn(View view){
        startActivityForResult(new Intent(this, SignInActivity.class), SIGNINACTIVITY_REQUESCODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            case SIGNINACTIVITY_REQUESCODE:
                if(resultCode == SIGNIN_SUCCEEDED){
                    this.finish();
                }
                break;
        }
    }
}
