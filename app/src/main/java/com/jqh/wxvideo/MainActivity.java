package com.jqh.wxvideo;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.jqh.core.activites.ProxyActivity;
import com.jqh.core.app.ISignListener;
import com.jqh.core.app.Jqh;
import com.jqh.core.deletegates.JqhDelegate;
import com.jqh.core.ui.camera.CameraImageBean;
import com.jqh.core.ui.camera.CameraUtils;
import com.jqh.core.ui.camera.JqhCamera;
import com.jqh.core.ui.camera.RequestCodes;
import com.jqh.core.util.callback.CallbackManager;
import com.jqh.core.util.callback.CallbackType;
import com.jqh.core.util.callback.IGlobalCallback;
import com.jqh.core.util.log.JqhLogger;
import com.jqh.wxvideo.delegate.LauncherDelegate;
import com.jqh.wxvideo.delegate.bottom.BottomDelegate;
import com.jqh.wxvideo.delegate.login.LoginDelegate;
import com.yalantis.ucrop.UCrop;

public class MainActivity extends ProxyActivity implements ISignListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.hide();
        }
        Jqh.getConfigurator().withActivity(this);
    }

    @Override
    public JqhDelegate setRootDelegate() {
        return new LauncherDelegate();
    }

    @Override
    public void onSignIn() {
        getSupportDelegate().startWithPop(new BottomDelegate());
    }

    @Override
    public void onSignUp() {
        getSupportDelegate().startWithPop(new LoginDelegate());
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode== CameraUtils.REQUEST_CODE_CAMERA_RECODER)
//        {
//            if(resultCode==RESULT_OK)
//            {
//                Toast.makeText(this, "Video saved to:\n" +
//                        data.getData(), Toast.LENGTH_LONG).show();
//                JqhLogger.d(data.getData());
////                vv_play.setVideoURI(fileUri);
////                vv_play.requestFocus();
//            }
//        }
//    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getmPath();
                    UCrop.of(resultUri,resultUri)  //  剪裁的图片替换新的图片
                            .withMaxResultSize(400,400)
                            .start(this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if(data != null){
                        final Uri pickPath = data.getData();
                        final String pickCropResult = JqhCamera.createCropFile().getPath();
                        UCrop.of(pickPath,Uri.parse(pickCropResult))
                                .withMaxResultSize(400,400)
                                .start(this);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    // 处理剪裁后的图片
                    //Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_LONG).show();
                    break;
                case RequestCodes.CROP_PHOTO:
                    final Uri cropUrl = UCrop.getOutput(data);
                    final IGlobalCallback<Uri> callback = CallbackManager.getInstance()
                            .getCallback(CallbackType.ON_CROP);
                    if(callback != null){
                        callback.executeCallback(cropUrl);
                    }
                    break;
            }
        }
    }
}
