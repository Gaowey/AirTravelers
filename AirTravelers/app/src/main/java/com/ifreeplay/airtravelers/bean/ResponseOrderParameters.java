package com.ifreeplay.airtravelers.bean;

/**
 * Created by lenovo on 2017/9/28.
 */

public class ResponseOrderParameters {

    /**
     * code : 0
     * message : Success
     * data : {"nonce_str":"gzLZh9XYfipc2lAw","appid":"wx5c8698af4ea9d013","sign":"7CF0692CDC969C272E5019F9F8BC7953","trade_type":"APP","return_msg":"OK","result_code":"SUCCESS","mch_id":"1488162712","return_code":"SUCCESS","prepay_id":"wx201709281851567a26c4e2480477114999"}
     */

    private int code;
    private String message;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * nonce_str : gzLZh9XYfipc2lAw  //随机字符串
         * appid : wx5c8698af4ea9d013
         * sign : 7CF0692CDC969C272E5019F9F8BC7953
         * trade_type : APP
         * return_msg : OK
         * result_code : SUCCESS
         * mch_id : 1488162712  //商户号
         * return_code : SUCCESS
         * prepay_id : wx201709281851567a26c4e2480477114999
         */

        private String nonce_str;
        private String appid;
        private String sign;
        private String trade_type;
        private String return_msg;
        private String result_code;
        private String mch_id;
        private String return_code;
        private String prepay_id;

        public String getNonce_str() {
            return nonce_str;
        }

        public void setNonce_str(String nonce_str) {
            this.nonce_str = nonce_str;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }

        public String getTrade_type() {
            return trade_type;
        }

        public void setTrade_type(String trade_type) {
            this.trade_type = trade_type;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }

        public String getResult_code() {
            return result_code;
        }

        public void setResult_code(String result_code) {
            this.result_code = result_code;
        }

        public String getMch_id() {
            return mch_id;
        }

        public void setMch_id(String mch_id) {
            this.mch_id = mch_id;
        }

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }
    }
}
