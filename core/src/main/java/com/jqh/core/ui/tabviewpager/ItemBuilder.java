package com.jqh.core.ui.tabviewpager;

import java.util.LinkedHashMap;

public final class ItemBuilder {
    private final LinkedHashMap<TabViewPagerBean,TabViewPagerItemDelegate> ITEMS = new LinkedHashMap<>();

    static ItemBuilder builder(){
        return new ItemBuilder();
    }
    public final ItemBuilder addItem(TabViewPagerBean bean, TabViewPagerItemDelegate delegate){
        ITEMS.put(bean,delegate);
        return this;
    }

    public  final ItemBuilder addItems(LinkedHashMap<TabViewPagerBean,TabViewPagerItemDelegate> items){
        ITEMS.putAll(items);
        return this ;
    }

    public final LinkedHashMap<TabViewPagerBean,TabViewPagerItemDelegate> build(){
        return ITEMS;
    }

}
