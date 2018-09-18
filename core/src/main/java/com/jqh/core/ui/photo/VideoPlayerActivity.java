package com.jqh.core.ui.photo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.jqh.core.R;
import com.jqh.core.media.EntityVideo;

import cn.jzvd.JZVideoPlayer;
import cn.jzvd.JZVideoPlayerStandard;

public class VideoPlayerActivity extends AppCompatActivity {

    private AppCompatButton btnOk ;

    private EntityVideo entityVideo ;

    private int resultCode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_video_player);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String video = bundle.getString("video");
        String thumb = bundle.getString("thumb");
        int duration = bundle.getInt("duration");
        final int resultCode = bundle.getInt("resultcode");
        entityVideo = new EntityVideo();
        entityVideo.setDuration(duration);
        entityVideo.setThumbPath(thumb);
        entityVideo.setVideoPath(video);
        JZVideoPlayerStandard jzVideoPlayerStandard = (JZVideoPlayerStandard) findViewById(R.id.videoplayer);
        jzVideoPlayerStandard.setUp(video,
                JZVideoPlayerStandard.SCREEN_WINDOW_LIST,
                "");
        jzVideoPlayerStandard.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        jzVideoPlayerStandard.startVideo();
        Glide.with(getApplicationContext())
                .load(thumb)
                .into(jzVideoPlayerStandard.thumbImageView);

        btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putExtra("data",entityVideo);
                setResult(resultCode, intent);  //多选不允许裁剪裁剪，返回数据
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (JZVideoPlayer.backPress()) {
            return;
        }
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        super.onPause();
        JZVideoPlayer.releaseAllVideos();
    }

}
