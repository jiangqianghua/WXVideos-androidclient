package com.jqh.wxvideo.delegate.mine.tab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;

import com.jqh.core.ui.tabviewpager.BaseTabViewPagerDelegate;
import com.jqh.core.ui.tabviewpager.ItemBuilder;
import com.jqh.core.ui.tabviewpager.TabViewPagerBean;
import com.jqh.core.ui.tabviewpager.TabViewPagerItemDelegate;
import com.jqh.wxvideo.R;

import java.util.LinkedHashMap;

public class MineTabsDelegate extends BaseTabViewPagerDelegate {

    private static final String PUBLISH_ID = "PUBLISH_ID";
    private String publishId ;
    public static MineTabsDelegate getInstance(String publishId){
        Bundle args = new Bundle();
        MineTabsDelegate delegate = new MineTabsDelegate();
        args.putString(PUBLISH_ID,publishId);
        delegate.setArguments(args);
        return delegate ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        publishId = args.getString(PUBLISH_ID);
        super.onCreate(savedInstanceState);
    }

    @Override
    public LinkedHashMap<TabViewPagerBean, TabViewPagerItemDelegate> setItems(ItemBuilder builder) {
        builder.addItem(new TabViewPagerBean("作品"),MineVideoTabItemDelegate.getInstance(MineVideoTabItemDelegate.VIDEO_TYPE_MINE,publishId))
                .addItem(new TabViewPagerBean("收藏"),MineVideoTabItemDelegate.getInstance(MineVideoTabItemDelegate.VIDEO_TYPE_LIKE,publishId))
                ;//.addItem(new TabViewPagerBean("关注"),MineVideoTabItemDelegate.getInstance(MineVideoTabItemDelegate.VIDEO_TYPE_FOLLOW,publishId));
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
