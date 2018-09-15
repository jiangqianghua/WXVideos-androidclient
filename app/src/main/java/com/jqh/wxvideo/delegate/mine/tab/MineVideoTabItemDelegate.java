package com.jqh.wxvideo.delegate.mine.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.ISuccess;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.ui.tabviewpager.TabViewPagerItemDelegate;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.video.videoplayer.VideoDataConverter;

import java.util.ArrayList;

public class MineVideoTabItemDelegate extends TabViewPagerItemDelegate {

    public static final int VIDEO_TYPE_MINE = 0 ;
    public static final int VIDEO_TYPE_FOLLOW = 1 ;
    public static final int VIDEO_TYPE_LIKE = 2 ;
    private int videoType ;
    private String userId ;

    private RecyclerView recyclerView ;
    private MineVideoTabItemAdapter adapter ;

    private  static final String VIDEO_TYPE_KEY = "VIDEO_TYPE_KEY";

    private static final String VIDEO_PUBLISH_ID_KEY = "VIDEO_PUBLISH_ID_KEY";

    public static MineVideoTabItemDelegate getInstance(int type,String publishId){
        Bundle args = new Bundle();
        args.putInt(VIDEO_TYPE_KEY,type);
        args.putString(VIDEO_PUBLISH_ID_KEY,publishId);
        final MineVideoTabItemDelegate delegate = new MineVideoTabItemDelegate();
        delegate.setArguments(args);
        return delegate ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        videoType = args.getInt(VIDEO_TYPE_KEY);
        userId = args.getString(VIDEO_PUBLISH_ID_KEY);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_minevideo_tabitem;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        recyclerView = rootView.findViewById(R.id.rv_videos_list);

        GridLayoutManager manager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(manager);

        adapter = new MineVideoTabItemAdapter(new ArrayList<MultipleItemEntity>());
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        if(videoType == VIDEO_TYPE_MINE)
            loadUserVideos();
        else if(videoType == VIDEO_TYPE_FOLLOW)
            loadFollowVideos();
        else if(videoType == VIDEO_TYPE_LIKE)
            loadLikeVideos();
    }

    private void loadUserVideos(){
        RestClient.builder()
                .loader(getContext())
                .url("video/showAll")
                .headers("userId",userId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        adapter.addData(new MineVideoDataConverter().setJsonData(response).convert());
                    }
                })
                .build()
                .postJson();
    }

    private void loadLikeVideos(){
        RestClient.builder()
                .loader(getContext())
                .url("video/showMyLike?userId="+userId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        adapter.addData(new MineVideoDataConverter().setJsonData(response).convert());
                    }
                })
                .build()
                .postJson();
    }

    private void loadFollowVideos(){
        RestClient.builder()
                .loader(getContext())
                .url("video/showMyFollow?userId="+userId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        adapter.addData(new MineVideoDataConverter().setJsonData(response).convert());
                    }
                })
                .build()
                .postJson();
    }
}
