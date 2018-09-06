package com.jqh.core.deletegates.imagebrowse;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.bumptech.glide.Glide;
import com.jqh.core.R;
import com.jqh.core.deletegates.JqhDelegate;

import java.util.ArrayList;
import java.util.List;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

public class ImageBrowseDeleagte extends JqhDelegate {

    private ViewPager mViewPager ;

    private ArrayList<View> pageViewList ;
    private List<String> imageArr ;
    private int curPosition = 0 ;

    private static final String ARGS_POSITION = "ARGS_POSITION";
    private static final String ARGS_IMAGE_LIST = "ARGS_IMAGE_LIST";
    public static ImageBrowseDeleagte create(int position, ArrayList<String> imageList){
        Bundle args = new Bundle();
        args.putStringArrayList(ARGS_IMAGE_LIST,imageList);
        args.putInt(ARGS_POSITION,position);
        ImageBrowseDeleagte deleagte = new ImageBrowseDeleagte();
        deleagte.setArguments(args);
        return deleagte ;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        curPosition = args.getInt(ARGS_POSITION);
        imageArr = args.getStringArrayList(ARGS_IMAGE_LIST);
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_image_browse;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        mViewPager = rootView.findViewById(R.id.vp_image_list_broswer);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        pageViewList = new ArrayList<>();
        for(String imgUrl:imageArr){
            PhotoView view = new PhotoView(getContext());
            Glide.with(getContext())
                    .load(imgUrl)
                    .into(view);
            pageViewList.add(view);

            // 单击退出
//            view.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
//                @Override
//                public void onPhotoTap(View view, float v, float v1) {
//                    pop();
//                }
//            });
        }

        //绑定适配器
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setCurrentItem(curPosition);

    }


    //数据适配器
    PagerAdapter mPagerAdapter = new PagerAdapter(){

        @Override
        //获取当前窗体界面数
        public int getCount() {
            // TODO Auto-generated method stub
            return pageViewList.size();
        }

        @Override
        //断是否由对象生成界面
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0==arg1;
        }
        //是从ViewGroup中移出当前View
        public void destroyItem(View arg0, int arg1, Object arg2) {
            PhotoView photoView = (PhotoView) pageViewList.get(arg1);
          //  photoView.setOnPhotoTapListener(null);
            ((ViewPager) arg0).removeView(pageViewList.get(arg1));
        }

        //返回一个对象，这个对象表明了PagerAdapter适配器选择哪个对象放在当前的ViewPager中
        public Object instantiateItem(View arg0, int arg1){
            PhotoView photoView = (PhotoView) pageViewList.get(arg1);
            photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
                @Override
                public void onPhotoTap(View view, float v, float v1) {
                    pop();
                }
            });
            ((ViewPager)arg0).addView(pageViewList.get(arg1));
            return pageViewList.get(arg1);
        }


    };
}
