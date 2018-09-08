package com.jqh.core.util.json;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public class JsonUtils {

    public static String converterToJson(Map<String,Object> map){
        JSONObject jsonObject = new JSONObject();
        for(String key:map.keySet()){
            jsonObject.put(key,map.get(key).toString());
        }
        return jsonObject.toJSONString();
    }
}
