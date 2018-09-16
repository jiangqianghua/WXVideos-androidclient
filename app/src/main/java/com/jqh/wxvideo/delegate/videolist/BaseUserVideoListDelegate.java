package com.jqh.wxvideo.delegate.videolist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.refresh.RefreshHandlerImpro;
import com.jqh.wxvideo.R;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

public abstract class BaseUserVideoListDelegate extends JqhDelegate implements RefreshHandlerImpro.OnRefreshHandlerImproListener {
    protected String userId ;

    private RecyclerView recyclerView ;
    private UserVideoTabItemAdapter adapter ;

    private RefreshHandlerImpro refreshHandlerImpro ;
    private SmartRefreshLayout smartRefreshLayout ;

    protected static final String VIDEO_PUBLISH_ID_KEY = "VIDEO_PUBLISH_ID_KEY";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        userId = args.getString(VIDEO_PUBLISH_ID_KEY);
    }


    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        recyclerView = rootView.findViewById(R.id.rv_videos_list);

        GridLayoutManager manager = new GridLayoutManager(getContext(),3);
        recyclerView.setLayoutManager(manager);

        adapter = new UserVideoTabItemAdapter(new ArrayList<MultipleItemEntity>());
        recyclerView.setAdapter(adapter);

        smartRefreshLayout = rootView.findViewById(R.id.refreshLayout_videodata);
        refreshHandlerImpro = new RefreshHandlerImpro(smartRefreshLayout);
        refreshHandlerImpro.setOnRefreshHandlerListener(this);
        refreshHandlerImpro.firstPage();
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_minevideo_tabitem;
    }
    /**
     * 加载视频数据
     */
    protected abstract void loadVideoData(int pageIndex,boolean isRefresh);

    /**
     * 更新adater数据
     * @param response
     */
    protected void loadDataFinished(String response,boolean isRefresh){
        refreshHandlerImpro.onLoadFinish(response,isRefresh);
    }

    @Override
    public void onRefreshData(String response) {
        JSONObject jsonObject = JSON.parseObject(response).getJSONObject("data");
        int total = jsonObject.getInteger("total");
        int records = jsonObject.getInteger("records");
        adapter.setNewData(new UserVideoDataConverter().setJsonData(response).convert());
        refreshHandlerImpro.setPageInfo(records,total);
        refreshHandlerImpro.setCurCount(adapter.getData().size());
    }

    @Override
    public void onLoadMoreData(String response) {
        adapter.addData(new UserVideoDataConverter().setJsonData(response).convert());
        refreshHandlerImpro.setCurCount(adapter.getData().size());
    }

    @Override
    public void onLoadData(int curPageIndex, boolean isRefresh) {
        loadVideoData(curPageIndex,isRefresh);
    }


}
