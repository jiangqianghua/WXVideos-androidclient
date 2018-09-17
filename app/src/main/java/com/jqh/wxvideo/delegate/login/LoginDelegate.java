package com.jqh.wxvideo.delegate.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.ISuccess;
import com.jqh.core.util.log.JqhLogger;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.utils.cache.CacheData;
import com.jqh.wxvideo.utils.json.ResponseParse;

public class LoginDelegate extends JqhDelegate implements View.OnClickListener {

    private EditText userIdEditText ;
    private EditText passEditText ;

    private Button loginBtn ;
    private Button registBtn ;
    @Override
    public Object setLayout() {
        return R.layout.delegate_login;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        userIdEditText = rootView.findViewById(R.id.userId);
        passEditText = rootView.findViewById(R.id.pass);

        loginBtn = rootView.findViewById(R.id.loginBtn);
        registBtn = rootView.findViewById(R.id.registBtn);

        loginBtn.setOnClickListener(this);
        registBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.loginBtn){
            login();
        }else if(view.getId() == R.id.registBtn){
            gotoRegistView();
        }
    }

    // 登录
    private void login(){
        String userId = userIdEditText.getText().toString();
        String pass = passEditText.getText().toString();

        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(pass)){
            ToastUtils.showShort("输入不能为空");
            return ;
        }

        RestClient.builder()
                .loader(getContext())
                .url("login")
                .params("username",userId)
                .params("password",pass)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        //{"status":200,"msg":"OK","data":{"id":"18072892YYX16R1P","userToken":"ff13e720-aea9-46ea-af3d-4c6975cfb2af","username":"1","faceImage":"/18072892YYX16R1P/face/tmp_31751fcdf334decfe6d7009a681ee850.jpg","nickname":"1","fansCounts":0,"followCounts":1,"receiveLikeCounts":1,"follow":false},"ok":null}
                        JqhLogger.d(response);
                        int status = ResponseParse.getStatus(response);
                        if(status == ResponseParse.STATUS_OK){
                            JSONObject data = JSON.parseObject(response).getJSONObject("data");
                            String userId = data.getString("id");
                            String token = data.getString("userToken");
                            CacheData.savaLoginToken(userId,token);
                            LoginDelegate.this.pop();
                        }else{
                            ToastUtils.showShort(ResponseParse.getMsg(response));
                        }

                    }
                })
                .build().postJson();

    }

    private void gotoRegistView(){
        this.getParentDelegate().start(new RegisterDelegate());
    }
}
