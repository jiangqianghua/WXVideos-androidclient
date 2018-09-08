package com.jqh.wxvideo.delegate.video.recommend;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.IError;
import com.jqh.core.net.calback.IFailure;
import com.jqh.core.net.calback.ISuccess;
import com.jqh.core.recycler.BaseDecoration;
import com.jqh.core.ui.tabviewpager.TabViewPagerItemDelegate;
import com.jqh.wxvideo.R;

public class RecommentVideosDelegate extends TabViewPagerItemDelegate{

    private RecyclerView mRecyclerView ;

    @Override
    public Object setLayout() {
        return R.layout.delegate_recommend_videos;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rv_recommend_videos_list);
        // 瀑布模式
        //GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
       // manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        mRecyclerView.setLayoutManager(manager);

        // 2行，边宽2
     //   mRecyclerView.addItemDecoration(new BaseDecoration(Color.BLACK,10));


    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        RestClient.builder()
                .loader(getContext())
                .url("video/showAll?page=0")
                .params("userId","")
                .params("videoDesc","")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        RecommentVideosAdapter adapter = new RecommentVideosAdapter(new RecommentVideosDataConverter().setJsonData(response).convert());
                        mRecyclerView.setAdapter(adapter);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {

                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {

                    }
                })
                .build().postJson();
    }
}
