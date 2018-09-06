package com.jqh.core.net.download;

import android.os.AsyncTask;

import com.jqh.core.net.RestCreator;
import com.jqh.core.net.calback.IError;
import com.jqh.core.net.calback.IFailure;
import com.jqh.core.net.calback.IRequest;
import com.jqh.core.net.calback.ISuccess;

import java.util.WeakHashMap;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jiangqianghua on 18/2/21.
 */

public class DownloadHandler {

    private final String URL ;

    private final WeakHashMap<String,Object> PARAMS = RestCreator.getParams() ;

    private final IRequest REQUEST ;

    private final ISuccess SUCCESS ;

    private final IError ERROR ;

    private final IFailure FAILURE ;

    private final String DOWNLOAD_DIR ;

    private final String EXTENSION ;

    private final String NAME;

    public DownloadHandler(String URL, IRequest REQUEST, ISuccess SUCCESS, IError ERROR, IFailure FAILURE, String DOWNLOAD_DIR, String EXTENSION, String NAME) {
        this.URL = URL;
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
        this.DOWNLOAD_DIR = DOWNLOAD_DIR;
        this.EXTENSION = EXTENSION;
        this.NAME = NAME;
    }

    public void handlerDownload(){

        if(REQUEST != null){
            REQUEST.onRequestStart();
        }

        RestCreator.getRestService().download(URL,PARAMS)
                .equals(new Callback<ResponseBody>(){
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            ResponseBody responseBody = response.body();
                            final SaveFileTask task = new SaveFileTask(REQUEST,SUCCESS);
                            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR,DOWNLOAD_DIR,EXTENSION,responseBody,NAME);
                            // 判断文件是否下载全
                            if(task.isCancelled()){
                                if(REQUEST != null){
                                    REQUEST.onRequestEnd();
                                }
                            }
                        }else{
                            if(ERROR != null){
                                ERROR.onError(response.code(),response.message());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        if(FAILURE != null){
                            FAILURE.onFailure();
                        }
                    }
                });
    }
}
