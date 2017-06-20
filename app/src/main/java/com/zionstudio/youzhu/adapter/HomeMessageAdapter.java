package com.zionstudio.youzhu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zionstudio.youzhu.R;

import java.util.List;

/**
 * Created by hasee on 11/12/2016.
 */
public class HomeMessageAdapter extends RecyclerView.Adapter {

	private Context mContext;
	private List<String> mMessageList;
	private LayoutInflater mInflater;

	public HomeMessageAdapter(Context mContext, List<String> mMessageList) {
		this.mContext = mContext;
		this.mMessageList = mMessageList;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

		View view = mInflater.inflate(R.layout.fragment_message_item,parent,false);
		return new HomeMessageViewHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

	}

	@Override
	public int getItemCount() {
		return mMessageList.size();
	}

	class HomeMessageViewHolder extends RecyclerView.ViewHolder{

		public HomeMessageViewHolder(View itemView) {
			super(itemView);
		}
	}

}
