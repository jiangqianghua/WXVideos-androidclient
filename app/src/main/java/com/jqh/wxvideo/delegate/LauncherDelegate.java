package com.jqh.wxvideo.delegate;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.Toast;

import com.jqh.core.bottom.BottomItemDelegate;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.ui.camera.CameraImageBean;
import com.jqh.core.ui.camera.CameraUtils;
import com.jqh.core.ui.camera.JqhCamera;
import com.jqh.core.ui.camera.RequestCodes;
import com.jqh.core.ui.photo.PictureSelector;
import com.jqh.core.ui.photo.RequestCode;
import com.jqh.core.util.callback.CallbackManager;
import com.jqh.core.util.callback.CallbackType;
import com.jqh.core.util.callback.IGlobalCallback;
import com.jqh.wxvideo.R;
import com.jqh.wxvideo.delegate.bottom.BottomDelegate;
import com.yalantis.ucrop.UCrop;

public class LauncherDelegate extends JqhDelegate {

    private AppCompatButton enterBtn ;
    @Override
    public Object setLayout() {
        return R.layout.delegate_launcher;
    }

    @Override
    public void onBindView(@Nullable Bundle saveInstanceState, View rootView) {
        enterBtn = rootView.findViewById(R.id.launcher_enter);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //    startWithPop(new BottomDelegate());
//                PictureSelector.create(_mActivity)
//                        .setSelectorType(PictureSelector.SELECTOR_TYPE_MEDIA)
//                        .forResult(1234)
//                        .start();
                //CameraUtils.openRecodCamera();
               // JqhCamera.start(LauncherDelegate.this);
                startCameraWithCheck();
            }
        });
    }

}
