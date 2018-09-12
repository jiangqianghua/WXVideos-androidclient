package com.jqh.wxvideo.delegate.mine;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jqh.core.app.ConfigKeys;
import com.jqh.core.app.Jqh;
import com.jqh.core.bottom.BottomItemDelegate;
import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.ISuccess;
import com.jqh.core.util.log.JqhLogger;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.login.LoginDelegate;
import com.jqh.wxvideo.utils.cache.CacheData;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineDelegate extends BottomItemDelegate {
    public static final RequestOptions OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();
    private CircleImageView thumbImg ;
    private AppCompatTextView nameTv ;
    private AppCompatTextView fanTv;
    private AppCompatTextView followTv ;
    private AppCompatTextView priseTv ;
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

        nameTv = rootView.findViewById(R.id.tv_name);
        thumbImg = rootView.findViewById(R.id.iv_thum);
        fanTv = rootView.findViewById(R.id.tv_fannum);
        followTv = rootView.findViewById(R.id.tv_follownum);
        priseTv = rootView.findViewById(R.id.tv_prisenum);

        if(!CacheData.isLogin()){
            this.getParentDelegate().start(new LoginDelegate());
        }
       // this.getParentDelegate().start(new LoginDelegate());

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
                .headers("userId",userId)
                .headers("userToken",token)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JqhLogger.d(response);
                        updateView(response);
                    }
                })
                .build().postJson();
    }

    private void updateView(String response){
        String host = Jqh.getConfiguration(ConfigKeys.API_HOST);
        JSONObject data = JSON.parseObject(response).getJSONObject("data");
        String faceImage = data.getString("faceImage");
        String nickName = data.getString("nickName");
        int fansCounts = data.getInteger("fansCounts");
        int receiveLikeCounts = data.getInteger("receiveLikeCounts");
        int followCounts = data.getInteger("followCounts") ;
        Glide.with(getContext())
                .load(host+""+faceImage)
                .apply(OPTIONS)
                .into(thumbImg);
        nameTv.setText(nickName);
        fanTv.setText(fansCounts+" 粉丝");
        followTv.setText(followCounts+" 关注");
        priseTv.setText(receiveLikeCounts+" 点赞");
    }




}
