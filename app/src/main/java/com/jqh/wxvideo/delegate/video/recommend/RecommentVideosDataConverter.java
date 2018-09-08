package com.jqh.wxvideo.delegate.video.recommend;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jqh.core.recycler.DataConverter;
import com.jqh.core.recycler.ItemType;
import com.jqh.core.recycler.MultipleFields;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.wxvideo.delegate.video.VideosItemType;

import java.util.ArrayList;

public class RecommentVideosDataConverter extends DataConverter {
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

            final MultipleItemEntity entity = MultipleItemEntity.builder()
                    .setItemType(VideosItemType.VIDEO_ITEM_RECOMMENT)
                    .setField(MultipleFields.IMAGE_URL,coverPath)
                    .build();
            ENTITES.add(entity);
        }
        return ENTITES;
    }
}
