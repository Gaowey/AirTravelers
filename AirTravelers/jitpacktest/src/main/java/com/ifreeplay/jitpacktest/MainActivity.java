package com.ifreeplay.jitpacktest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogInStateListener;
import com.ifreeplay.ifreeplaysdk.interfaces.PaymentCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.WechatPayCallBackListener;
import com.ifreeplay.ifreeplaysdk.login.LoginManager;
import com.ifreeplay.ifreeplaysdk.model.ViewPlayer;
import com.ifreeplay.ifreeplaysdk.payment.GooglePay;
import com.ifreeplay.ifreeplaysdk.payment.PaypalPay;
import com.ifreeplay.ifreeplaysdk.payment.WechatPay;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.AndroidUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.HttpUtils;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView mFindProductList = findViewById(R.id.tv_findProductList);


        final Map<String, String> map = new HashMap<>();
        map.put("gameId","2");
        mFindProductList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpUtils.getDateFromServicer("http://192.168.0.114:8081/product/findByGameId", map, HttpUtils.HttpMethod.GET, new HttpCallBackListener() {
                    @Override
                    public void onFinish(String s) {
                        AndroidUtils.shortToast(MainActivity.this,s);
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        });


        LoginManager loginManager = LoginManager.initialize(MainActivity.this, 2);

        //微信登录
        loginManager.initWechatLogin();
        loginManager.setWechatLoginParams(MainActivity.this,"","", "",new LogInStateListener() {
            @Override
            public void OnLoginSuccess(ViewPlayer viewPlayer, String s) {

            }

            @Override
            public void OnLoginError(String s) {

            }
        });

        //Facebook登录
        loginManager.initFaceBookLogin();
        loginManager.setFaceBookLoginParams(MainActivity.this, null, new LogInStateListener() {
            @Override
            public void OnLoginSuccess(ViewPlayer viewPlayer, String s) {

            }

            @Override
            public void OnLoginError(String s) {

            }
        });


        //paypal支付
        PaypalPay.init(MainActivity.this,"", PaypalPay.Environment.ENVIRONMENT_SANDBOX);
        PaypalPay.pay(1,3, PaypalPay.CurrencyTypes.CNY,"房卡", new PaymentCallBackListener() {
            @Override
            public void onFinish(String s) {

            }

            @Override
            public void onError(Exception e) {

            }
        });


        //微信支付
        WechatPay.pay(1, MainActivity.this, new WechatPayCallBackListener() {
            @Override
            public void onFinish(String s) {

            }

            @Override
            public void onError(Exception e) {

            }
        });


        //谷歌支付
        GooglePay.init(MainActivity.this,"");

        GooglePay.pay("121212", 1, new PaymentCallBackListener() {
            @Override
            public void onFinish(String s) {

            }

            @Override
            public void onError(Exception e) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        PaypalPay.onActivityResult(requestCode,resultCode,data);
        GooglePay.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PaypalPay.unbindService();
        GooglePay.unbindService();
    }
}
