package com.zionstudio.youzhu.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/18 0018.
 */

public class FriendList {
    private String type;
    private String statusCode;
    private List<PersonInfo> friendList;
    private List<PersonInfo> friendLabelList;

    public List<PersonInfo> getFriendLabelList() {
        return friendLabelList;
    }

    public void setFriendLabelList(List<PersonInfo> friendLabelList) {
        this.friendLabelList = friendLabelList;
    }

    public List<PersonInfo> getFriendList() {
        return friendList;
    }

    public void setFriendList(List<PersonInfo> friendList) {
        this.friendList = friendList;
    }

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


}
