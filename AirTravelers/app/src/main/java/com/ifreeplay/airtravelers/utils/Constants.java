package com.ifreeplay.airtravelers.utils;

/**
 * Created by lenovo on 2017/9/28.
 */

public class Constants {
    public enum SignType {
        MD5, HMACSHA256
    }

    public static final String HMACSHA256 = "HMAC-SHA256";
    public static final String MD5 = "MD5";
    public static final String WECHATAPPID = "wx5c8698af4ea9d013";
    public static final String WECHATSECRET = "6404466b271ee9732f15da181ed15ad1";
    public static final String WECHATMCHKEY = "Beijyoukonghuyu20170829ifreeplay";
    public static final String FIELD_SIGN_TYPE = "sign_type";//微信签名类型
    public static final int PAYPAL_REQUEST_CODE = 10101;//paypal请求码
    public static final int GOOGLE_REQUEST_CODE = 10001;//Google请求码
    public static final String PAYPAL_CLIENT_ID = "ATdJEC70AgF4ae_jIaK8WiVMzxBiarr-Whf1dJMAWbGm8IVQG57o28GA_5hLKvNFIH9vIoPqG13MLQ8T";
    public static final String GOOGLE_PUBLIC_KEY = "ATdJEC70AgF4ae_jIaK8WiVMzxBiarr-Whf1dJMAWbGm8IVQG57o28GA_5hLKvNFIH9vIoPqG13MLQ8T";

}
