package com.jqh.wxvideo.delegate.video;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jqh.core.bottom.BottomItemDelegate;
import com.jqh.wxvideo.R;

public class VideosDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_videos;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        VideosTabViewDelegate delegate = new VideosTabViewDelegate();
        loadRootFragment(R.id.fragment_videox,delegate);
    }
}
