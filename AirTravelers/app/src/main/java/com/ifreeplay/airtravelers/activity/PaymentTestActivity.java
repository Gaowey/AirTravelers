package com.ifreeplay.airtravelers.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ifreeplay.airtravelers.R;
import com.ifreeplay.ifreeplaysdk.interfaces.PaymentCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.WechatPayCallBackListener;
import com.ifreeplay.ifreeplaysdk.payment.GooglePay;
import com.ifreeplay.ifreeplaysdk.payment.PaypalPay;
import com.ifreeplay.ifreeplaysdk.payment.WechatPay;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.AndroidUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentTestActivity extends AppCompatActivity {

    //private TextView mWechatPay;
    //private TextView mPaypalPay;
    private TextView mGooglePay;
    private int orderId;
    private int totalPrice;
    private String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        initData();
        //初始化相关支付信息
        //PaypalPay.init(this, "ATdJEC70AgF4ae_jIaK8WiVMzxBiarr-Whf1dJMAWbGm8IVQG57o28GA_5hLKvNFIH9vIoPqG13MLQ8T",PaypalPay.Environment.ENVIRONMENT_SANDBOX);
        GooglePay.init(this,"MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAqlg8aC8XnVfI+IePgNJ8moQHx2QG9j4I8DCg9e3xitmgMvTi1+HfEFioKyung9JiDy+SEOeObY6R/QD1o5varLVA5/FTC+JpOvlKdrD2syrHyatdL4jXiE2a4PzS+Yh1XqOdh/8U6yAzm1a36P6K6EqEYaL4SGCxJSX1OJUkhSCtHeGk888pASHChqh8jR7C2OEsO4T51HW6MSE67UYng1+NpnZBn8ueefo5uxEAIdGCOd6eoyxa7adem0W001UArC5BxJj7HNRU6bMTigfIeHaDXI2ceK51+/Gpo7y85LVDGODUHdBYnVX0nMRHYchIiodPGt5uTS6VKs889xDLkQIDAQAB");
    }

    /**
     * 初始化布局
     */
    private void initView() {
        //mWechatPay = (TextView) findViewById(R.id.tv_wechatPay);
        //mPaypalPay = (TextView) findViewById(R.id.tv_paypalPay);
        mGooglePay = (TextView) findViewById(R.id.tv_googlePay);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //获取订单详情
        orderId = getIntent().getIntExtra("orderId",0);
        totalPrice = getIntent().getIntExtra("totalPrice",0);
        productName = getIntent().getStringExtra("productName");

        //微信支付
        /*mWechatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WechatPay.pay(orderId, PaymentTestActivity.this, new WechatPayCallBackListener() {
                    @Override
                    public void onFinish(String netWrongMsg) {
                        AndroidUtils.shortToast(PaymentTestActivity.this,netWrongMsg);
                    }

                    @Override
                    public void onError(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });*/

        //paypal支付
        /*mPaypalPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaypalPay.pay(orderId, totalPrice, PaypalPay.CurrencyTypes.USD, productName, new PaymentCallBackListener() {
                    @Override
                    public void onFinish(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean data = (boolean) jsonObject.get("data");
                            int code = (int) jsonObject.get("code");
                            String message = (String) jsonObject.get("message");
                            if (data){
                                AndroidUtils.shortToast(PaymentTestActivity.this,"Payment successful!");
                            }else {
                                AndroidUtils.shortToast(PaymentTestActivity.this,"Payment failure!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        AndroidUtils.shortToast(PaymentTestActivity.this,e.toString());
                    }
                });
            }
        });*/

        //googlePay
        mGooglePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Google应用内支付有自己的productId,不同于我们自己系统的商品id，若应用接入Google支付需去Google开发者平台创建商品
                //然后获得productId,再与我们的管理系统相关联,最后在返回的order详情中会有Google的productId
                GooglePay.pay("121212", orderId, new PaymentCallBackListener() {
                    @Override
                    public void onFinish(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean data = (boolean) jsonObject.get("data");

                            if (data){
                                AndroidUtils.shortToast(PaymentTestActivity.this,"Payment successful!");
                            }else {
                                AndroidUtils.shortToast(PaymentTestActivity.this,"Payment failure!");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Exception e) {
                        AndroidUtils.shortToast(PaymentTestActivity.this,e.toString());
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //PaypalPay.onActivityResult(requestCode,resultCode,data);
        GooglePay.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        //PaypalPay.unbindService();
        GooglePay.unbindService();
        super.onDestroy();
    }
}
