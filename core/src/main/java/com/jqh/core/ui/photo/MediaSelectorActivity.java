package com.jqh.core.ui.photo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqh.core.R;
import com.jqh.core.media.EntityVideo;
import com.jqh.core.media.LocalVideoList;
import com.jqh.core.recycler.BaseDecoration;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.recycler.SimpleClickListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MediaSelectorActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;

    private PictureSelectorAdapter adapter ;

    private int maxSelectNum = 0;

    private int resultCode ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_media_selector);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BundleKey.BUNDLE_KEY);
        maxSelectNum = bundle.getInt(BundleKey.MAX_SELECTOR_NUM);
        resultCode = bundle.getInt(BundleKey.RESULT_CODE);
        bindView();
        initEvent();
    }


    private void bindView(){
        recyclerView = this.findViewById(R.id.rv_list);

        GridLayoutManager manager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(manager);

        adapter = new PictureSelectorAdapter(getDatas(),maxSelectNum);
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new BaseDecoration(Color.WHITE,10));


    }

    private void initEvent(){
        recyclerView.addOnItemTouchListener(new SimpleClickListenerAdapter(){
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MultipleItemEntity entity = (MultipleItemEntity) adapter.getData().get(position);
                String video = entity.getField(PictureFiles.PICTURE_URL);
                int duration = entity.getField(PictureFiles.PICTURE_DURATION);
                String thumb = entity.getField(PictureFiles.PICTURE_THUMB);

                // 跳转到播放activity
                Intent intent = new Intent(MediaSelectorActivity.this, VideoPlayerActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("video",video);
                bundle.putString("thumb",thumb);
                bundle.putInt("duration",duration);
                bundle.putInt("resultcode",resultCode);
                intent.putExtras(bundle);
                MediaSelectorActivity.this.startActivityForResult(intent,RequestCode.REQUESTCODE_MEDIA_PLAY);
            }
        });
    }

    private List<MultipleItemEntity> getDatas(){
        List<MultipleItemEntity> datas = new ArrayList<>();
        LocalVideoList videoList = new LocalVideoList();
        List<EntityVideo> videos = videoList.getList(this);
        for(EntityVideo entityVideo:videos){
            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(PictureType.PICTURE_TYPE_MEDIA)
                    .setField(PictureFiles.PICTURE_URL,entityVideo.getVideoPath())
                    .setField(PictureFiles.PICTURE_SELECT,false)
                    .setField(PictureFiles.PICTURE_DURATION,entityVideo.getDuration())
                    .setField(PictureFiles.PICTURE_THUMB,entityVideo.getThumbPath())
                    .build();
            datas.add(entity);
        }
        return datas ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
      //  super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RequestCode.REQUESTCODE_MEDIA_PLAY && resultCode == this.resultCode){
            setResult(resultCode,data);
            finish();
        }

    }
}
