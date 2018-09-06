package com.jqh.core.net;

import com.jqh.core.app.ConfigKeys;
import com.jqh.core.app.Jqh;

import java.util.ArrayList;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by jiangqianghua on 18/2/19.
 */

public class RestCreator {

    public static RestService getRestService(){
        return RestServiceHolder.REST_SERVICE ;
    }

    private static final class RestServiceHolder{
        private static final RestService REST_SERVICE =
                RetrofitHolder.RETROFIT_CLIENT.create(RestService.class);
    }

    private static final class RetrofitHolder{
        private static final String BASE_URL = (String) Jqh.getConfiguration(ConfigKeys.API_HOST);

        private static final Retrofit RETROFIT_CLIENT = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OKHttpHolder.OK_HTTP_CLIENT)
                .addConverterFactory(ScalarsConverterFactory.create())
                .build();
    }

    private static final class OKHttpHolder{
        private static final int TIME_OUT = 60 ;
//
//        private static final OkHttpClient OK_HTTP_CLIENT = new OkHttpClient.Builder()
//                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
//                .build();

        // 添加拦截器模拟请求
        private static final OkHttpClient.Builder BUILDER = new OkHttpClient.Builder();
        private static final ArrayList<Interceptor> INTERCEPTORS = (ArrayList<Interceptor>) Jqh.getConfiguration(ConfigKeys.INTERCEPTOR);

        private static final OkHttpClient OK_HTTP_CLIENT = addInterceptor()
                .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
                .build();

        private static OkHttpClient.Builder addInterceptor(){
            if(INTERCEPTORS != null && !INTERCEPTORS.isEmpty()){
                for(Interceptor interceptor : INTERCEPTORS){
                    BUILDER.addInterceptor(interceptor);
                }
            }
            return BUILDER;
        }

    }


    public static WeakHashMap<String,Object> getParams(){
        return ParamsHolder.PARAMS ;
    }

    private static final class ParamsHolder{
        private static final WeakHashMap<String,Object> PARAMS = new WeakHashMap<>();
    }
}
