package com.jqh.wxvideo.delegate.mine;

import android.content.Intent;
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
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.bigkoo.convenientbanner.ConvenientBanner;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jqh.core.app.ConfigKeys;
import com.jqh.core.app.Jqh;
import com.jqh.core.app.SignHandler;
import com.jqh.core.bottom.BottomItemDelegate;
import com.jqh.core.media.EntityVideo;
import com.jqh.core.media.MediaUtils;
import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.IError;
import com.jqh.core.net.calback.IFailure;
import com.jqh.core.net.calback.ISuccess;
import com.jqh.core.ui.photo.PictureSelector;
import com.jqh.core.ui.photo.RequestCode;
import com.jqh.core.util.log.JqhLogger;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.login.LoginDelegate;
import com.jqh.wxvideo.delegate.videolist.TabPagerUserVideoAdapter;
import com.jqh.wxvideo.utils.cache.CacheData;
import com.jqh.wxvideo.utils.json.ResponseParse;

import java.io.File;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MineDelegate extends BottomItemDelegate implements
        AppBarLayout.OnOffsetChangedListener{
    private final int resultCode = 1001;
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
    private AppCompatButton uploadBtn ;

    TabLayout mTabLayout = null;
    ViewPager mViewPager = null;

    CollapsingToolbarLayout mCollapsingToolbarLayout = null;

    AppBarLayout mAppBar = null;

    private String userId ;

    private AppCompatButton logoutBtn ;

    public static final String USER_ID_KEY = "USER_ID_KEY";
    @Override
    public Object setLayout() {
        return R.layout.delegate_mine;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments() ;
        userId = args.getString(USER_ID_KEY);

    }

    public static MineDelegate getInstance(String userId ){
        Bundle args = new Bundle();
        args.putString(USER_ID_KEY,userId);
        MineDelegate delegate = new MineDelegate();
        delegate.setArguments(args);
        return delegate ;
    }


    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        uploadBtn = rootView.findViewById(R.id.btn_upload);
        nameTv = rootView.findViewById(R.id.tv_name);
        thumbImg = rootView.findViewById(R.id.iv_thum);
        fanTv = rootView.findViewById(R.id.tv_fannum);
        followTv = rootView.findViewById(R.id.tv_follownum);
        priseTv = rootView.findViewById(R.id.tv_prisenum);
        logoutBtn = rootView.findViewById(R.id.btn_logout);
        mTabLayout = (TabLayout)rootView.findViewById(R.id.tab_mine_layout);
        mViewPager = (ViewPager)rootView.findViewById(R.id.view_pager_mine);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout)rootView.findViewById(R.id.collapsing_toolbar_mine);
        mAppBar = (AppBarLayout)rootView.findViewById(R.id.app_bar_mine);
        // 伸缩topbar变换颜色
        mCollapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(getContext(),R.color.app_main));
        mAppBar.addOnOffsetChangedListener(this);
        initTabLayout();
        if(!CacheData.isLogin()){
            //this.getParentDelegate().start(new LoginDelegate());
            SignHandler.signUp();
        }
//        this.getParentDelegate().start(new LoginDelegate());

        // 获取用户信息
        updateUserInfo();
        initPager();

        initEvent();
    }

    private void initTabLayout(){
        // 均分
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        mTabLayout.setSelectedTabIndicatorColor
                (ContextCompat.getColor(getContext(), R.color.app_main));
        mTabLayout.setTabTextColors(ColorStateList.valueOf(Color.BLACK));
        mTabLayout.setBackgroundColor(Color.WHITE);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void initPager() {
        final PagerAdapter adapter = new TabPagerUserVideoAdapter(getFragmentManager(), userId);
        mViewPager.setAdapter(adapter);
    }
    /**
     * 更新用户信息
     */
    private void updateUserInfo(){
        String fanId = CacheData.getUserId() ;
        String token = CacheData.getTokenId();
        RestClient.builder()
                .loader(getContext())
                .url("/user/query?userId="+userId + "&fanId=" + fanId)
                .headers("userId",userId)
                .headers("userToken",token)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JqhLogger.d(response);
                        int status = ResponseParse.getStatus(response);
                        if(status == ResponseParse.STATUS_OK){
                            updateView(response);
                        }
                        else if(status == ResponseParse.STATUS_TOKEN_ERR){
                           // MineDelegate.this.getParentDelegate().start(new LoginDelegate());
                            SignHandler.signUp();
                        }else {
                            ToastUtils.showShort(ResponseParse.getMsg(response));
                        }

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

    private void updateView(String response){
        String host = Jqh.getConfiguration(ConfigKeys.API_HOST);
        JSONObject data = JSON.parseObject(response).getJSONObject("data");
        String faceImage = data.getString("faceImage");
        String nickName = data.getString("username");
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


    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {

    }

    private void initEvent(){
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CacheData.deleteLoginToken();
                SignHandler.signUp();

            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 上传作品
                PictureSelector.create(MineDelegate.this)
                        .setSelectorType(PictureSelector.SELECTOR_TYPE_MEDIA)
                        .maxSelectNum(1)
                        .forResult(resultCode)
                        .start();
            }
        });

        thumbImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 上传头像
                PictureSelector.create(MineDelegate.this)
                        .setSelectorType(PictureSelector.SELECTOR_TYPE_IMAGES)
                        .maxSelectNum(1)
                        .forResult(resultCode)
                        .start();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCode.REQUESTCODE_IMAGE_SELECT && resultCode == this.resultCode){
            ArrayList<String> imageDatas = data.getStringArrayListExtra("data");
            if(imageDatas.size() == 1 ){
                String path = imageDatas.get(0);
                updateFace(path);
            }
        }else if(requestCode == RequestCode.REQUESTCODE_MEDIA_SELECT && resultCode == this.resultCode){
            EntityVideo entityVideo = (EntityVideo) data.getSerializableExtra("data");
            if(entityVideo != null){
                MediaUtils.fillVideWH(entityVideo.getVideoPath(),entityVideo);
                uploadVideo(entityVideo);
            }
        }

    }

    private void updateFace(String path){
        String userId = CacheData.getUserId();
        String tokenId = CacheData.getTokenId();
        RestClient.builder()
                .loader(getContext())
                .url("user/uploadFace?userId="+userId)
                .file(path)
                .headers("userId",userId)
                .headers("userToken",tokenId)
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JqhLogger.d(response);
                        updateUserInfo();
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        JqhLogger.d(msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        JqhLogger.d("onFailure");
                    }
                })
                .build()
                .upload();

    }

    /**
     * 上传视频
     * @param entityVideo
     */
    private void uploadVideo(EntityVideo entityVideo){
        String userId = CacheData.getUserId();
        String tokenId = CacheData.getTokenId();
        RestClient.builder()
                .loader(getContext())
                .url("video/upload")
                .file(entityVideo.getVideoPath())
                .headers("userId",userId)
                .headers("userToken",tokenId)
                .params("userId",userId)
                .params("bgmId","")
                .params("desc","desc")
                .params("videoSeconds",entityVideo.getDuration())
                .params("videoWidth",entityVideo.getWidth())
                .params("videoHeight",entityVideo.getHeight())
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        JqhLogger.d(response);
                    }
                })
                .error(new IError() {
                    @Override
                    public void onError(int code, String msg) {
                        JqhLogger.d(msg);
                    }
                })
                .failure(new IFailure() {
                    @Override
                    public void onFailure() {
                        JqhLogger.d("onFailure");
                    }
                })
                .build()
                .upload();

    }
}
