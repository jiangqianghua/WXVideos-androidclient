package com.jqh.core.ui.camera;

import android.net.Uri;

public class CameraImageBean {

    private Uri mPath = null;

    private static final CameraImageBean INSTANCE = new CameraImageBean();

    public static CameraImageBean getInstance(){
        return INSTANCE;
    }

    public Uri getmPath() {
        return mPath;
    }

    public void setmPath(Uri mPath) {
        this.mPath = mPath;
    }
}
