package com.zionstudio.youzhu.bean;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class MyInfo {
    private String type;
    private String statusCode;
    private UserInfo userInfo;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
