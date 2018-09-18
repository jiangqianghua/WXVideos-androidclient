package com.jqh.core.ui.photo;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jqh.core.R;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.recycler.MultipleViewHolder;
import com.jqh.core.recycler.MutipleRecyclerAdapter;
import com.jqh.core.util.date.DateUtils;

import java.util.List;

public class PictureSelectorAdapter extends MutipleRecyclerAdapter {

    private final int maxSelectCount  ;

    private int mCurSelectCount = 0 ;

    public interface OnPictureSelectorAdapterListener{
        void onAddImageClick(String path);
        void onDelImageClick(String path);
    }

    private OnPictureSelectorAdapterListener onPictureSelectorAdapterListener ;

    public void setOnPictureSelectorAdapterListener(OnPictureSelectorAdapterListener onPictureSelectorAdapterListener) {
        this.onPictureSelectorAdapterListener = onPictureSelectorAdapterListener;
    }

    public static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                   // .error(R.mipmap.checkbox_checked)
                    .dontAnimate();
    public PictureSelectorAdapter(List<MultipleItemEntity> data, int maxSelectCount) {
        super(data);
        addItemType(PictureType.PICTURE_TYPE_IMAGE, R.layout.item_image_selector);
        addItemType(PictureType.PICTURE_TYPE_MEDIA, R.layout.item_media_selector);
        this.maxSelectCount = maxSelectCount ;
    }

    @Override
    protected void convert(MultipleViewHolder helper, final MultipleItemEntity item) {
        super.convert(helper, item);

        AppCompatImageView imageView = helper.getView(R.id.iv_image);
        String url = item.getField(PictureFiles.PICTURE_URL);
        Glide.with(mContext)
                .load(url)
                .apply(RECYCLER_OPTIONS)
                .into(imageView);
        int type = helper.getItemViewType();
        switch (type){
            case PictureType.PICTURE_TYPE_IMAGE:
                boolean selected = item.getField(PictureFiles.PICTURE_SELECT);
                final AppCompatImageView selectImageView = helper.getView(R.id.iv_select);
                final View selectBk = helper.getView(R.id.view_select_bk);
                if(!selected) {
                    Glide.with(mContext)
                            .load(R.mipmap.unselected)
                            .into(selectImageView);
                    selectBk.setVisibility(View.INVISIBLE);
                }else{
                    Glide.with(mContext)
                            .load(R.mipmap.selected)
                            .into(selectImageView);
                    selectBk.setVisibility(View.VISIBLE);
                }
                selectImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean select = item.getField(PictureFiles.PICTURE_SELECT);
                        if(!select){
                            if(mCurSelectCount >= maxSelectCount){
                                ToastUtils.showShort("最多选中"+maxSelectCount+"张");
                                return ;
                            }
                        }
                        item.setField(PictureFiles.PICTURE_SELECT,!select);
                        select = !select ;
                        String url = item.getField(PictureFiles.PICTURE_URL);
                        if(!select) {
                            mCurSelectCount--;
                            if(mCurSelectCount < 0)
                                mCurSelectCount = 0 ;
                            Glide.with(mContext)
                                    .load(R.mipmap.unselected)
                                    .into(selectImageView);
                            selectBk.setVisibility(View.INVISIBLE);
                            if(onPictureSelectorAdapterListener != null)
                                onPictureSelectorAdapterListener.onDelImageClick(url);
                        }else{
                            mCurSelectCount++;
                            Glide.with(mContext)
                                    .load(R.mipmap.selected)
                                    .into(selectImageView);
                            selectBk.setVisibility(View.VISIBLE);
                            if(onPictureSelectorAdapterListener != null)
                                onPictureSelectorAdapterListener.onAddImageClick(url);
                        }
                    }
                });
                break;
            case PictureType.PICTURE_TYPE_MEDIA:
                AppCompatTextView durationTextView = helper.getView(R.id.iv_duration);
                int druation = item.getField(PictureFiles.PICTURE_DURATION);
                String time = DateUtils.secToTime(druation/1000);
                durationTextView.setText(time);
                break;
        }
    }
}
