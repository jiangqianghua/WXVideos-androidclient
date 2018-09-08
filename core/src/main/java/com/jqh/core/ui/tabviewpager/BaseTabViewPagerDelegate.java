package com.jqh.core.ui.tabviewpager;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.jqh.core.R;
import com.jqh.core.deletegates.JqhDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class BaseTabViewPagerDelegate extends JqhDelegate {

    private final ArrayList<TabViewPagerItemDelegate> ITEM_DELEGES = new ArrayList<>();
    private final ArrayList<TabViewPagerBean> TAB_BEAN = new ArrayList<>();
    private final LinkedHashMap<TabViewPagerBean,TabViewPagerItemDelegate> ITEMS = new LinkedHashMap<>();

    private int mSelectColor = Color.GREEN;

    private int mNormalColor = Color.BLACK;

    private int mTabBackground = Color.WHITE ;

    private TabLayout mTabLayout;

    private ViewPager mViewPager ;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(setSelectColor() != 0){
            mSelectColor = setSelectColor();
        }

        if(setNormalColor() != 0){
            mNormalColor = setNormalColor();
        }

        if (setTabBackGround() != 0){
            mTabBackground = setTabBackGround();
        }

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<TabViewPagerBean,TabViewPagerItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for(Map.Entry<TabViewPagerBean,TabViewPagerItemDelegate> item:ITEMS.entrySet()){
            final TabViewPagerBean key = item.getKey();
            final TabViewPagerItemDelegate value = item.getValue();
            TAB_BEAN.add(key);
            ITEM_DELEGES.add(value);
        }

    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_tabviewpager;
    }


    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        // 设置tab
        mTabLayout = rootView.findViewById(R.id.tab_layout);
        mViewPager = rootView.findViewById(R.id.view_pager);

        initPager();

        initTabLayout();
    }

    private void initTabLayout(){
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor(mSelectColor);
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabTextColors(mNormalColor,mSelectColor);
        mTabLayout.post(new Runnable() {
            @Override
            public void run() {
                TabUtils.setIndicator(mTabLayout,35,35);
            }
        });

        mTabLayout.setBackgroundColor(mTabBackground);


    }

    private void initPager(){
        final PagerAdapter adapter = new TabPagerAdapter(getFragmentManager(),TAB_BEAN,ITEM_DELEGES);
        mViewPager.setAdapter(adapter);
    }

    public abstract LinkedHashMap<TabViewPagerBean,TabViewPagerItemDelegate> setItems(ItemBuilder builder);

    protected abstract int setSelectColor();

    protected abstract int setNormalColor();

    protected abstract  int setTabBackGround();
}
