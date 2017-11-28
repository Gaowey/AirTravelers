package com.ifreeplay.airtravelers.utils;

import android.app.Application;
import android.content.Context;


/**
 * Created by lenovo on 2017/9/28.
 */

public class AirTravelersApplication extends Application {
    public static Context appContext;
    //public static IWXAPI msgApi;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        initOkHttp();
    }

    private void initOkHttp() {
        /*OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10000L, TimeUnit.MILLISECONDS)
                .readTimeout(10000L, TimeUnit.MILLISECONDS)
                //其他配置
                .build();
        OkHttpUtils.initClient(okHttpClient);*/
    }

    public static Context getAppContext() {
        return appContext;
    }

}
