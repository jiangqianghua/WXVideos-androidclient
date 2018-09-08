package com.jqh.wxvideo;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jqh.core.activites.ProxyActivity;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.wxvideo.delegate.LauncherDelegate;

public class MainActivity extends ProxyActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
    }

    @Override
    public JqhDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

}
