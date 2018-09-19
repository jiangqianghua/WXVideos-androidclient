package com.jqh.core.ui.camera;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blankj.utilcode.util.FileUtils;
import com.jqh.core.R;
import com.jqh.core.deletegates.PermissionCheckerDelegate;
import com.jqh.core.util.file.FileUtil;

import java.io.File;

/**
 * 照片处理类
 */
public class CameraHandler implements View.OnClickListener {

    private final AlertDialog DIALOG ;

    private final PermissionCheckerDelegate DELEGAT;

    public CameraHandler(PermissionCheckerDelegate delegate) {
        DIALOG = new AlertDialog.Builder(delegate.getContext()).create();
        this.DELEGAT = delegate;
    }

    public void benginCameraDialog(){
        DIALOG.show();

        final Window window = DIALOG.getWindow();
        if(window != null){
            window.setContentView(R.layout.dialog_camera_panel);
            window.setGravity(Gravity.BOTTOM);
            window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            final WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.WRAP_CONTENT;
            params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
            params.dimAmount = 0.5f;
            window.setAttributes(params);

            window.findViewById(R.id.photodialog_btn_cancel).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_native).setOnClickListener(this);
            window.findViewById(R.id.photodialog_btn_take).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        if(id == R.id.photodialog_btn_cancel){
            DIALOG.cancel();
        }else if(id == R.id.photodialog_btn_native){
            pickPhtot();
            DIALOG.cancel();
        }else if(id == R.id.photodialog_btn_take){
            takePhoto();
            DIALOG.cancel();
        }
    }

    private String getPhotoName(){
        return FileUtil.getFileNameByTime("IMG","jpg");
    }

    private void pickPhtot(){
        final Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        DELEGAT.getActivity().startActivityForResult(Intent.createChooser(intent,"选择获取的图片方式"),RequestCodes.PICK_PHOTO);
    }
    // 拍照
    private void takePhoto(){
        final String currentPhtotName = getPhotoName();
        final Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        final File tempFile = new File(FileUtil.CAMERA_PHOTO_DIR,currentPhtotName);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            // 兼容7.0以上
            final ContentValues contentValues = new ContentValues(1);
            contentValues.put(MediaStore.Images.Media.DATA,tempFile.getPath());
            final Uri uri = DELEGAT.getContext().getContentResolver()
                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,contentValues);
            // uri -> path
            final File realFile = FileUtils.getFileByPath(FileUtil.getRealFilePath(DELEGAT.getContext(),uri));
            final Uri readUri = Uri.fromFile(realFile);
            CameraImageBean.getInstance().setmPath(readUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        }else{
            final Uri fileUri = Uri.fromFile(tempFile);
            CameraImageBean.getInstance().setmPath(fileUri);
            intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        }
        DELEGAT.getActivity().startActivityForResult(intent,RequestCodes.TAKE_PHOTO);
    }
}
