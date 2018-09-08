package com.jqh.wxvideo.delegate.bottom;

import android.graphics.Color;
import android.support.v4.content.ContextCompat;

import com.jqh.core.bottom.BaseBottomDelegate;
import com.jqh.core.bottom.BottomItemDelegate;
import com.jqh.core.bottom.BottomTabBean;
import com.jqh.core.bottom.ItemBuilder;
import com.jqh.core.util.log.JqhLogger;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.LauncherDelegate;
import com.jqh.wxvideo.delegate.home.HomeDelegate;
import com.jqh.wxvideo.delegate.video.VideosDelegate;

import java.util.LinkedHashMap;

public class BottomDelegate extends BaseBottomDelegate {
    @Override
    public LinkedHashMap<BottomTabBean, BottomItemDelegate> setItems(ItemBuilder builder) {
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items = new LinkedHashMap<>();
        items.put(new BottomTabBean("{fa-home}","直播"),new HomeDelegate());
        items.put(new BottomTabBean("{fa-home}","视频"),new VideosDelegate());
      //  items.put(new BottomTabBean(R.mipmap.plus),new LauncherDelegate());
        items.put(new BottomTabBean("{fa-home}","消息"),new LauncherDelegate());
        items.put(new BottomTabBean("{fa-home}","我的"),new LauncherDelegate());
        //items.put(new BottomTabBean("{fa-home}","我的"),new LauncherDelegate());
        return builder.addItems(items).build();
    }

    @Override
    public int setIndexDelegate() {
        return 0;
    }

    @Override
    public int setClickedColor() {
        return ContextCompat.getColor(getContext(),R.color.app_main);
    }

    @Override
    protected void onItemClick(int itemIndex) {
        JqhLogger.d("BottomDelegate[onItemClick]",itemIndex);
    }
}
