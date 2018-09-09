package com.jqh.wxvideo.delegate.video.videoplayer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.net.RestClient;
import com.jqh.core.net.calback.ISuccess;
import com.jqh.core.recycler.MultipleItemEntity;
import com.jqh.core.ui.viewpager.VerticalViewPager;
import com.jqh.wxvideo.R;

import java.util.ArrayList;

public class VideoPlayerDelegate extends JqhDelegate {
    private VerticalViewPager viewPager ;
    private FragmentPagerAdapter adapter ;
    private ArrayList<Fragment> mDatas = new ArrayList<>();
    @Override
    public Object setLayout() {
        return R.layout.delegate_videoplayer;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        viewPager = rootView.findViewById(R.id.view_video_pager);
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

                RestClient.builder()
                .loader(getContext())
                .url("video/showAll?page=0")
                .params("userId","")
                .params("videoDesc","")
                .success(new ISuccess() {
                    @Override
                    public void onSuccess(String response) {
                        ArrayList<MultipleItemEntity> datas = new VideoDataConverter().setJsonData(response).convert();
                        initData(datas);
                    }
                })
                .build().postJson();
    }

    private void initData(ArrayList<MultipleItemEntity> datas){
        for(int i = 0 ; i < datas.size(); i++){
            PlayVideoPageItemFragment fragment = PlayVideoPageItemFragment.newInstance(datas.get(i));
            mDatas.add(fragment);
        }
        adapter = new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mDatas.get(position);
            }

            @Override
            public int getCount() {
                return mDatas.size();
            }
        };
        viewPager.setAdapter(adapter);
    }
}
