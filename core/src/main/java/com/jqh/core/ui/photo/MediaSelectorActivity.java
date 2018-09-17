package com.jqh.core.ui.photo;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jqh.core.R;
import com.jqh.core.media.LocalVideoList;
import com.jqh.core.recycler.BaseDecoration;
import com.jqh.core.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

public class MediaSelectorActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;

    private PictureSelectorAdapter adapter ;

    private int maxSelectNum = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_selector);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra(BundleKey.BUNDLE_KEY);
        maxSelectNum = bundle.getInt(BundleKey.MAX_SELECTOR_NUM);
        bindView();
        initEvent();
    }


    private void bindView(){
        recyclerView = this.findViewById(R.id.rv_media_list);

        GridLayoutManager manager = new GridLayoutManager(this,3);
        recyclerView.setLayoutManager(manager);

        adapter = new PictureSelectorAdapter(getDatas());
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new BaseDecoration(Color.WHITE,10));
    }

    private void initEvent(){

    }

    private List<MultipleItemEntity> getDatas(){
        List<MultipleItemEntity> datas = new ArrayList<>();
        LocalVideoList videoList = new LocalVideoList();
        List<LocalVideoList.EntityVideo> videos = videoList.getList(this);
        for(LocalVideoList.EntityVideo entityVideo:videos){
            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(PictureType.PICTURE_TYPE_MEDIA)
                    .setField(PictureFiles.PICTURE_URL,entityVideo.getVideoPath())
                    .setField(PictureFiles.PICTURE_SELECT,false)
                    .build();
            datas.add(entity);
        }
        return datas ;
    }
}
