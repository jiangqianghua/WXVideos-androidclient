package com.jqh.core.ui.camera;

import android.net.Uri;

import com.jqh.core.deletegates.PermissionCheckerDelegate;
import com.jqh.core.util.file.FileUtil;

/**
 *相机调用
 */
public class JqhCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).benginCameraDialog();
    }
}
