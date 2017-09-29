package com.ifreeplay.airtravelers.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.ifreeplay.airtravelers.R;
import com.ifreeplay.airtravelers.interfaces.PaypalPayCallBackListener;
import com.ifreeplay.airtravelers.payment.PaypalPay;
import com.ifreeplay.airtravelers.payment.WechatPay;
import com.ifreeplay.airtravelers.utils.AndroidUtils;
import com.ifreeplay.airtravelers.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class PaymentTestActivity extends AppCompatActivity {

    private TextView mWechatPay;
    private TextView mPaypalPay;
    private TextView mGooglePay;
    private long orderNumber;
    private int totalPrice;
    private String currencyTypes;
    private String productName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        initData();
        PaypalPay.init(this, Constants.PAYPAL_CLIENT_ID,PaypalPay.Environment.ENVIRONMENT_SANDBOX);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mWechatPay = (TextView) findViewById(R.id.tv_wechatPay);
        mPaypalPay = (TextView) findViewById(R.id.tv_paypalPay);
        mGooglePay = (TextView) findViewById(R.id.tv_googlePay);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        //获取订单属性
        orderNumber = getIntent().getLongExtra("orderNumber",0);
        totalPrice = getIntent().getIntExtra("totalPrice",0);
        currencyTypes = getIntent().getStringExtra("currencyTypes");
        productName = getIntent().getStringExtra("productName");

        //微信支付
        mWechatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WechatPay.pay(String.valueOf(orderNumber),PaymentTestActivity.this);
            }
        });

        //paypal支付
        mPaypalPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PaypalPay.pay(orderNumber, totalPrice, currencyTypes, productName, new PaypalPayCallBackListener() {
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

        //googlePay
        mGooglePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        PaypalPay.onActivityResult(requestCode,resultCode,data);

    }

    @Override
    protected void onDestroy() {
        PaypalPay.stopPaypalService();
        super.onDestroy();
    }
}
