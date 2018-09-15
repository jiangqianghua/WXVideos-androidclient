package com.jqh.wxvideo.delegate.mine;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.jqh.core.app.ConfigKeys;
import com.jqh.core.app.Jqh;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.recycler.MultipleViewHolder;
import com.jqh.core.recycler.MutipleRecyclerAdapter;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.video.VideoItemFields;
import com.jqh.wxvideo.delegate.video.VideosItemType;

import java.util.List;

public class MineVideoTabItemAdapter extends MutipleRecyclerAdapter {
    public static final RequestOptions OPTIONS =
            new RequestOptions()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .dontAnimate();

    public MineVideoTabItemAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(VideosItemType.VIDEO_ITEM_MINE, R.layout.item_mine_tabvideo);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        final String imageUrl ;
        final String desc ;
        final String thumb ;
        final int likeCount ;
        String host = Jqh.getConfiguration(ConfigKeys.API_HOST);
        switch (helper.getItemViewType()){
            case VideosItemType.VIDEO_ITEM_MINE:
                imageUrl = item.getField(VideoItemFields.COVER);
                desc = item.getField(VideoItemFields.DESC);
                thumb = item.getField(VideoItemFields.THUMB);
                likeCount = item.getField(VideoItemFields.LIKECOUNT);
                Glide.with(mContext)
                        .load(host+""+imageUrl)
                        .apply(OPTIONS)
                        .into((ImageView) helper.getView(R.id.id_mine_tab_image));
                break;
        }
    }
}
