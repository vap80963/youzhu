package com.zionstudio.youzhu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.bean.PersonInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/11/9 0009.
 */

public class ConnectionAdapter extends RecyclerView.Adapter<ViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<PersonInfo> mDatas;
    private MyItemClickListener mItemClickListener;
    private MyItemLongClickListener mItemLongClickListener;

    private int touxiangList[] = new int[]{R.drawable.touxiang, R.drawable.touxiang2, R.drawable.touxiang3,
            R.drawable.touxiang4, R.drawable.touxiang5, R.drawable.touxiang6};

    public ConnectionAdapter(Context context, List<PersonInfo> datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_conn, parent, false);
        MyViewHolder viewHolder = new MyViewHolder(view, mItemClickListener, mItemLongClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ((MyViewHolder) holder).tv.setText(mDatas.get(position).getName());

        ((MyViewHolder) holder).sdv.setImageResource(touxiangList[position % touxiangList.length]);
    }

    public void setOnClickItemListener(MyItemClickListener listener) {
        this.mItemClickListener = listener;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tv;
        ImageView sdv;
        private MyItemClickListener mListener = null;
        private MyItemLongClickListener myLongClickListener = null;

        public MyViewHolder(View itemView, MyItemClickListener listener, MyItemLongClickListener longClickListener) {
            super(itemView);
            tv = (TextView) itemView.findViewById(R.id.tv_name_conn);
            sdv = (ImageView) itemView.findViewById(R.id.sdv_touxiang_conn);

            this.mListener = listener;
            this.myLongClickListener = longClickListener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (null != mListener) {
                        mListener.onItemClick(view, getLayoutPosition());
                    }
                }
            });
//			itemView.setOnLongClickListener(this);
        }
    }

    public interface MyItemClickListener {
        void onItemClick(View view, int position);
    }

    public interface MyItemLongClickListener {
        void onItemLongClick(View view, int position);
    }
}

