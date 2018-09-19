package com.jqh.core.ui.camera;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.blankj.utilcode.util.Utils;
import com.jqh.core.app.ConfigKeys;
import com.jqh.core.app.Jqh;
import com.jqh.core.util.log.JqhLogger;

import java.io.File;
import java.io.IOException;

public class CameraUtils {

    public static final int REQUEST_CODE_CAMERA_RECODER = 123;
    public static void openRecodCamera(){
        Activity activity = Jqh.getConfiguration(ConfigKeys.ACTIVITY);
        Uri fileUri = null ;
        Intent intent=new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        try {

            //fileUri=Uri.fromFile(createMediaFile());如果这样写会报错
            fileUri= FileProvider.getUriForFile(activity,activity.getApplicationContext().getPackageName() + ".provider",createMediaFile());//这是正确的写法

        } catch (IOException e) {
            e.printStackTrace();
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT,fileUri);
        intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY,1);

        activity.startActivityForResult(intent,REQUEST_CODE_CAMERA_RECODER);
    }

    private static File createMediaFile() throws IOException {
        if(/**Utils.checkSDCardAvaliable() **/ true) {
            if ((Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))) {
                // 选择自己的文件夹
                String path = Environment.getExternalStorageDirectory().getPath() + "/myvideo/";
                // Constants.video_url 是一个常量，代表存放视频的文件夹
                File mediaStorageDir = new File(path);
                if (!mediaStorageDir.exists()) {
                    if (!mediaStorageDir.mkdirs()) {
                        JqhLogger.e("TAG", "文件夹创建失败");
                        return null;
                    }
                }

                // 文件根据当前的毫秒数给自己命名
                String timeStamp = String.valueOf(System.currentTimeMillis());
                timeStamp = timeStamp.substring(7);
                String imageFileName = "V" + timeStamp;
                String suffix = ".mp4";
                File mediaFile = new File(mediaStorageDir + File.separator + imageFileName + suffix);
                return mediaFile;
            }
        }
        return null;
    }
}
