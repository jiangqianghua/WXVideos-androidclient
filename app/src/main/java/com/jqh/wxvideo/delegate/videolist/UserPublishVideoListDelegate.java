package com.jqh.wxvideo.delegate.videolist;

import android.os.Bundle;

import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.ISuccess;

public class UserPublishVideoListDelegate extends BaseUserVideoListDelegate {

    public static BaseUserVideoListDelegate getInstance(String publishId){
        Bundle args = new Bundle();
        args.putString(VIDEO_PUBLISH_ID_KEY,publishId);
        final BaseUserVideoListDelegate delegate = new UserPublishVideoListDelegate();
        delegate.setArguments(args);
        return delegate ;
    }

    @Override
    protected void loadVideoData(final int pageIndex,final boolean isRefresh) {
        RestClient.builder()
                .url("video/showAll?page="+pageIndex)
                .params("userId",userId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                       loadDataFinished(response,isRefresh);
                    }
                })
                .build()
                .postJson();
    }


}
