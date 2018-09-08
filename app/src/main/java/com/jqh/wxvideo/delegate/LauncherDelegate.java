package com.jqh.wxvideo.delegate;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;

import com.jqh.core.bottom.BottomItemDelegate;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.bottom.BottomDelegate;

public class LauncherDelegate extends BottomItemDelegate {

    private AppCompatButton enterBtn ;
    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        enterBtn = rootView.findViewById(R.id.launcher_enter);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startWithPop(new BottomDelegate());
            }
        });
    }
}
