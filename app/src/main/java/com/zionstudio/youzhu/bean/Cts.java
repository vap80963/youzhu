package com.zionstudio.youzhu.bean;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Administrator on 2016/11/17 0017.
 */

public class Cts {
    private boolean[] isChecked;
    private ArrayList<HashMap<String, String>> mCtsList;

    public boolean[] getIsChecked() {
        return isChecked;
    }

    public void setChecked(int position) {
        isChecked[position] = true;
    }

    public void setUnChecked(int position) {
        isChecked[position] = false;
    }

    public ArrayList<HashMap<String, String>> getCtsList() {
        return mCtsList;
    }

    public void setCtsList(ArrayList<HashMap<String, String>> ctsList) {
        mCtsList = ctsList;
        isChecked = new boolean[getSize()];
    }

    public int getSize() {
        return mCtsList.size();
    }
}
