package com.jqh.wxvideo.delegate.videolist;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TabPagerUserVideoAdapter extends FragmentStatePagerAdapter {
    private final ArrayList<String> TAB_TITLES = new ArrayList<>();
    private String userId ;
    public TabPagerUserVideoAdapter(FragmentManager fm, String userId) {
        super(fm);
        TAB_TITLES.add("作品");
        TAB_TITLES.add("收藏");
       // TAB_TITLES.add("关注");
        this.userId = userId ;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return UserPublishVideoListDelegate.getInstance(userId);
        } else if (position == 1) {
            return UserLikeVideoListDelegate.getInstance(userId);
        }
        return null ;
//        else{
//            return UserFollowVideoListDelegate.getInstance(userId);
//        }
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