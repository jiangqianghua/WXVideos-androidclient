package com.jqh.wxvideo.delegate.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jqh.core.bottom.BottomItemDelegate;
import com.jqh.wxvideo.R;

public class HomeDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_home;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }
}
