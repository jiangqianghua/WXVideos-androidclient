package com.jqh.core.net.calback;

import android.os.Handler;

import com.jqh.core.ui.loader.JqhLoader;
import com.jqh.core.ui.loader.LoaderStyle;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by jiangqianghua on 18/2/19.
 */

public class RequestCallBacks implements Callback<String> {
    private final IRequest REQUEST ;

    private final ISuccess SUCCESS ;

    private final IError ERROR ;

    private final IFailure FAILURE ;

    private final LoaderStyle LOADER_STYLE ;

    private static final Handler HANDLER = new Handler();

    public RequestCallBacks(IRequest REQUEST, ISuccess SUCCESS, IError ERROR, IFailure FAILURE, LoaderStyle style) {
        this.REQUEST = REQUEST;
        this.SUCCESS = SUCCESS;
        this.ERROR = ERROR;
        this.FAILURE = FAILURE;
        this.LOADER_STYLE = style;
    }

    @Override
    public void onResponse(Call<String> call, Response<String> response) {
        if(response.isSuccessful()){
            if(call.isExecuted()){
                if(SUCCESS != null){
                    SUCCESS.onSuccess(response.body());
                }
            }
        } else {
            if(ERROR != null){
                ERROR.onError(response.code(),response.message());
            }
        }
        stopLoading();

    }

    @Override
    public void onFailure(Call<String> call, Throwable t) {
        if(FAILURE != null){
            FAILURE.onFailure();
        }

        if(REQUEST != null){
            REQUEST.onRequestEnd();
        }
        stopLoading();
    }

    private void stopLoading(){
        if(LOADER_STYLE != null){
            HANDLER.postDelayed(new Runnable() {
                @Override
                public void run() {
                    JqhLoader.stopLoading();
                }
            },1000);
        }
    }
}
