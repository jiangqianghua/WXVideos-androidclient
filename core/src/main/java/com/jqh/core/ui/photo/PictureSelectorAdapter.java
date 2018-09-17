package com.jqh.core.ui.photo;

import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jqh.core.R;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.recycler.MultipleViewHolder;
import com.jqh.core.recycler.MutipleRecyclerAdapter;

import java.util.List;

public class PictureSelectorAdapter extends MutipleRecyclerAdapter {

    public static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                   // .error(R.mipmap.checkbox_checked)
                    .dontAnimate();
    public PictureSelectorAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(PictureType.PICTURE_TYPE_IMAGE, R.layout.item_image_selector);
        addItemType(PictureType.PICTURE_TYPE_MEDIA,R.layout.item_media_selector);
    }

    @Override
    protected void convert(MultipleViewHolder helper, final MultipleItemEntity item) {
        super.convert(helper, item);
        int type = helper.getItemViewType();
        switch (type){
            case PictureType.PICTURE_TYPE_IMAGE:
                AppCompatImageView imageView = helper.getView(R.id.iv_image);
                String url = item.getField(PictureFiles.PICTURE_URL);
                Glide.with(mContext)
                        .load(url)
                        .apply(RECYCLER_OPTIONS)
                        .into(imageView);
                boolean selected = item.getField(PictureFiles.PICTURE_SELECT);
                final AppCompatImageView selectImageView = helper.getView(R.id.iv_image_select);
                if(!selected) {
                    Glide.with(mContext)
                            .load(R.mipmap.unselected)
                            .into(selectImageView);
                }else{
                    Glide.with(mContext)
                            .load(R.mipmap.selected)
                            .into(selectImageView);
                }
                selectImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean select = item.getField(PictureFiles.PICTURE_SELECT);
                        item.setField(PictureFiles.PICTURE_SELECT,!select);
                        select = !select ;
                        if(!select) {
                            Glide.with(mContext)
                                    .load(R.mipmap.unselected)
                                    .into(selectImageView);
                        }else{
                            Glide.with(mContext)
                                    .load(R.mipmap.selected)
                                    .into(selectImageView);
                        }
                    }
                });
                break;
            case PictureType.PICTURE_TYPE_MEDIA:
                AppCompatImageView imageView1 = helper.getView(R.id.iv_media);
                String url1 = item.getField(PictureFiles.PICTURE_URL);
                Glide.with(mContext)
                        .load(url1)
                        .apply(RECYCLER_OPTIONS)
                        .into(imageView1);

                boolean selected1 = item.getField(PictureFiles.PICTURE_SELECT);
                final AppCompatImageView selectImageView1 = helper.getView(R.id.iv_media_select);
                if(!selected1) {
                    Glide.with(mContext)
                            .load(R.mipmap.unselected)
                            .into(selectImageView1);
                }else{
                    Glide.with(mContext)
                            .load(R.mipmap.selected)
                            .into(selectImageView1);
                }

                selectImageView1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean select = item.getField(PictureFiles.PICTURE_SELECT);
                        item.setField(PictureFiles.PICTURE_SELECT,!select);
                        select = !select ;
                        if(!select) {
                            Glide.with(mContext)
                                    .load(R.mipmap.unselected)
                                    .into(selectImageView1);
                        }else{
                            Glide.with(mContext)
                                    .load(R.mipmap.selected)
                                    .into(selectImageView1);
                        }
                    }
                });
                break;
        }
    }
}
