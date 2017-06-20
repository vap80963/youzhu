package com.zionstudio.youzhu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.bean.Cts;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.zionstudio.youzhu.MyApplication.iconList;

/**
 * Created by Administrator on 2016/11/12 0012.
 */

public class ContactsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater mInflater;
    private Context mContext;
    private List<HashMap<String, String>> datas;
    private List<Integer> mList;
    private Cts mCts;
    private boolean[] isCheckedList;

 //   List<HashMap<String, String>> datas

    public ContactsAdapter(Context context, Cts cts) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
//        this.datas = datas;
        this.mCts = cts;
        this.datas = cts.getCtsList();
        isCheckedList = cts.getIsChecked();
        this.mList = new ArrayList<>();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_contacts, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((MyViewHolder) holder).tv.setText(datas.get(position).get("name"));
        ((MyViewHolder) holder).iv.setImageResource(iconList[position % iconList.length]);
        ((MyViewHolder) holder).cb.setTag(position);
        ((MyViewHolder) holder).cb.setChecked(isCheckedList[position]);
        ((MyViewHolder) holder).cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Integer tag = (Integer) buttonView.getTag();
                if(isChecked){
                    isCheckedList[tag] = true;
                } else {
                    isCheckedList[tag] = false;
                }
            }
        });
//
//        if (mList != null) {
//            ((MyViewHolder) holder).cb.setChecked(mList.contains(new Integer(position)) ? true : false);
//        } else {
//            ((MyViewHolder) holder).cb.setChecked(false);
//        }
//        ((MyViewHolder) holder).cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                Integer tag = (Integer) buttonView.getTag();
//                if (isChecked) {
//                    if (!mList.contains(tag)) {
//                        mList.add(tag);
//                    }
//                } else {
//                    if (mList.contains(tag)) {
//                        mList.remove(tag);
//                    }
//                }
//            }
//        });
    }

    public boolean[] getCheckedList(){
        return isCheckedList;
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        private ImageView iv;
        private TextView tv;
        private CheckBox cb;
        private View view;

        public MyViewHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.iv_item_cts);
            tv = (TextView) itemView.findViewById(R.id.tv_item_cts);
            cb = (CheckBox) itemView.findViewById(R.id.cb_item_cts);
            view = itemView.findViewById(R.id.view_item_cts);
        }
    }
}
