package com.zionstudio.youzhu.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.bean.StatusEntity;

import java.util.List;

/**
 * Created by hasee on 11/12/2016.
 */
public class HomeSquareAdapater extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

	private Context mContext;
	private List<StatusEntity> mSquareList;
	private LayoutInflater mInflater;

	public HomeSquareAdapater(Context mContext, List<StatusEntity> mSquareList) {
		this.mContext = mContext;
		this.mSquareList = mSquareList;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View view = view = mInflater.inflate(R.layout.fragment_square_item,parent,false);
		return new HomeSquareHolder(view);
	}

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
		if(holder instanceof HomeSquareHolder){
			HomeSquareHolder squareHolder = (HomeSquareHolder) holder;
			squareHolder.fgContactsContent.setText(mSquareList.get(position).text);
			squareHolder.vpSquarePortrait.setImageResource(mSquareList.get(position).head_pic_id);
			if(0 != mSquareList.get(position).content_pic_id) {
				squareHolder.fgContactsImg.setVisibility(View.VISIBLE);
				squareHolder.fgContactsImg.setImageResource(mSquareList.get(position).content_pic_id);
			}
			squareHolder.fgContactsUsername.setText(mSquareList.get(position).source);
			squareHolder.fgContactsTime.setText(mSquareList.get(position).created_at);

		}

	}

	@Override
	public int getItemCount() {
		return mSquareList.size();
	}


	class HomeSquareHolder extends RecyclerView.ViewHolder{

		private ImageButton vpSquarePortrait;
		private TextView fgContactsUsername;
		private TextView fgContactsTime;
		private TextView fgContactsContent;
		private ImageView fgContactsImg;


		public HomeSquareHolder(View itemView) {
			super(itemView);
			vpSquarePortrait = (ImageButton) itemView.findViewById(R.id.vp_square_portrait);
			fgContactsUsername = (TextView) itemView.findViewById(R.id.fg_contacts_username);
			fgContactsTime = (TextView) itemView.findViewById(R.id.fg_contacts_time);
			fgContactsContent = (TextView) itemView.findViewById(R.id.fg_contacts_content);
			fgContactsImg = (ImageView) itemView.findViewById(R.id.fg_contacts_img);
		}
	}
}
