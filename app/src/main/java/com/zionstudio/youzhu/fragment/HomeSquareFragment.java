package com.zionstudio.youzhu.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.zionstudio.youzhu.R;
import com.zionstudio.youzhu.adapter.HomeSquareAdapater;
import com.zionstudio.youzhu.bean.StatusEntity;
import com.zionstudio.youzhu.ui.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hasee on 11/12/2016.
 */
public class HomeSquareFragment extends Fragment {


    private RecyclerView squareRclView = null;
    private RecyclerView.LayoutManager mLayoutManager;
    private DividerItemDecoration mDiverItemDecoration;
    StatusEntity mStatusEntity;
    private List<StatusEntity> mSquareList;
    private HomeSquareAdapater mSquareAdapter;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        mSquareAdapter = new HomeSquareAdapater(getActivity(), mSquareList);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if ( squareRclView == null) {
            squareRclView = (RecyclerView) inflater.inflate(R.layout.fragment_square, container, false);
            init();
        }
        ViewGroup parent = (ViewGroup) squareRclView.getParent();
        if (parent != null){
            parent.removeView(squareRclView);
        }

        return squareRclView;
    }

    private void initData() {
        Log.e("TAG", "开始初始化数据 》》》》》》》》》》mSquareList");
        mSquareList = new ArrayList<>();
        mStatusEntity = new StatusEntity();
        mStatusEntity.created_at = "5分钟前";
        mStatusEntity.source = "电脑小白";
        mStatusEntity.head_pic_id = R.drawable.head_pic6;
        mStatusEntity.content_pic_id = R.drawable.content_pic;
        mStatusEntity.text = "求问哪位大佬会修电脑啊，电脑系统出问题了，不会装系统啊，快来人救朕，送上香浓咖啡一份";
        mStatusEntity.comments_count = 10;
        mStatusEntity.favorited = true;
        mSquareList.add(mStatusEntity);

        mStatusEntity = new StatusEntity();
        mStatusEntity.created_at = "半个钟前";
        mStatusEntity.source = "山地爱好者";
        mStatusEntity.head_pic_id = R.drawable.head_pic4;
        mStatusEntity.content_pic_id = R.drawable.content_pic2;
        mStatusEntity.text = "今天想去骑车，不过不知道哪位爱骑车，爱骑车的童鞋加我吧，我们以后一起啊";
        mStatusEntity.comments_count = 10;
        mStatusEntity.favorited = true;
        mSquareList.add(mStatusEntity);

        mStatusEntity = new StatusEntity();
        mStatusEntity.created_at = "1个钟前";
        mStatusEntity.source = "迷茫的大班长";
        mStatusEntity.head_pic_id = R.drawable.head_pic5;
        mStatusEntity.content_pic_id = R.drawable.content_pic1;
        mStatusEntity.text = "女生节活动不知道怎么设计好啊，来个有经验的大神吧,去麦当劳讨论，我请客";
        mStatusEntity.comments_count = 10;
        mStatusEntity.favorited = true;
        mSquareList.add(mStatusEntity);

        mStatusEntity = new StatusEntity();
        mStatusEntity.created_at = "一个半钟前";
        mStatusEntity.source = "华为HR";
        mStatusEntity.head_pic_id = R.drawable.head_pic3;
        mStatusEntity.content_pic_id = R.drawable.content_pic3;
        mStatusEntity.text = "今年不知道怎么回事，安卓工程师居然招不够人啊，友圈的各位好友们，麻烦帮忙介绍一下你们认识的大牛给我啊，感激不尽";
        mStatusEntity.comments_count = 10;
        mStatusEntity.favorited = true;
        mSquareList.add(mStatusEntity);

        mStatusEntity = new StatusEntity();
        mStatusEntity.created_at = "两个半钟前";
        mStatusEntity.source = "当世达芬奇";
        mStatusEntity.head_pic_id = R.drawable.head_pic7;
        mStatusEntity.content_pic_id = R.drawable.content_pic4;
        mStatusEntity.text = "高手就是寂寞，枉费我花费那么多年苦学画画，自认也不输达芬奇多少，却不知从何处去找我的知己，唉~";
        mStatusEntity.comments_count = 10;
        mStatusEntity.favorited = true;
        mSquareList.add(mStatusEntity);

        Log.e("TAG", "mSquareList = " + mSquareList.get(0));
    }

    private void init() {
        Context mContext = getActivity();
        mLayoutManager = new LinearLayoutManager(mContext);
        squareRclView.setLayoutManager(mLayoutManager);
        mDiverItemDecoration = new DividerItemDecoration(mContext, LinearLayoutManager.VERTICAL);
        squareRclView.addItemDecoration(mDiverItemDecoration);
        squareRclView.setAdapter(mSquareAdapter);
    }
}
