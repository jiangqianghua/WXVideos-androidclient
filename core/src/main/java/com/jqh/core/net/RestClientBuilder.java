package com.jqh.core.net;

import android.content.Context;

import com.jqh.core.net.calback.IError;
import com.jqh.core.net.calback.IFailure;
import com.jqh.core.net.calback.IRequest;
import com.jqh.core.net.calback.ISuccess;
import com.jqh.core.ui.loader.LoaderStyle;

import java.io.File;
import java.util.Map;
import java.util.WeakHashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by jiangqianghua on 18/2/19.
 */

public class RestClientBuilder {
    private  String mUrl ;

    private  static WeakHashMap<String,Object> PARAMS = RestCreator.getParams() ;

    private  WeakHashMap<String,String> HEADERS = new WeakHashMap<>() ;

    private IRequest mIRequest ;

    private ISuccess mISuccess ;

    private IError mIError ;

    private IFailure mIFailure ;

    private  RequestBody mBody ;

    private LoaderStyle loaderStyle ;

    private Context context;

    private File mFile ;

    private  String mDownoad_dir ;

    private  String mExtenion ;

    private  String mName;

    public RestClientBuilder(){

    }


    public final RestClientBuilder url(String url){
        this.mUrl = url;
        return this;
    }

    public final RestClientBuilder params(WeakHashMap<String,Object> params){
        this.PARAMS.putAll(params);
        return this;
    }

    public final RestClientBuilder params(String key , Object value){
        PARAMS.put(key,value);
        return this;
    }

    public final RestClientBuilder headers(String key,String value){
        HEADERS.put(key,value);
        return this ;
    }

    public final RestClientBuilder raw(String raw){
        this.mBody = RequestBody.create(MediaType.parse("application/json;charset=UTF-8"),raw);
        return this;
    }

    public final RestClientBuilder success(ISuccess iSuccess){
        this.mISuccess = iSuccess ;
        return this;
    }


    public final RestClientBuilder failure(IFailure iFailure){
        this.mIFailure = iFailure ;
        return this;
    }

    public final RestClientBuilder error(IError iError){
        this.mIError = iError ;
        return this;
    }

    public final RestClientBuilder file(File file){
        this.mFile = file ;
        return this;
    }

    public final RestClientBuilder file(String file){
        this.mFile = new File(file) ;
        return this;
    }

    public final RestClientBuilder request(IRequest iRequest){
        this.mIRequest = iRequest ;
        return this;
    }

    public final RestClientBuilder loader(Context context, LoaderStyle style){
        this.context = context ;
        this.loaderStyle = style ;
        return this ;
    }

    public final RestClientBuilder loader(Context context){
        this.context = context ;
        this.loaderStyle = LoaderStyle.BallClipRotateIndicator ;
        return this ;
    }

    public final RestClientBuilder dir(String dir){
        this.mDownoad_dir = dir ;
        return this ;
    }

    public final RestClientBuilder extension(String extension){
        this.mExtenion = extension ;
        return this;
    }

    public final RestClientBuilder name(String name){
        this.mName = name ;
        return this ;
    }

    public final RestClient build(){
        return new RestClient(mUrl,PARAMS,mIRequest,mISuccess,mIError,mIFailure,mBody,context,loaderStyle,mFile,mDownoad_dir,mExtenion,mName,HEADERS);
    }

}
