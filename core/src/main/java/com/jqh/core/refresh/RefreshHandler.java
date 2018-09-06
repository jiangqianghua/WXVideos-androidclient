package com.jqh.core.refresh;

import android.support.annotation.NonNull;

import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.ISuccess;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

public class RefreshHandler implements OnRefreshListener,OnLoadMoreListener {

    private final PageBean BEAN ;

    private final SmartRefreshLayout REFRESHLAYOUT ;

    private String url ;

    public interface OnRefreshHandlerListener{
        void onRefreshData(String response);
        void onLoadMoreData(String response);
    }

    public void setPageInfo(int total , int pageSize){
        BEAN.setTotal(total);
        BEAN.setPageSize(pageSize);
    }

    public void setCurCount(int count){
        BEAN.setCurrentCount(count);
    }
    private OnRefreshHandlerListener onRefreshHandlerListener;

    public void setOnRefreshHandlerListener(OnRefreshHandlerListener onRefreshHandlerListener) {
        this.onRefreshHandlerListener = onRefreshHandlerListener;
    }

    public RefreshHandler( SmartRefreshLayout smartRefreshLayout) {
        this.BEAN = new PageBean();
        this.REFRESHLAYOUT = smartRefreshLayout;
        REFRESHLAYOUT.setOnLoadMoreListener(this);
        REFRESHLAYOUT.setOnRefreshListener(this);
    }

    public static RefreshHandler create(SmartRefreshLayout smartRefreshLayout){
        return new RefreshHandler(smartRefreshLayout);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        onPageing(url);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        // 重新加载数据
        firstPage(url);
    }

    /**
     * 第一次加载调用
     * @param url
     */
    public void firstPage(String url){
        BEAN.setCurrentCount(0);
        REFRESHLAYOUT.setEnableLoadMore(true);
        this.url = url ;
        BEAN.setPageIndex(0);

        RestClient.builder()
                .url(url+""+(BEAN.getPageIndex()+1))
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        onRefreshHandlerListener.onRefreshData(response);
                        BEAN.addIndex();
                        REFRESHLAYOUT.finishRefresh(100/*,false*/);
                    }
                })
                .build()
                .get();
    }

    private void onPageing(String url){
        final int pageSize  = BEAN.getPageSize();
        final int currentCount = BEAN.getCurrentCount();
        final int total = BEAN.getTotal();


        if( currentCount < pageSize || currentCount >= total){
            REFRESHLAYOUT.finishLoadMore(1/*,false*/);
            REFRESHLAYOUT.setEnableLoadMore(false);
        }
        else {
            RestClient.builder()
                    .url(url + "" + (BEAN.getPageIndex() + 1))
                    .success(new ISuccess() {
                        @Override
                        public void onSuccess(String response) {
                            onRefreshHandlerListener.onLoadMoreData(response);
                            BEAN.addIndex();
                            REFRESHLAYOUT.finishLoadMore(100/*,false*/);
                        }
                    })
                    .build()
                    .get();
        }
    }
}
