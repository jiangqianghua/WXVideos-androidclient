package com.jqh.core.widget;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.joanzapata.iconify.widget.IconTextView;
import com.jqh.core.R;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.recycler.BaseDecoration;
import com.jqh.core.ui.camera.RequestCodes;
import com.lzy.imagepicker.ui.ImageGridActivity;

import java.util.ArrayList;
import java.util.List;

public class AutoPhotoLayout extends RecyclerView {

    private int mCurrentNum = 0;
    private final int mMaxNum;
    private final int mMaxLineNum;
    private IconTextView mIconAdd = null;
    private LayoutParams mParams = null;
    //要删除的图片ID
    private int mDeleteId = 0;
    private AppCompatImageView mTargetImageVew = null;
    private final int mImageMargin;
    private JqhDelegate mDelegate = null;
    private ArrayList<View> mLineViews = null;
    private AlertDialog mTargetDialog = null;
    private static final String ICON_TEXT = "{fa-plus}";
    private final float mIconSize;

    private final ArrayList<ArrayList<View>> ALL_VIEWS = new ArrayList<>();
    private final ArrayList<Integer> LINE_HEIGHTS = new ArrayList<>();

    private GridLayoutManager manager ;

    private PhotoLayoutAdapter adapter ;

    private ArrayList<ItemBean> itemBeans ;

    private ItemBean addItemBean ;

    private boolean isAddIteamHiden = false ;


    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_count, 1);
        mMaxLineNum = typedArray.getInt(R.styleable.camera_flow_layout_line_count, 3);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin, 0);
        mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size, 20);
        typedArray.recycle();
        initView();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTargetDialog = new AlertDialog.Builder(getContext()).create();
    }

    public final void setDelegate(JqhDelegate delegate) {
        this.mDelegate = delegate;
    }

    private void initView(){
        itemBeans = new ArrayList<>();
        addItemBean = new ItemBean(ItemBeanType.ITEMBEAN_TYPE_ADD,"");
        itemBeans.add(addItemBean);
        isAddIteamHiden = false ;

        manager = new GridLayoutManager(getContext(),3);

        this.setLayoutManager(manager);

        adapter = new PhotoLayoutAdapter(itemBeans);

        this.setAdapter(adapter);

        this.addItemDecoration(new BaseDecoration(Color.WHITE,5));
    }

    public void addImage(String url){
        int addPosition = 0 ;
        for(int i = 0 ; i < itemBeans.size() ; i++){
            if(addItemBean == itemBeans.get(i)){
                addPosition = i ;
            }
        }
        adapter.remove(addPosition);
        ItemBean itemBean = new ItemBean(ItemBeanType.ITEMBEAN_TYPE_IMG,url);
        adapter.addData(itemBean);
        if(adapter.getData().size() < mMaxNum) {
            adapter.addData(addItemBean);
            isAddIteamHiden = false;
        }
        else{
            isAddIteamHiden = true ;
        }

    }

    private class ItemBeanType{
        public static final int ITEMBEAN_TYPE_ADD = 0;
        public static final int ITEMBEAN_TYPE_IMG = 1 ;
    }

    private class ItemBean implements MultiItemEntity{
        private int type ;
        private String value ;

        public ItemBean(int type, String value) {
            this.type = type;
            this.value = value;
        }


        public String getValue() {
            return value;
        }

        public int getType() {
            return type;
        }

        @Override
        public int getItemType() {
            return type;
        }
    }

    private class PhotoLayoutAdapter extends BaseMultiItemQuickAdapter<ItemBean,BaseViewHolder>{
        public PhotoLayoutAdapter(List<ItemBean> data) {
            super(data);
            addItemType(ItemBeanType.ITEMBEAN_TYPE_ADD, R.layout.item_photo_add);
            addItemType(ItemBeanType.ITEMBEAN_TYPE_IMG, R.layout.item_photo_img);
        }

        @Override
        protected void convert(final BaseViewHolder helper, ItemBean item) {
            switch (helper.getItemViewType()){
                case ItemBeanType.ITEMBEAN_TYPE_ADD:
                    IconTextView iconTextView = helper.getView(R.id.item_tv_photo_ad);
                    iconTextView.setText(ICON_TEXT);
                    iconTextView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(mDelegate.getActivity(), ImageGridActivity.class);
                            mDelegate.getActivity().startActivityForResult(intent, RequestCodes.CROP_PHOTO);
                        }
                    });
                    break;
                case ItemBeanType.ITEMBEAN_TYPE_IMG:
                    String url = item.getValue();
                    AppCompatImageView imageView = helper.getView(R.id.item_iv_photo_image);
                    Glide.with(getContext())
                            .load(url)
                            .apply(OPTIONS)
                            .into(imageView);
                    imageView.setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mTargetDialog.show();
                            final Window window = mTargetDialog.getWindow();
                            if (window != null) {
                                window.setContentView(R.layout.dialog_image_click_panel);
                                window.setGravity(Gravity.BOTTOM);
                                window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
                                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                final WindowManager.LayoutParams params = window.getAttributes();
                                params.width = WindowManager.LayoutParams.MATCH_PARENT;
                                params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                                params.dimAmount = 0.5f;
                                window.setAttributes(params);
                                window.findViewById(R.id.dialog_image_clicked_btn_delete)
                                        .setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                //得到要删除的图片
                                                int position = helper.getAdapterPosition();
                                                adapter.remove(position);
                                                if(isAddIteamHiden)
                                                    adapter.addData(addItemBean);
                                                isAddIteamHiden = false ;
                                                mTargetDialog.cancel();
                                            }
                                        });
                                window.findViewById(R.id.dialog_image_clicked_btn_undetermined)
                                        .setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mTargetDialog.cancel();
                                            }
                                        });
                                window.findViewById(R.id.dialog_image_clicked_btn_cancel)
                                        .setOnClickListener(new OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                mTargetDialog.cancel();
                                            }
                                        });
                            }
                            mTargetDialog.show();
                        }
                    });
                    break;
            }
        }
    }

}
