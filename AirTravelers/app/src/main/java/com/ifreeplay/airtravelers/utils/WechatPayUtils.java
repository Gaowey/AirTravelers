package com.ifreeplay.airtravelers.utils;

import com.google.gson.Gson;
import com.ifreeplay.airtravelers.bean.ResponseOrderParameters;
import com.ifreeplay.airtravelers.interfaces.HttpCallBackListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/9/28.
 */

public class WechatPayUtils {

    //调起统一下单接口
    public static void getOrderParameters(String orderNumber){
        Map<String, String> map = new HashMap<>();
        map.put("orderNumber",orderNumber);
        HttpUtils.getDateFromServicer(UrlConstants.GETORDERPARAMETERS, map, HttpUtils.HttpMethod.POST, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new Gson();
                ResponseOrderParameters responseOrderParameters = gson.fromJson(response, ResponseOrderParameters.class);
                //TODO 获取参数调起支付
                String appid = responseOrderParameters.getData().getAppid();
                String mch_id = responseOrderParameters.getData().getMch_id();
                String nonce_str = responseOrderParameters.getData().getNonce_str();
                String prepay_id = responseOrderParameters.getData().getPrepay_id();
                String sign = responseOrderParameters.getData().getSign();



            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
