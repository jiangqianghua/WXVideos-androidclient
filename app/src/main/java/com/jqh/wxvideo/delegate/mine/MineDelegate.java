package com.jqh.wxvideo.delegate.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.jqh.core.bottom.BottomItemDelegate;
import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.ISuccess;
import com.jqh.core.util.log.JqhLogger;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.login.LoginDelegate;
import com.jqh.wxvideo.utils.cache.CacheData;

public class MineDelegate extends BottomItemDelegate {
    @Override
    public Object setLayout() {
        return R.layout.delegate_mine;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        if(!CacheData.isLogin()){
            this.getParentDelegate().start(new LoginDelegate());
        }

        // 获取用户信息
        updateUserInfo();

    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo(){
        String userId = CacheData.getUserId() ;
        String token = CacheData.getTokenId();
        RestClient.builder()
                .loader(getContext())
                .url("/user/query?userId="+userId + "&fanId=" + userId)
                .params("userId",userId)
                .params("userToken",token)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JqhLogger.d(response);
                    }
                })
                .build().postJson();
    }




}
