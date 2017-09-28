package com.ifreeplay.airtravelers.utils;

import android.app.Application;
import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/9/28.
 */

public class AirTravelersApplication extends Application {
    public static Context appContext;
    public static Map<String, String> remindMap = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
    }

    public static Context getAppContext() {
        return appContext;
    }

}
