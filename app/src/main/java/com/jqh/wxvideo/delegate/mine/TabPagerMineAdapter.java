package com.jqh.wxvideo.delegate.mine;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jqh.core.recycler.MultipleItemEntity;

import java.util.ArrayList;

public class TabPagerMineAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<String> TAB_TITLES = new ArrayList<>();
    private String userId ;
    public TabPagerMineAdapter(FragmentManager fm,String userId) {
        super(fm);
        TAB_TITLES.add("作品");
        TAB_TITLES.add("收藏");
        TAB_TITLES.add("关注");
        this.userId = userId ;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return MineVideoTabItemDelegate.getInstance(MineVideoTabItemDelegate.VIDEO_TYPE_MINE,userId);
        } else if (position == 1) {
            return MineVideoTabItemDelegate.getInstance(MineVideoTabItemDelegate.VIDEO_TYPE_LIKE,userId);
        }else{
            return MineVideoTabItemDelegate.getInstance(MineVideoTabItemDelegate.VIDEO_TYPE_FOLLOW,userId);
        }
    }

    @Override
    public int getCount() {
        return TAB_TITLES.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position);
    }
}