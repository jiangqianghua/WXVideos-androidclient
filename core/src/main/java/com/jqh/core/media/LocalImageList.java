package com.jqh.core.media;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * get local all images
 */
public class LocalImageList {

    public List<EntityImage> getList(Context context){
        List<EntityImage> entityImageList = new ArrayList<>();
        Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String[] projImage = { MediaStore.Images.Media._ID
                , MediaStore.Images.Media.DATA
                ,MediaStore.Images.Media.SIZE
                ,MediaStore.Images.Media.DISPLAY_NAME};
        final Cursor mCursor = context.getContentResolver().query(mImageUri,
                projImage,
                MediaStore.Images.Media.MIME_TYPE + "=? or " + MediaStore.Images.Media.MIME_TYPE + "=?",
                new String[]{"image/jpeg", "image/png"},
                MediaStore.Images.Media.DATE_MODIFIED+" desc");

        if(mCursor != null){
            while (mCursor.moveToNext()){
                // 获取图片的路径
                String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                int size = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Images.Media.SIZE))/1024;
                String displayName = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DISPLAY_NAME));
                EntityImage entityImage = new EntityImage(path,size,displayName);
                entityImageList.add(entityImage);
            }
            mCursor.close();
        }
        return entityImageList ;
    }

}
