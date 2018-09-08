package com.jqh.wxvideo.delegate.video.follow;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jqh.core.ui.tabviewpager.TabViewPagerItemDelegate;
import com.jqh.wxvideo.R;

public class FollowVideosDelegate extends TabViewPagerItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_follow_videos;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
