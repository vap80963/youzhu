package com.zionstudio.youzhu.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class UserInfo {
    private int userID;
    private String friendNum;
    private String friendName;
    private String friendSex;
    private Object friendAddress;
    private int type;
    private List<MyLabel> LabelList;

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getFriendNum() {
        return friendNum;
    }

    public void setFriendNum(String friendNum) {
        this.friendNum = friendNum;
    }

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendSex() {
        return friendSex;
    }

    public void setFriendSex(String friendSex) {
        this.friendSex = friendSex;
    }

    public Object getFriendAddress() {
        return friendAddress;
    }

    public void setFriendAddress(Object friendAddress) {
        this.friendAddress = friendAddress;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<MyLabel> getLabelList() {
        return LabelList;
    }

    public void setLabelList(List<MyLabel> labelList) {
        LabelList = labelList;
    }
}
