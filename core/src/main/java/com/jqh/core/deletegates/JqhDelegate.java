package com.jqh.core.deletegates;

import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import com.jqh.core.ui.camera.CameraImageBean;
import com.jqh.core.ui.camera.JqhCamera;
import com.jqh.core.ui.camera.RequestCodes;
import com.jqh.core.util.callback.CallbackManager;
import com.jqh.core.util.callback.CallbackType;
import com.jqh.core.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

public abstract class JqhDelegate extends PermissionCheckerDelegate {
    // 获得父类的fragment
    public <T extends JqhDelegate> T getParentDelegate(){
        return (T) getParentFragment();
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case RequestCodes.TAKE_PHOTO:
                    final Uri resultUri = CameraImageBean.getInstance().getmPath();
                    UCrop.of(resultUri,resultUri)  //  剪裁的图片替换新的图片
                            .withMaxResultSize(400,400)
                            .start(getContext(),this);
                    break;
                case RequestCodes.PICK_PHOTO:
                    if(data != null){
                        final Uri pickPath = data.getData();
                        final String pickCropResult = JqhCamera.createCropFile().getPath();
                        UCrop.of(pickPath,Uri.parse(pickCropResult))
                                .withMaxResultSize(400,400)
                                .start(getContext(),this);
                    }
                    break;
                case RequestCodes.CROP_ERROR:
                    // 处理剪裁后的图片
                    Toast.makeText(getContext(), "剪裁出错", Toast.LENGTH_LONG).show();
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
