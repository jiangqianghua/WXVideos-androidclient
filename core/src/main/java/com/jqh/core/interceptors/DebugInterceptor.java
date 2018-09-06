package com.jqh.core.interceptors;

import android.support.annotation.RawRes;

import com.jqh.core.util.file.FileUtil;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Protocol;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DebugInterceptor extends BaseInterceptor {
    private final String DEBUG_URL;
    private final int DEBUG_RWAID ;

    public DebugInterceptor(String DEBUG_URL, int DEBUG_RWAID) {
        this.DEBUG_URL = DEBUG_URL;
        this.DEBUG_RWAID = DEBUG_RWAID;
    }

    private Response getResponse(Chain chain , String json){
        return new Response.Builder()
                .code(200)
                .addHeader("Content-Type","application/json")
                .body(ResponseBody.create(MediaType.parse("application/json"),json))
                .message("OK")
                .request(chain.request())
                .protocol(Protocol.HTTP_1_1)
                .build();
    }

    private Response debugResponse(Chain chain, @RawRes int rawId){
        final String json = FileUtil.getRawFile(rawId);
        return getResponse(chain,json);
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        final String url = chain.request().url().toString();
        if(url.contains(DEBUG_URL)){
            return debugResponse(chain,DEBUG_RWAID);
        }
        return chain.proceed(chain.request());
    }
}
