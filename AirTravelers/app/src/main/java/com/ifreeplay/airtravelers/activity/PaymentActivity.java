package com.ifreeplay.airtravelers.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ifreeplay.airtravelers.R;
import com.ifreeplay.airtravelers.utils.WechatPayUtils;

public class PaymentActivity extends AppCompatActivity {

    private TextView mWechatPay;
    private TextView mPaypalPay;
    private TextView mGooglePay;
    private long orderNumber;
    private int totalPrice;
    private String currencyTypes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        initView();
        initData();
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

        //微信支付
        mWechatPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WechatPayUtils.getOrderParameters(String.valueOf(orderNumber));
            }
        });

        //paypal支付
        mPaypalPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //googlePay
        mGooglePay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
