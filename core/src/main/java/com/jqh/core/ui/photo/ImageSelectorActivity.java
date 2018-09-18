package com.jqh.core.ui.photo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;

import com.jqh.core.R;
import com.jqh.core.media.EntityImage;
import com.jqh.core.media.LocalImageList;
import com.jqh.core.recycler.BaseDecoration;
import com.jqh.core.recycler.MultipleItemEntity;

import java.util.ArrayList;
import java.util.List;

public class ImageSelectorActivity extends AppCompatActivity {

    private RecyclerView recyclerView ;

    private PictureSelectorAdapter adapter ;

    private int maxSelectNum = 0;
    private int resultCode ;

    private AppCompatButton okBtn ;
    private AppCompatButton cancelBtn ;

    private int selectCount = 0 ;

    private ArrayList<String> selectList = new ArrayList<>() ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去掉标题栏
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_image_selector);
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

        okBtn = findViewById(R.id.btn_ok);
        cancelBtn = findViewById(R.id.btn_cancel);

        okBtn.setText("确定("+selectCount+"/"+maxSelectNum+")");

    }

    private void initEvent(){
        okBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra("data",selectList);
                setResult(resultCode, intent);  //多选不允许裁剪裁剪，返回数据
                finish();
            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        adapter.setOnPictureSelectorAdapterListener(new PictureSelectorAdapter.OnPictureSelectorAdapterListener() {
            @Override
            public void onAddImageClick(String path) {
                selectCount++;
                okBtn.setText("确定("+selectCount+"/"+maxSelectNum+")");
                selectList.add(path);
            }

            @Override
            public void onDelImageClick(String path) {
                selectCount--;
                okBtn.setText("确定("+selectCount+"/"+maxSelectNum+")");
                deleteSelectList(path);
            }
        });
    }

    private void deleteSelectList(String path){
        for(int i = 0 ; i < selectList.size() ; i++){
            String val = selectList.get(i);
            if(val.equals(path)){
                selectList.remove(val);
            }
        }
    }

    private List<MultipleItemEntity> getDatas(){
        List<MultipleItemEntity> datas = new ArrayList<>();
        LocalImageList imageList = new LocalImageList();
        List<EntityImage> videos = imageList.getList(this);
        for(EntityImage entityVideo:videos){
            MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(PictureType.PICTURE_TYPE_IMAGE)
                    .setField(PictureFiles.PICTURE_URL,entityVideo.getPath())
                    .setField(PictureFiles.PICTURE_SELECT,false)
                    .build();
            datas.add(entity);
        }
        return datas ;
    }
}
