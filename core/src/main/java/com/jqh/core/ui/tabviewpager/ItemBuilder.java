package com.jqh.core.ui.tabviewpager;

import com.jqh.core.deletegates.JqhDelegate;

import java.util.LinkedHashMap;

public final class ItemBuilder {
    private final LinkedHashMap<TabViewPagerBean,JqhDelegate> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder(){
        return new ItemBuilder();
    }
    public final ItemBuilder addItem(TabViewPagerBean bean, JqhDelegate delegate){
        ITEMS.put(bean,delegate);
        return this;
    }

    public  final ItemBuilder addItems(LinkedHashMap<TabViewPagerBean,JqhDelegate> items){
        ITEMS.putAll(items);
        return this ;
    }

    public final LinkedHashMap<TabViewPagerBean,JqhDelegate> build(){
        return ITEMS;
    }

}
