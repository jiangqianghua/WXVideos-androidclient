package com.jqh.wxvideo.delegate.video.videoplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jqh.core.app.ConfigKeys;
import com.jqh.core.app.Jqh;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.recycler.MultipleFields;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.util.log.JqhLogger;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.detial.UserDetailDelegate;
import com.jqh.wxvideo.delegate.video.VideoItemFields;

import cn.jzvd.JZVideoPlayerStandard;
import de.hdodenhof.circleimageview.CircleImageView;

public class PlayVideoPageItemFragment extends JqhDelegate {

   // private AppCompatImageView imageView ;
    private String cover ;
    private String videoUrl ;
    private JZVideoPlayerStandard jzVideoPlayerStandard ;
    private CircleImageView headerImageView ;

    private String userId;
    private String header ;
    private static final RequestOptions OPTIONS = new RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate()
            .centerCrop();
    public static PlayVideoPageItemFragment newInstance(MultipleItemEntity entity) {

        Bundle args = new Bundle();
        PlayVideoPageItemFragment fragment = new PlayVideoPageItemFragment();
        String cover = entity.getField(VideoItemFields.COVER) ;
        String videoPath = entity.getField(VideoItemFields.VIDEOPATH);
        String userId= entity.getField(VideoItemFields.USERID);
        String header = entity.getField(VideoItemFields.THUMB);
        args.putString("coverPath",cover);
        args.putString("videoPath",videoPath);
        args.putString("userId",userId);
        args.putString("header",header);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        cover = args.getString("coverPath");
        videoUrl = args.getString("videoPath");
        userId = args.getString("userId");
        header = args.getString("header");
        JqhLogger.d("PlayVideoPageItemFragment:onCreate"+cover);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_item_playvideo;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        //imageView = rootView.findViewById(R.id.iv_cover);
        String host = Jqh.getConfiguration(ConfigKeys.API_HOST);
        jzVideoPlayerStandard = (JZVideoPlayerStandard) rootView.findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp(host+""+videoUrl,
                JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL,
                "");


        Glide.with(getContext())
                .load(host+""+cover)
                .into(jzVideoPlayerStandard.thumbImageView);


        headerImageView = rootView.findViewById(R.id.iv_header);

        Glide.with(getContext())
                .load(host+""+header)
                .apply(OPTIONS)
                .into(headerImageView);

        headerImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PlayVideoPageItemFragment.this.getParentDelegate().start(UserDetailDelegate.getInstance(userId));
            }
        });

    }

//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        JqhLogger.d("PlayVideoPageItemFragment:"+cover+" "+hidden);
//    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser){
            //播放
            if(jzVideoPlayerStandard != null)
                jzVideoPlayerStandard.startVideo();
        }else{
            //停止
//            jzVideoPlayerStandard.stop;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        JqhLogger.d("PlayVideoPageItemFragment:onDestory"+cover);
    }
}
