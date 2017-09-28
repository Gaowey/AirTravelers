package com.ifreeplay.airtravelers.interfaces;

/**
 * Created by lenovo on 2017/9/28.
 */

public interface HttpCallBackListener {
    void onFinish(String response);

    void onError(Exception e);
}
