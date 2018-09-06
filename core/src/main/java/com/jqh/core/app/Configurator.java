package com.jqh.core.app;

import android.app.Activity;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.joanzapata.iconify.IconFontDescriptor;
import com.joanzapata.iconify.Iconify;
import com.jqh.core.deletegates.web.event.Event;
import com.jqh.core.deletegates.web.event.EventManager;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Interceptor;

/**
 * 全局配置
 */
public final class Configurator {

    private static final HashMap<Object,Object> JQH_CONFIGS = new HashMap<>();
    private static final Handler HANDLER = new Handler();
    private static final ArrayList<IconFontDescriptor> ICONS = new ArrayList<>();
    private static final ArrayList<Interceptor> INTERCEPTORS = new ArrayList<>();

    private Configurator(){
        JQH_CONFIGS.put(ConfigKeys.CONFIG_READY,false);
        JQH_CONFIGS.put(ConfigKeys.HANDLER,HANDLER);
    }

    public final void configure(){
        initIcons();
        Logger.addLogAdapter(new AndroidLogAdapter());
        JQH_CONFIGS.put(ConfigKeys.CONFIG_READY,true);
    }

    private void initIcons(){
        if(ICONS.size() > 0){
            final Iconify.IconifyInitializer initializer = Iconify.with(ICONS.get(0));
            for(int i = 1 ; i < ICONS.size() ; i++){
                initializer.with(ICONS.get(i));
            }
        }
    }

    private static class Holder{
        private static final Configurator INSTANCE = new Configurator();
    }
    static Configurator getInstance(){
        return Holder.INSTANCE;
    }

    final  HashMap<Object,Object> getJqhConfigs(){
        return JQH_CONFIGS ;
    }

    public final Configurator withApiHost(String host){
        JQH_CONFIGS.put(ConfigKeys.API_HOST,host);
        return this;
    }

    public final Configurator withLoaderDelayed(long delayed){
        JQH_CONFIGS.put(ConfigKeys.LOADER_DELAYED,delayed);
        return this;
    }

    public final Configurator withIcon(IconFontDescriptor descriptor){
        ICONS.add(descriptor);
        return this ;
    }

    public final Configurator withInterceptor(Interceptor interceptor){
        INTERCEPTORS.add(interceptor);
        JQH_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this ;
    }

    public final Configurator withInterceptors(ArrayList<Interceptor> interceptors){
        INTERCEPTORS.addAll(interceptors);
        JQH_CONFIGS.put(ConfigKeys.INTERCEPTOR,INTERCEPTORS);
        return this ;
    }

    public final Configurator withChatAppId(String appId){
        JQH_CONFIGS.put(ConfigKeys.WE_CHAT_APP_ID,appId);
        return this ;
    }

    public final Configurator withChatAppSecret(String appSecret){
        JQH_CONFIGS.put(ConfigKeys.WE_CHAT_APP_SECRET,appSecret);
        return this ;
    }

    public final Configurator withActivity(Activity activity){
        JQH_CONFIGS.put(ConfigKeys.ACTIVITY,activity);
        return this ;
    }
    public Configurator withJavascriptInterface(@NonNull String name){
        JQH_CONFIGS.put(ConfigKeys.JAVASCRIPT_INTERFACE, name);
        return this;
    }

    public Configurator withWebEvent(@NonNull String name, @NonNull Event event){
        final EventManager manager = EventManager.getInstance();
        manager.addEvent(name,event);
        return this;
    }

    private void checkConfiguration(){
        final boolean isReady = (boolean)JQH_CONFIGS.get(ConfigKeys.CONFIG_READY);
        if(!isReady){
            throw  new RuntimeException("Configuration is not ready , all configure");
        }
    }

    final <T> T getConfiguration(Object key){
        checkConfiguration();
        final  Object value = JQH_CONFIGS.get(key);
        if(value == null)
            throw  new NullPointerException(key.toString() + " is null");
        return (T) JQH_CONFIGS.get(key);
    }





}
