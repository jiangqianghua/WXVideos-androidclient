package com.jqh.core.recycler;

import android.support.annotation.ColorInt;

import com.choices.divider.DividerItemDecoration;

/**
 * recycler 分割线
 */
public class BaseDecoration extends DividerItemDecoration {

    public BaseDecoration(@ColorInt int color , int size){
        setDividerLookup(new DividerLookUpImpl(color,size));
    }

    public static BaseDecoration create(@ColorInt int color , int size){
        return new BaseDecoration(color,size);
    }
}
