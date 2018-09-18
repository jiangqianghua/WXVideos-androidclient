package com.jqh.core.ui.photo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

public class PictureSelector {

    public static final int SELECTOR_TYPE_IMAGES = 1 ;
    public static final int SELECTOR_TYPE_MEDIA = 2;
    private WeakReference<Activity> mWeakActivity ;
    private WeakReference<Fragment> mWeakFragment ;
    private int resultCode;
    private int maxSelectNum = 9;

    private int selectorType = SELECTOR_TYPE_IMAGES ;




    public static PictureSelector create(Activity activity){
        return new PictureSelector(activity);
    }

    public static PictureSelector create(Fragment fragment){
        return new PictureSelector(fragment);
    }

    public PictureSelector(Activity mActivity) {
        selectorType = SELECTOR_TYPE_IMAGES ;
        this.mWeakActivity =new WeakReference<>(mActivity);
    }

    public PictureSelector(Fragment fragment) {
        selectorType = SELECTOR_TYPE_IMAGES ;
        this.mWeakFragment =new WeakReference<>(fragment);
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
        if(activity != null)
            startForActivity();
        else{
            Fragment fragment = getFragment();
            if(fragment != null)
                startForFragment();
        }


    }

    private void startForActivity(){
        Activity activity = getActivity() ;
        if(activity == null)
            return ;
        Class clazz = getClazz();
        Intent intent = new Intent(activity,clazz);
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.MAX_SELECTOR_NUM,maxSelectNum);
        bundle.putInt(BundleKey.RESULT_CODE,resultCode);
        intent.putExtra(BundleKey.BUNDLE_KEY,bundle);
        if(selectorType == SELECTOR_TYPE_MEDIA)
            activity.startActivityForResult(intent,RequestCode.REQUESTCODE_MEDIA_SELECT);
        else
            activity.startActivityForResult(intent,RequestCode.REQUESTCODE_IMAGE_SELECT);
    }

    private void startForFragment(){
        Fragment fragment = getFragment();
        if(fragment == null)
            return ;
        Class clazz = getClazz();
        Intent intent = new Intent(fragment.getActivity(),clazz);
        Bundle bundle = new Bundle();
        bundle.putInt(BundleKey.MAX_SELECTOR_NUM,maxSelectNum);
        bundle.putInt(BundleKey.RESULT_CODE,resultCode);
        intent.putExtra(BundleKey.BUNDLE_KEY,bundle);
        if(selectorType == SELECTOR_TYPE_MEDIA)
            fragment.startActivityForResult(intent,RequestCode.REQUESTCODE_MEDIA_SELECT);
        else
            fragment.startActivityForResult(intent,RequestCode.REQUESTCODE_IMAGE_SELECT);
    }

    private Class getClazz(){
        if(selectorType == SELECTOR_TYPE_IMAGES)
            return ImageSelectorActivity.class;
        else
            return MediaSelectorActivity.class;
    }

    private Activity getActivity(){
        if(mWeakActivity == null)
            return null ;
        return mWeakActivity.get();
    }

    private Fragment getFragment(){
        if(mWeakFragment == null)
            return null ;
        return mWeakFragment.get();
    }

}
