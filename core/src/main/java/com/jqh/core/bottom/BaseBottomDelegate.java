package com.jqh.core.bottom;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.joanzapata.iconify.widget.IconTextView;
import com.jqh.core.R;
import com.jqh.core.deletegates.JqhDelegate;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import me.yokeyword.fragmentation.SupportFragment;

/**
 * Created by jiangqianghua on 18/7/28.
 */

public abstract class BaseBottomDelegate extends JqhDelegate implements View.OnClickListener{
    private final ArrayList<BottomItemDelegate> ITEM_DELEGES = new ArrayList<>();
    private final ArrayList<BottomTabBean> TAB_BEAN = new ArrayList<>();
    private final LinkedHashMap<BottomTabBean,BottomItemDelegate> ITEMS = new LinkedHashMap<>();

    // 当前显示的delegate
    private int mCurrentDelegate = 0 ;
    // 被选中的item的index
    private int mIndexDelgate = 0 ;

    private LinearLayout mBottomBar ;

    // table被点击的颜色
    private int mClickColor = Color.RED;

    public abstract LinkedHashMap<BottomTabBean,BottomItemDelegate> setItems(ItemBuilder builder);

    public abstract  int setIndexDelegate();

    @ColorInt
    public abstract  int setClickedColor();


    @Override
    public Object setLayout() {
        return R.layout.deletage_bottom;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setIndexDelegate();

        if(setClickedColor() != 0)
            mClickColor = setClickedColor();

        final ItemBuilder builder = ItemBuilder.builder();
        final LinkedHashMap<BottomTabBean,BottomItemDelegate> items = setItems(builder);
        ITEMS.putAll(items);
        for(Map.Entry<BottomTabBean,BottomItemDelegate> item: ITEMS.entrySet()){
            final BottomTabBean key = item.getKey();
            final BottomItemDelegate value = item.getValue();
            TAB_BEAN.add(key);
            ITEM_DELEGES.add(value);
        }
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        // 初始化底部导航
        mBottomBar = (LinearLayout) rootView.findViewById(R.id.bottom_bar);
        final int size = ITEMS.size();
        for(int i = 0 ; i < size ; i++){
            LayoutInflater.from(getContext()).inflate(R.layout.bottom_item_icon_text_layout,mBottomBar);
            final RelativeLayout item = (RelativeLayout) mBottomBar.getChildAt(i);
            item.setTag(i);
            item.setOnClickListener(this);
            final IconTextView itemIcon = (IconTextView)item.getChildAt(0);
            final AppCompatTextView itemTitle = (AppCompatTextView)item.getChildAt(1);
            final BottomTabBean bean = TAB_BEAN.get(i);
            itemIcon.setText(bean.getIcon().toString());
            itemTitle.setText(bean.getTitle());
            if(i == mIndexDelgate){
                itemIcon.setTextColor(mClickColor);
                itemTitle.setTextColor(mClickColor);
            }
        }
        final SupportFragment[] delegateArray = ITEM_DELEGES.toArray(new SupportFragment[size]);
        loadMultipleRootFragment(R.id.bottom_bar_delegate_container,mIndexDelgate,delegateArray);

    }

    private void resetColor(){
        final int count = mBottomBar.getChildCount();
        for(int i = 0 ; i < count ; i++){
            final RelativeLayout item = (RelativeLayout)mBottomBar.getChildAt(i);
            final IconTextView itemIcon = (IconTextView)item.getChildAt(0);
            itemIcon.setTextColor(Color.GRAY);
            final AppCompatTextView itemTitle = (AppCompatTextView)item.getChildAt(1);
            itemTitle.setTextColor(Color.GRAY);
        }
    }

    @Override
    public void onClick(View v) {
        final int tag = (int)v.getTag();
        resetColor();
        final RelativeLayout item = (RelativeLayout)v;
        final IconTextView itemIcon = (IconTextView)item.getChildAt(0);
        itemIcon.setTextColor(mClickColor);
        final AppCompatTextView itemTitle = (AppCompatTextView)item.getChildAt(1);
        itemTitle.setTextColor(mClickColor);
        showHideFragment(ITEM_DELEGES.get(tag),ITEM_DELEGES.get(mCurrentDelegate));
        mCurrentDelegate = tag;
    }
}
