package com.jqh.core.ui.dialog;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.jqh.core.R;


/**
 * Created by user on 2018/4/10.
 */
public abstract  class BaseParentDialog {
    protected Context mContext;
    protected Dialog dialog;
    private View mContentView;

    public BaseParentDialog(Context context) {
        this.mContext = context;
        dialog = new Dialog(context, loadStyle());
        mContentView = LayoutInflater.from(context).inflate(getLayoutId(), null, false);
        setContentView(mContentView);
        onBindView(mContentView);
    }

    protected abstract void onBindView(View rootView);

    public int loadStyle(){
        return R.style.baseparentdialog;
    }

    public abstract int getLayoutId();

    public void setContentView(View view) {
        dialog.setContentView(view);
    }

    public void setWidthAndHeight(int width, int height) {
        Window win = dialog.getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        if (params != null) {
            if(width == 0)
                params.width = ViewGroup.LayoutParams.WRAP_CONTENT;
            else
                params.width = width;//设置宽度
            if(height == 0)
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;//设置高度
            else
                params.height = height;
            params.x = 0;
            params.y = 0;
            params.gravity = Gravity.CENTER;//设置显示在中间
            win.setAttributes(params);
        }
    }

    public void setPosition(int x, int y){
        Window win = dialog.getWindow();
        WindowManager.LayoutParams params = win.getAttributes();
        params.x = 50;
        params.y = 200;
        params.gravity = Gravity.TOP|Gravity.RIGHT;//设置显示在中间
        win.setAttributes(params);

    }

    public void show() {
        dialog.show();
    }

    public void hide() {
        dialog.hide();
    }

    protected <T extends View> T bindViewId(int resId) {
        return (T) mContentView.findViewById(resId);
    }
}