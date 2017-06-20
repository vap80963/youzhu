package com.zionstudio.youzhu.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/19 0019.
 */

public class FriendLabelList {
    private String type;
    private String statusCode;
    private List<Label> friendLabelList;

    private int test;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return statusCode;
    }

    public void setCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public List<Label> getFriendLabelList() {
        return friendLabelList;
    }

    public void setFriendLabelList(List<Label> friendLabelList) {
        this.friendLabelList = friendLabelList;
    }
}
