package com.jqh.core.refresh;

import android.support.annotation.NonNull;

import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.ISuccess;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class RefreshHandlerImpro implements OnRefreshListener,OnLoadMoreListener {

    private final PageBean BEAN ;

    private final SmartRefreshLayout REFRESHLAYOUT ;

    public interface OnRefreshHandlerImproListener{
        void onRefreshData(String response);
        void onLoadMoreData(String response);
        void onLoadData(int curPageIndex,boolean isRefresh);
    }

    public void setPageInfo(int total , int pageSize){
        BEAN.setTotal(total);
        BEAN.setPageSize(pageSize);
    }

    public void setCurCount(int count){
        BEAN.setCurrentCount(count);
    }
    private OnRefreshHandlerImproListener onRefreshHandlerListener;

    public void setOnRefreshHandlerListener(OnRefreshHandlerImproListener onRefreshHandlerListener) {
        this.onRefreshHandlerListener = onRefreshHandlerListener;
    }

    public RefreshHandlerImpro(SmartRefreshLayout smartRefreshLayout) {
        this.BEAN = new PageBean();
        this.REFRESHLAYOUT = smartRefreshLayout;
        REFRESHLAYOUT.setOnLoadMoreListener(this);
        REFRESHLAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandlerImpro create(SmartRefreshLayout smartRefreshLayout){
        return new RefreshHandlerImpro(smartRefreshLayout);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        onPageing();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        // 重新加载数据
        firstPage();
    }

    /**
     * 第一次加载调用
     */
    public void firstPage(){
        BEAN.setCurrentCount(0);
        REFRESHLAYOUT.setEnableLoadMore(true);
        BEAN.setPageIndex(0);
        if(onRefreshHandlerListener != null)
            onRefreshHandlerListener.onLoadData((BEAN.getPageIndex()+1),true);
    }

    private void onPageing(){
        final int pageSize  = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();


        if( currentCount < pageSize || currentCount >= total){
            REFRESHLAYOUT.finishLoadMore(1/*,false*/);
            REFRESHLAYOUT.setEnableLoadMore(false);
        }
        else {
            if(onRefreshHandlerListener != null)
                onRefreshHandlerListener.onLoadData((BEAN.getPageIndex()+1),false);
        }
    }

    public void onLoadFinish(String response,boolean isRefresh){
        if(!isRefresh){
            onRefreshHandlerListener.onLoadMoreData(response);
            REFRESHLAYOUT.finishLoadMore(100/*,false*/);
        }else{
            onRefreshHandlerListener.onRefreshData(response);
            REFRESHLAYOUT.finishRefresh(100/*,false*/);
        }
        BEAN.addIndex();

    }
}
