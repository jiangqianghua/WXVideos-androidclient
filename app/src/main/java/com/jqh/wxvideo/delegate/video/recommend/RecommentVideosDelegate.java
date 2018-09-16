package com.jqh.wxvideo.delegate.video.recommend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.SimpleClickListener;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.refresh.RefreshHandler;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.video.videoplayer.VideoPlayerDelegate;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;
import java.util.List;

public class RecommentVideosDelegate extends JqhDelegate implements RefreshHandler.OnRefreshHandlerListener{

    private RecyclerView mRecyclerView ;

    private RefreshHandler refreshHandler ;

    private SmartRefreshLayout smartRefreshLayout ;
    private RecommentVideosAdapter adapter ;


    @Override
    public Object setLayout() {
        return R.layout.delegate_recommend_videos;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mRecyclerView = rootView.findViewById(R.id.rv_recommend_videos_list);
        // 瀑布模式
       // GridLayoutManager manager = new GridLayoutManager(getContext(),2);
        final StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        manager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);

        mRecyclerView.setLayoutManager(manager);
        // 2行，边宽2
     //   mRecyclerView.addItemDecoration(new BaseDecoration(Color.BLACK,10));

        smartRefreshLayout = rootView.findViewById(R.id.refreshLayout);
        refreshHandler = RefreshHandler.create(smartRefreshLayout);
        refreshHandler.setOnRefreshHandlerListener(this);

        adapter = new RecommentVideosAdapter(new ArrayList<MultipleItemEntity>());
        mRecyclerView.setAdapter(adapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                manager.invalidateSpanAssignments();
            }
        });

        mRecyclerView.addOnItemTouchListener(new SimpleClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                RecommentVideosDelegate.this.getParentDelegate().getParentDelegate().start(new VideoPlayerDelegate());
            }

            @Override
            public void onItemLongClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }

            @Override
            public void onItemChildLongClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        refreshHandler.firstPage("video/showAll?page=");

    }

    @Override
    public void onRefreshData(String response) {
        JSONObject jsonObject = JSON.parseObject(response).getJSONObject("data");
        int total = jsonObject.getInteger("total");
        int records = jsonObject.getInteger("records");
//        adapter = new RecommentVideosAdapter(new RecommentVideosDataConverter().setJsonData(response).convert());
//        mRecyclerView.setAdapter(adapter);
        ArrayList<MultipleItemEntity> data = new RecommentVideosDataConverter().setJsonData(response).convert() ;
       // adapter.replaceData(data);
        adapter.setNewData(data);
        refreshHandler.setPageInfo(records,total);
        refreshHandler.setCurCount(adapter.getData().size());
    }

    @Override
    public void onLoadMoreData(String response) {
        final List<MultipleItemEntity> data = new RecommentVideosDataConverter().setJsonData(response).convert();
        adapter.addData(data);
        refreshHandler.setCurCount(adapter.getData().size());
    }
}
