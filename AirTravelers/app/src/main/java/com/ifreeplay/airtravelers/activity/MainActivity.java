package com.ifreeplay.airtravelers.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ifreeplay.airtravelers.R;
import com.ifreeplay.airtravelers.bean.Credential;
import com.ifreeplay.airtravelers.bean.Order;
import com.ifreeplay.airtravelers.interfaces.HttpCallBackListener;
import com.ifreeplay.airtravelers.utils.AndroidUtils;
import com.ifreeplay.airtravelers.utils.HttpUtils;
import com.ifreeplay.airtravelers.utils.UrlConstants;

public class MainActivity extends AppCompatActivity{

    private TextView mAuthLogin;
    private TextView mConfirmOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mAuthLogin = (TextView) findViewById(R.id.tv_authLogin);
        mConfirmOrder = (TextView) findViewById(R.id.tv_confirmOrder);
    }

    /**
     * 初始化数据
     */
    private void initData() {
        mAuthLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Credential credential = new Credential();
                credential.setGameId(2);
                credential.setName("Gw");
                credential.setType(Credential.Type.WECHAT);
                credential.setWechatId("121212121");
                HttpUtils.postString(UrlConstants.AUTHLOGIN, new Gson().toJson(credential), new HttpCallBackListener() {
                    @Override
                    public void onFinish(String response) {
                        AndroidUtils.shortToast(MainActivity.this,"登录成功！");
                    }

                    @Override
                    public void onError(Exception e) {
                        AndroidUtils.shortToast(MainActivity.this,e.toString());
                    }
                });
            }
        });

        //确认订单
        mConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Order order = new Order();
                order.setGameId(2);
                order.setCurrencyTypes(Order.CurrencyTypes.CNY);
                order.setPlayerId(1);
                order.setDealPrice(30);
                order.setPrice(30);
                order.setTotalPrice(30);
                order.setProductId(5);
                order.setStatus(Order.Status.OPEN);
                order.setSpbillCreateIp(AndroidUtils.getIPAddress(MainActivity.this));
                HttpUtils.postString(UrlConstants.CONFIRMORDER, new Gson().toJson(order), new HttpCallBackListener() {
                    @Override
                    public void onFinish(String response) {
                        String str = response;
                    }

                    @Override
                    public void onError(Exception e) {
                        AndroidUtils.shortToast(MainActivity.this,e.toString());
                    }
                });
            }
        });

    }

}
