package com.jqh.core.deletegates;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.jqh.core.ui.camera.CameraImageBean;
import com.jqh.core.ui.camera.JqhCamera;
import com.jqh.core.ui.camera.RequestCodes;
import com.jqh.core.util.callback.CallbackManager;
import com.jqh.core.util.callback.CallbackType;
import com.jqh.core.util.callback.IGlobalCallback;
import com.yalantis.ucrop.UCrop;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public abstract  class PermissionCheckerDelegate extends BaseDeletegate {

    //  不是直接调用的方法,系统会帮我们生成其他的方法调用
    @NeedsPermission(Manifest.permission.CAMERA)
    void startCamera(){
        JqhCamera.start(this);
    }
    // 这个才是是被调用的方法
    public void startCameraWithCheck(){
        // 该类是动态生成的
        //PermissionCheckerDelegate.startCameraWithCheck(this);
        PermissionCheckerDelegatePermissionsDispatcher.startCameraWithCheck(this);
    }
    @OnPermissionDenied(Manifest.permission.CAMERA)
    void onCameraDenied() {
        Toast.makeText(getContext(), "不允许拍照", Toast.LENGTH_LONG).show();
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    void onCameraNever() {
        Toast.makeText(getContext(), "永久拒绝权限", Toast.LENGTH_LONG).show();
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    void onCameraRationale(PermissionRequest request) {
        showRationaleDialog(request);
    }

    private void showRationaleDialog(final PermissionRequest request) {
        new AlertDialog.Builder(getContext())
                .setPositiveButton("同意使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.proceed();
                    }
                })
                .setNegativeButton("拒绝使用", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        request.cancel();
                    }
                })
                .setCancelable(false)
                .setMessage("权限管理")
                .show();
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
