package com.jqh.wxvideo.delegate.video;

import android.support.v4.content.ContextCompat;

import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.ui.tabviewpager.BaseTabViewPagerDelegate;
import com.jqh.core.ui.tabviewpager.ItemBuilder;
import com.jqh.core.ui.tabviewpager.TabViewPagerBean;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.video.follow.FollowVideosDelegate;
import com.jqh.wxvideo.delegate.video.recommend.RecommentVideosDelegate;

import java.util.LinkedHashMap;

public class VideosTabViewDelegate extends BaseTabViewPagerDelegate {
    @Override
    public LinkedHashMap<TabViewPagerBean, JqhDelegate> setItems(ItemBuilder builder) {
        builder.addItem(new TabViewPagerBean("推荐"),new RecommentVideosDelegate())
                .addItem(new TabViewPagerBean("关注"),new FollowVideosDelegate());
        return  builder.build();
    }

    @Override
    protected int setSelectColor() {
        return ContextCompat.getColor(getContext(), R.color.app_main);
    }

    @Override
    protected int setNormalColor() {
        return 0;
    }

    @Override
    protected int setTabBackGround() {
        return 0;
    }
}
