package com.jqh.core.ui.banner;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;

import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.jqh.core.recycler.MutipleRecyclerAdapter;

public class ImageHolder implements Holder<String> {

    private AppCompatImageView mImageView = null ;

    @Override
    public View createView(Context context) {
        mImageView = new AppCompatImageView(context);
        return mImageView;
    }

    @Override
    public void UpdateUI(Context context, int position, String data) {
        Glide.with(context)
                .load(data)
                .apply(MutipleRecyclerAdapter.RECYCLER_OPTIONS)
                .into(mImageView);
    }
}
