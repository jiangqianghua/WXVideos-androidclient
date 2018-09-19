package com.jqh.wxvideo;

import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jqh.core.activites.ProxyActivity;
import com.jqh.core.app.ISignListener;
import com.jqh.core.app.Jqh;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.wxvideo.delegate.LauncherDelegate;
import com.jqh.wxvideo.delegate.bottom.BottomDelegate;
import com.jqh.wxvideo.delegate.login.LoginDelegate;

public class MainActivity extends ProxyActivity implements ISignListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Jqh.getConfigurator().withActivity(this);
    }

    @Override
    public JqhDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignIn() {
        getSupportDelegate().startWithPop(new BottomDelegate());
    }

    @Override
    public void onSignUp() {
        getSupportDelegate().startWithPop(new LoginDelegate());
    }
}
