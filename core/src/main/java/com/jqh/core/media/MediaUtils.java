package com.jqh.core.media;

import java.util.HashMap;

public class MediaUtils {

    public static void  fillVideWH(String mUri,EntityVideo entityVideo)
    {
        android.media.MediaMetadataRetriever mmr = new android.media.MediaMetadataRetriever();
        try {
            if (mUri != null)
            {
                HashMap<String, String> headers = null;
                if (headers == null)
                {
                    headers = new HashMap<String, String>();
                    headers.put("User-Agent", "Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; MW-KW-001 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/1.0.0.001 U4/0.8.0 Mobile Safari/533.1");
                }
                mmr.setDataSource(mUri, headers);
            } else
            {
                //mmr.setDataSource(mFD, mOffset, mLength);
            }

            String duration = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_DURATION);//时长(毫秒)
            String width = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_WIDTH);//宽
            String height = mmr.extractMetadata(android.media.MediaMetadataRetriever.METADATA_KEY_VIDEO_HEIGHT);//高
            entityVideo.setWidth(Integer.parseInt(width));
            entityVideo.setHeight(Integer.parseInt(height));
            //  Toast.makeText(MainActivity.this, "playtime:"+ duration+"w="+width+"h="+height, Toast.LENGTH_SHORT).show();

        } catch (Exception ex)
        {
            //Log.e("TAG", "MediaMetadataRetriever exception " + ex);
        } finally {
            mmr.release();
        }

    }
}
