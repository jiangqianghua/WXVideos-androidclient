package com.jqh.wxvideo.delegate.video.follow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.wxvideo.R;

public class FollowVideosDelegate extends JqhDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_follow_videos;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
