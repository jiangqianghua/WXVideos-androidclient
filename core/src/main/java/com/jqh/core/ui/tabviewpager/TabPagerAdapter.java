package com.jqh.core.ui.tabviewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class TabPagerAdapter extends FragmentStatePagerAdapter {

    private final ArrayList<TabViewPagerBean> TAB_TITLES ;
    private final ArrayList<TabViewPagerItemDelegate> TAB_FRAMES ;
    public TabPagerAdapter(FragmentManager fm, ArrayList<TabViewPagerBean> tabBeans, ArrayList<TabViewPagerItemDelegate> tabViewPagerItemDelegates) {
        super(fm);
        TAB_TITLES = tabBeans;
        TAB_FRAMES = tabViewPagerItemDelegates ;
    }

    @Override
    public Fragment getItem(int position) {
        return TAB_FRAMES.get(position);
    }

    @Override
    public int getCount() {
        return TAB_TITLES.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return TAB_TITLES.get(position).getTITLE();
    }
}
