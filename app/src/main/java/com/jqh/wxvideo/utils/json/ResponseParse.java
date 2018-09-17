package com.jqh.wxvideo.utils.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class ResponseParse {
    public static final int STATUS_OK = 200 ;
    public static final int STATUS_TOKEN_ERR = 502;
    public static final int STATUS_EXCEPTION_ERR = 503;
    public static final int STATUS_ERR = 500 ;
    public static final int STATUS_BENA_ERR = 501;
    public static int getStatus(String response){
        JSONObject data = JSON.parseObject(response);
        int status = data.getInteger("status");
        return status ;
    }

    public static String getMsg(String response){
        JSONObject data = JSON.parseObject(response);
        String msg = data.getString("msg");
        return msg ;
    }
}
