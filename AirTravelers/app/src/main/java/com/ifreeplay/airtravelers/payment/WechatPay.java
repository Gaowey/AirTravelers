package com.ifreeplay.airtravelers.payment;

import android.content.Context;

import com.google.gson.Gson;
import com.ifreeplay.airtravelers.bean.ResponseOrderParameters;
import com.ifreeplay.airtravelers.interfaces.HttpCallBackListener;
import com.ifreeplay.airtravelers.utils.Constants;
import com.ifreeplay.airtravelers.utils.HttpUtils;
import com.ifreeplay.airtravelers.utils.UrlConstants;
import com.ifreeplay.airtravelers.utils.WechatPayUtils;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lenovo on 2017/9/29.
 */

public class WechatPay {
    //调起统一下单接口
    public static void getOrderParameters(String orderNumber, final Context context){
        Map<String, String> map = new HashMap<>();
        map.put("orderNumber",orderNumber);
        HttpUtils.getDateFromServicer(UrlConstants.GETORDERPARAMETERS, map, HttpUtils.HttpMethod.POST, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {
                Gson gson = new Gson();
                ResponseOrderParameters responseOrderParameters = gson.fromJson(response, ResponseOrderParameters.class);
                //TODO 获取参数调起支付
                String appid = responseOrderParameters.getData().getAppid();
                String partnerid = responseOrderParameters.getData().getMch_id();
                String noncestr = responseOrderParameters.getData().getNonce_str();
                String prepayid = responseOrderParameters.getData().getPrepay_id();
                String timestamp = String.valueOf(System.currentTimeMillis());
                Map<String, String> map = new HashMap<>();
                map.put("appid",appid);
                map.put("partnerid",partnerid);
                map.put("prepayid",prepayid);
                map.put("package","Sign=WXPay");
                map.put("noncestr",noncestr);
                map.put("timestamp",timestamp);

                //TODO 商户秘钥需要放在服务端，所以生成sign在服务端完成
                try {
                    String sign = WechatPayUtils.generateSignature(map, Constants.WECHATMCHKEY);
                    //调起微信支付
                    IWXAPI wxapi = WXAPIFactory.createWXAPI(context, appid);
                    wxapi.registerApp(appid);
                    PayReq request = new PayReq();
                    request.appId = appid;
                    request.partnerId = partnerid;
                    request.prepayId= prepayid;
                    request.packageValue = "Sign=WXPay";
                    request.nonceStr= noncestr;
                    request.timeStamp= timestamp;
                    request.sign= sign;
                    wxapi.sendReq(request);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(Exception e) {

            }
        });
    }
}
