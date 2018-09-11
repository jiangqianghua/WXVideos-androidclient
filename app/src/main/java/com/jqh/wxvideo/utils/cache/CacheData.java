package com.jqh.wxvideo.utils.cache;

import com.blankj.utilcode.util.StringUtils;
import com.jqh.core.util.storage.JqhPreference;

public class CacheData {

    private final static String USER_ID = "USER_ID";
    private final static String TOKEN_ID = "TOKEN_ID";
    public static boolean isLogin(){
        String userId = JqhPreference.getCustomAppProfile(USER_ID);
        String tokenId = JqhPreference.getCustomAppProfile(TOKEN_ID);
        if(StringUtils.isEmpty(userId) || StringUtils.isEmpty(tokenId))
            return false;
        return true ;
    }

    public static String getUserId(){
        return JqhPreference.getCustomAppProfile(USER_ID) ;
    }

    public static String getTokenId(){
        return JqhPreference.getCustomAppProfile(TOKEN_ID);
    }

    public static void savaLoginToken(String id , String token){
        JqhPreference.addCustomAppProfile(USER_ID,id);
        JqhPreference.addCustomAppProfile(TOKEN_ID,token);
    }

}
