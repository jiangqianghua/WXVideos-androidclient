package com.jqh.core.bottom;

/**
 * Created by jiangqianghua on 18/7/28.
 */

public final class BottomTabBean {

    public static final int TAB_TYPE_ICON_TEXT = 0 ;
    public static final int TAB_TYPE_IMAGE = 1 ;
    private CharSequence ICON ;
    private CharSequence TITLE ;

    private int tabType = TAB_TYPE_ICON_TEXT ;

    private int resId = 0 ;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
        tabType = TAB_TYPE_ICON_TEXT ;
    }

    public BottomTabBean(int resId){
        this.resId = resId ;
        tabType = TAB_TYPE_IMAGE;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }

    public int getResId() {
        return resId;
    }

    public int getTabType() {
        return tabType;
    }
}
