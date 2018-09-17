package com.jqh.core.ui.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import java.lang.ref.WeakReference;

public class PictureSelector {

    public static final int SELECTOR_TYPE_IMAGES = 1 ;
    public static final int SELECTOR_TYPE_MEDIA = 2;
    private final WeakReference<Activity> mWeakActivity ;
    private int resultCode;
    private int maxSelectNum = 9;

    private int selectorType = SELECTOR_TYPE_IMAGES ;




    public static PictureSelector create(Activity activity){
        return new PictureSelector(activity);
    }

    public PictureSelector(Activity mActivity) {
        selectorType = SELECTOR_TYPE_IMAGES ;
        this.mWeakActivity =new WeakReference<>(mActivity);
    }

    public PictureSelector forResult(int resultCode){
        this.resultCode = resultCode ;
        return this;
    }

    public PictureSelector setSelectorType(int type){
        this.selectorType = type ;
        return this ;
    }

    public PictureSelector maxSelectNum(int maxSelectNum){
        this.maxSelectNum = maxSelectNum ;
        return this ;
    }

    public void start(){
        Activity activity = getActivity() ;
        if(activity == null)
            return ;
        Class clazz = getClazz();
        Intent intent = new Intent(activity,clazz);
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.MAX_SELECTOR_NUM,maxSelectNum);
        intent.putExtra(BundleKey.BUNDLE_KEY,bundle);
        getActivity().startActivityForResult(intent,resultCode);

    }

    private Class getClazz(){
        if(selectorType == SELECTOR_TYPE_IMAGES)
            return ImageSelectorActivity.class;
        else
            return MediaSelectorActivity.class;
    }

    private Activity getActivity(){
        return mWeakActivity.get();
    }

}
