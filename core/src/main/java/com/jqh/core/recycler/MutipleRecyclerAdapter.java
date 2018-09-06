package com.jqh.core.recycler;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.jqh.core.R;
import com.jqh.core.ui.banner.BannerCreate;

import java.util.ArrayList;
import java.util.List;

public class MutipleRecyclerAdapter extends BaseMultiItemQuickAdapter<MultipleItemEntity,MultipleViewHolder>
implements BaseQuickAdapter.SpanSizeLookup,OnItemClickListener{

    // 确保初始化一次
    private boolean mIsInitBanner = false ;

    public static final RequestOptions RECYCLER_OPTIONS =
            new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .dontAnimate();

    public MutipleRecyclerAdapter(List<MultipleItemEntity> data) {
        super(data);
        init();
    }

    public static MutipleRecyclerAdapter create(List<MultipleItemEntity> data){
        return new MutipleRecyclerAdapter(data);
    }

    public static MutipleRecyclerAdapter create(DataConverter converter){
        return new MutipleRecyclerAdapter(converter.convert());
    }

    private void init(){
        // 设置不同的布局
        addItemType(ItemType.TEXT, R.layout.item_multiple_text);
        addItemType(ItemType.IMAGE, R.layout.item_multipe_image);
        addItemType(ItemType.TEXT_IMAGE, R.layout.item_multipe_image_text);
        addItemType(ItemType.BANNER, R.layout.item_multipe_banner);
        //设置宽度监听
        setSpanSizeLookup(this);
        // 打开动画
        openLoadAnimation();
        isFirstOnly(false);
    }

    @Override
    protected void convert(MultipleViewHolder helper, MultipleItemEntity item) {
        final String text ;
        final String imageUrl ;
        final ArrayList<String> bannerImages ;
        switch (helper.getItemViewType()){
            case ItemType.TEXT:
                text = item.getField(MultipleFields.TEXT);
                helper.setText(R.id.text_single,text);
                break;
                case ItemType.IMAGE:
                    imageUrl = item.getField(MultipleFields.IMAGE_URL);
                    Glide.with(mContext)
                            .load(imageUrl)
                            .apply(RECYCLER_OPTIONS)
                            .into((ImageView)helper.getView(R.id.img_single));
                    break;
                    case ItemType.BANNER:
                        if (!mIsInitBanner){
                            bannerImages = item.getField(MultipleFields.BANNERS);
                            final ConvenientBanner<String> convenientBanner = helper.getView(R.id.banner_recycler_item);
                            BannerCreate.setDefalut(convenientBanner,bannerImages,this);
                            mIsInitBanner = true ;
                        }
                        break;
                        case ItemType.TEXT_IMAGE:
                            text = item.getField(MultipleFields.TEXT);
                            imageUrl = item.getField(MultipleFields.IMAGE_URL);
                            Glide.with(mContext)
                                    .load(imageUrl)
                                    .apply(RECYCLER_OPTIONS)
                                    .into((ImageView)helper.getView(R.id.img_multipe));
                            helper.setText(R.id.tv_multipe,text);
                            break;
        }
    }

    @Override
    protected MultipleViewHolder createBaseViewHolder(View view) {
        return MultipleViewHolder.create(view);
    }

    @Override
    public int getSpanSize(GridLayoutManager gridLayoutManager, int position) {
        return getData().get(position).getField(MultipleFields.SPAN_SIZE);
    }

    @Override
    public void onItemClick(int position) {

    }
}
