package com.jqh.wxvideo.delegate.mine;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jqh.core.recycler.DataConverter;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.wxvideo.delegate.video.VideoItemFields;
import com.jqh.wxvideo.delegate.video.VideosItemType;

import java.util.ArrayList;

public class MineVideoDataConverter extends DataConverter {
    @Override
    public ArrayList<MultipleItemEntity> convert() {
        final JSONArray dataArray = JSON.parseObject(getJsonData()).getJSONObject("data").getJSONArray("rows");
        final int size = dataArray.size();
        for(int i = 0; i < size ; i++){
            final JSONObject data = dataArray.getJSONObject(i);
            String id = data.getString("id");
            String userId = data.getString("userId");
            String coverPath = data.getString("coverPath");
            String videoPath = data.getString("videoPath");
            String nickName = data.getString("nickName");
            String faceImage = data.getString("faceImage");
            int likeCounts = data.getInteger("likeCounts");
            String desc = data.getString("videoDesc");

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(VideosItemType.VIDEO_ITEM_MINE)
                    .setField(VideoItemFields.COVER,coverPath)
                    .setField(VideoItemFields.DESC,desc)
                    .setField(VideoItemFields.LIKECOUNT,likeCounts)
                    .setField(VideoItemFields.THUMB,faceImage)
                    .setField(VideoItemFields.VIDEOPATH,videoPath)
                    .build();
            ENTITES.add(entity);
        }
        return ENTITES;
    }
}
