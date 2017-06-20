package com.zionstudio.youzhu.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.zionstudio.youzhu.R;

import java.io.Serializable;

/**
 * Created by Administrator on 2016/11/10 0010.
 */

public class PersonInfo implements Serializable {
    private int mImageUri = -1;
    private int userID;

    private String friendName = "";
    private String mEmail = "";
    private String friendSex = "";
    private String friendNum;

    private String friendAddress;

    private String loginPhoneNum;

    private int Num = -1;

    public String getFriendAddress() {
        return friendAddress;
    }

    public void setFriendAddress(String friendAddress) {
        this.friendAddress = friendAddress;
    }

    public int getNum() {
        return Num;
    }

    public void setNum(int num) {
        Num = num;
    }

    public String getLoginPhoneNum() {
        return loginPhoneNum;
    }

    public void setLoginPhoneNum(String loginPhoneNum) {
        this.loginPhoneNum = loginPhoneNum;
    }

    public PersonInfo(int imageUri, String name, String number) {
        this.mImageUri = imageUri;
        this.friendName = name;
        this.friendNum = number;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getSex() {
        return friendSex;
    }

    public void setSex(String sex) {
        friendSex = sex;
    }

    public String getNumber() {
        return friendNum;
    }

    public void setNumber(String number) {
        friendNum = number;
    }

    public int getImageUri() {
        return mImageUri;
    }

    public void setImageUri(int imageUri) {
        mImageUri = imageUri;
    }

    public String getName() {
        return friendName;
    }

    public void setName(String name) {
        friendName = name;
    }

}
