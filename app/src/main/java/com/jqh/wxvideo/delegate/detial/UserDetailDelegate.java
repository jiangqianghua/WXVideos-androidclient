package com.jqh.wxvideo.delegate.detial;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jqh.core.app.ConfigKeys;
import com.jqh.core.app.Jqh;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.IError;
import com.jqh.core.net.calback.IFailure;
import com.jqh.core.net.calback.ISuccess;
import com.jqh.core.util.log.JqhLogger;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.mine.TabPagerMineAdapter;
import com.jqh.wxvideo.utils.cache.CacheData;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailDelegate extends JqhDelegate implements
        AppBarLayout.OnOffsetChangedListener {
    public static final RequestOptions OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();
    private CircleImageView thumbImg;
    private AppCompatTextView nameTv;
    private AppCompatTextView fanTv;
    private AppCompatTextView followTv;
    private AppCompatTextView priseTv;

    TabLayout mTabLayout = null;
    ViewPager mViewPager = null;

    CollapsingToolbarLayout mCollapsingToolbarLayout = null;

    AppBarLayout mAppBar = null;

    private String userId;

    public static final String USER_ID_KEY = "USER_ID_KEY";

    @Override
    public Object setLayout() {
        return R.layout.delegate_user_detial;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        userId = args.getString(USER_ID_KEY);
    }

    public static UserDetailDelegate getInstance(String userId) {
        Bundle args = new Bundle();
        args.putString(USER_ID_KEY, userId);
        UserDetailDelegate delegate = new UserDetailDelegate();
        delegate.setArguments(args);
        return delegate;
    }


    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {

        nameTv = rootView.findViewById(R.id.tv_name);
        thumbImg = rootView.findViewById(R.id.iv_thum);
        fanTv = rootView.findViewById(R.id.tv_fannum);
        followTv = rootView.findViewById(R.id.tv_follownum);
        priseTv = rootView.findViewById(R.id.tv_prisenum);

        mTabLayout = (TabLayout) rootView.findViewById(R.id.tab_detial_layout);
        mViewPager = (ViewPager) rootView.findViewById(R.id.view_pager_detial);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) rootView.findViewById(R.id.collapsing_toolbar_mine);
        mAppBar = (AppBarLayout) rootView.findViewById(R.id.app_bar_detial);
        // 伸缩topbar变换颜色
        mCollapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(getContext(), R.color.app_main));
        mAppBar.addOnOffsetChangedListener(this);
        initTabLayout();

        // 获取用户信息
        updateUserInfo();
        initPager();
    }

    private void initTabLayout() {
        // 均分
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor
                (ContextCompat.getColor(getContext(), R.color.app_main));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initPager() {
        final PagerAdapter adapter = new TabPagerMineAdapter(getFragmentManager(), userId);
        mViewPager.setAdapter(adapter);
    }

    /**
     * 更新用户信息
     */
    private void updateUserInfo() {
        String fanId = CacheData.getUserId();
        String token = CacheData.getTokenId();
        RestClient.builder()
                .loader(getContext())
                .url("/user/query?userId=" + userId + "&fanId=" + fanId)
                .headers("userId", fanId)
                .headers("userToken", token)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JqhLogger.d(response);
                        updateView(response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        ToastUtils.showShort(msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        ToastUtils.showShort("onFailure");
                    }
                })
                .build().postJson();
    }

    private void updateView(String response) {
        String host = Jqh.getConfiguration(ConfigKeys.API_HOST);
        JSONObject data = JSON.parseObject(response).getJSONObject("data");
        String faceImage = data.getString("faceImage");
        String nickName = data.getString("username");
        int fansCounts = data.getInteger("fansCounts");
        int receiveLikeCounts = data.getInteger("receiveLikeCounts");
        int followCounts = data.getInteger("followCounts");
        Glide.with(getContext())
                .load(host + "" + faceImage)
                .apply(OPTIONS)
                .into(thumbImg);
        nameTv.setText(nickName);
        fanTv.setText(fansCounts + " 粉丝");
        followTv.setText(followCounts + " 关注");
        priseTv.setText(receiveLikeCounts + " 点赞");
    }


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }
}
