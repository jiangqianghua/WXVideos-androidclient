package com.jqh.core.bottom;

/**
 * Created by jiangqianghua on 18/7/28.
 */

public final class BottomTabBean {

    private final CharSequence ICON ;
    private final CharSequence TITLE ;

    public BottomTabBean(CharSequence icon, CharSequence title) {
        this.ICON = icon;
        this.TITLE = title;
    }

    public CharSequence getIcon() {
        return ICON;
    }

    public CharSequence getTitle() {
        return TITLE;
    }
}
