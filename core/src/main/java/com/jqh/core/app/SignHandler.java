package com.jqh.core.app;

import android.app.Activity;

/**
 * 登录登出辅助类
 */
public class SignHandler {

    public static void signIn(){
        // 存储一些登录信息
        getSignListener().onSignIn();
    }

    public static void signUp(){
        // 清除一些登出信息
        getSignListener().onSignUp();
    }

    public static ISignListener getSignListener(){
        Activity activity = Jqh.getConfiguration(ConfigKeys.ACTIVITY);
        if(activity instanceof ISignListener){
            ISignListener signListener = (ISignListener)activity;
            return signListener ;
        }
        return null ;
    }
}
