package com.jqh.wxvideo.delegate.video.recommend;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jqh.core.app.ConfigKeys;
import com.jqh.core.app.Jqh;
import com.jqh.core.recycler.MultipleFields;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.recycler.MultipleViewHolder;
import com.jqh.core.recycler.MutipleRecyclerAdapter;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.video.VideosItemType;

import java.util.List;

public class RecommentVideosAdapter extends MutipleRecyclerAdapter{

    public RecommentVideosAdapter(List<MultipleItemEntity> data) {
        super(data);
        addItemType(VideosItemType.VIDEO_ITEM_RECOMMENT, R.layout.item_recommend_video);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        final String imageUrl ;
        String host = Jqh.getConfiguration(ConfigKeys.API_HOST);
        switch (helper.getItemViewType()){
            case VideosItemType.VIDEO_ITEM_RECOMMENT:
                imageUrl = item.getField(MultipleFields.IMAGE_URL);
                Glide.with(mContext)
                        .load(host+""+imageUrl)
                        .apply(RECYCLER_OPTIONS)
                        .into((ImageView) helper.getView(R.id.id_video_item_image));
                break;
        }
    }
}
