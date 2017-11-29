package com.ifreeplay.airtravelers.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ifreeplay.airtravelers.R;
import com.ifreeplay.airtravelers.bean.ResponseOrder;
import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogInStateListener;
import com.ifreeplay.ifreeplaysdk.login.LoginManager;
import com.ifreeplay.ifreeplaysdk.model.CreateOrder;
import com.ifreeplay.ifreeplaysdk.model.ViewPlayer;
import com.ifreeplay.ifreeplaysdk.utils.payutils.AndroidUtils;
import com.ifreeplay.ifreeplaysdk.utils.payutils.HttpUtils;
import com.ifreeplay.ifreeplaysdk.utils.payutils.UrlConstants;

public class MainActivity extends AppCompatActivity{

    private TextView mConfirmOrder;
    private TextView mWechatLogin;
    private TextView mLineLogin;
    private TextView mFaceBookLogin;
    private LoginManager loginManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        loginManager = LoginManager.initialize(this, 2);
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mConfirmOrder = (TextView) findViewById(R.id.tv_confirmOrder);
        mWechatLogin = (TextView) findViewById(R.id.tv_wechatlogin);
        mLineLogin = (TextView) findViewById(R.id.tv_linelogin);
        mFaceBookLogin = (TextView) findViewById(R.id.tv_facebooklogin);

    }

    /**
     * 初始化数据
     */
    private void initData() {
        //微信登录
        mWechatLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.initWechatLogin();
                loginManager.setWechatLoginParams(MainActivity.this,"wx5c8698af4ea9d013", "6404466b271ee9732f15da181ed15ad1", "com.ifreeplay.airtravelers", new LogInStateListener() {
                    @Override
                    public void OnLoginSuccess(ViewPlayer viewPlayer, String s) {
                        //viewPlayer中获取玩家信息
                        AndroidUtils.shortToast(MainActivity.this,viewPlayer.getData().getName()+"---"+viewPlayer.getData().getHeadPortraitUrl());
                    }

                    @Override
                    public void OnLoginError(String s) {
                        AndroidUtils.shortToast(MainActivity.this,"failed");
                    }
                });
            }
        });

        //line登录
        mLineLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.initLineLogin();
                loginManager.setLineLoginParams(MainActivity.this, "1549136935", new LogInStateListener() {
                    @Override
                    public void OnLoginSuccess(ViewPlayer viewPlayer, String s) {
                        AndroidUtils.shortToast(MainActivity.this,viewPlayer.getData().getName()+"---"+viewPlayer.getData().getHeadPortraitUrl());
                    }

                    @Override
                    public void OnLoginError(String s) {
                        AndroidUtils.shortToast(MainActivity.this,"failed");
                    }
                });
            }
        });

        //facebook登录
        mFaceBookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.initFaceBookLogin();
                loginManager.setFaceBookLoginParams(MainActivity.this, null, new LogInStateListener() {
                    @Override
                    public void OnLoginSuccess(ViewPlayer viewPlayer, String s) {

                    }

                    @Override
                    public void OnLoginError(String s) {

                    }
                });
            }
        });


        //确认订单
        mConfirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateOrder createOrder = new CreateOrder();
                createOrder.setPlayerId(1);
                createOrder.setProductId(5);
                createOrder.setCurrencyTypes(CreateOrder.CurrencyTypes.CNY);
                createOrder.setSpbillCreateIp(AndroidUtils.getIPAddress(MainActivity.this));
                HttpUtils.postString(UrlConstants.CONFIRMORDER, new Gson().toJson(createOrder), new HttpCallBackListener() {
                    @Override
                    public void onFinish(String response) {
                        Gson gson = new Gson();
                        ResponseOrder responseOrder = gson.fromJson(response, ResponseOrder.class);
                        Intent intent = new Intent(MainActivity.this,PaymentTestActivity.class);
                        intent.putExtra("orderId",responseOrder.getData().getId());
                        intent.putExtra("totalPrice",responseOrder.getData().getTotalPrice());
                        intent.putExtra("currencyTypes",responseOrder.getData().getCurrencyTypes());
                        intent.putExtra("productName",responseOrder.getData().getProductName());
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Exception e) {
                        AndroidUtils.shortToast(MainActivity.this,"订单创建失败！");
                    }
                });
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        loginManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginManager.unRegister();
    }
}
