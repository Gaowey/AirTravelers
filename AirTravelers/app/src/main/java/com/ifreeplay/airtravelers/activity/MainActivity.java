package com.ifreeplay.airtravelers.activity;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.google.gson.Gson;
import com.ifreeplay.airtravelers.R;
import com.ifreeplay.airtravelers.bean.ResponseOrder;
import com.ifreeplay.ifreeplaysdk.interfaces.FacebookShareListener;
import com.ifreeplay.ifreeplaysdk.interfaces.HttpCallBackListener;
import com.ifreeplay.ifreeplaysdk.interfaces.LogInStateListener;
import com.ifreeplay.ifreeplaysdk.login.LoginManager;
import com.ifreeplay.ifreeplaysdk.model.CreateOrder;
import com.ifreeplay.ifreeplaysdk.model.ViewPlayer;
import com.ifreeplay.ifreeplaysdk.share.FacebookShare;
import com.ifreeplay.ifreeplaysdk.share.LineShare;
import com.ifreeplay.ifreeplaysdk.share.WechatShare;
import com.ifreeplay.ifreeplaysdk.share.WhatsAppShare;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.AndroidUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.HttpUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.UrlConstants;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private TextView mConfirmOrder;
    private TextView mWechatLogin;
//    private TextView mLineLogin;
    private TextView mFaceBookLogin;
    private LoginManager loginManager;
    private TextView mFbSharePic;
    private CallbackManager callbackManager;
    private TextView mLineShareText;
    private TextView mLineSharePic;
    private TextView mFbShareUrl;
    private FacebookShare facebookShare;
    private TextView tvWechatShareText;
    private TextView tvWechatSharePic;
    private WechatShare wechatShare;
    private TextView tvWechatShareWeb;
    private TextView tvWhatsappShareText;
    private TextView tvWhatsappSharePic;
    private WhatsAppShare whatsAppShare;
    private TextView tvWhatsappShareNetPic;
    //    private LineShare lineShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化登录
        loginManager = LoginManager.initialize(this, 2);
        //初始化Facebook分享
        facebookShare = FacebookShare.initialize(this);
        //初始化微信分享
        wechatShare = WechatShare.getInstance(this,"wx5c8698af4ea9d013");

        //初始化whatsapp分享
        whatsAppShare = WhatsAppShare.initialize(this);

        //初始化line分享
//        lineShare = LineShare.initialize(this);
        initView();
        initData();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mConfirmOrder = findViewById(R.id.tv_confirmOrder);
        mWechatLogin = findViewById(R.id.tv_wechatlogin);
//        mLineLogin =  findViewById(R.id.tv_linelogin);
        mFaceBookLogin = findViewById(R.id.tv_facebooklogin);

        mFbShareUrl = findViewById(R.id.tv_fb_share_url);
        mFbSharePic = findViewById(R.id.tv_fb_share_pic);
        tvWechatShareText = findViewById(R.id.tv_wechat_share_text);
        tvWechatSharePic = findViewById(R.id.tv_wechat_share_pic);
        tvWechatShareWeb = findViewById(R.id.tv_wechat_share_web);
        tvWhatsappShareText = findViewById(R.id.tv_whatsapp_share_text);
        tvWhatsappSharePic = findViewById(R.id.tv_whatsapp_share_pic);
        tvWhatsappShareNetPic = findViewById(R.id.tv_whatsapp_share_net_pic);
//        mLineShareText = findViewById(R.id.tv_line_share_text);
//        mLineSharePic = findViewById(R.id.tv_line_share_pic);

//        ShareLinkContent content = new ShareLinkContent.Builder()
//                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.ifreeplay.airtravelers"))
//                .build();
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
                        AndroidUtils.shortToast(MainActivity.this,viewPlayer.getData().getName()+"---"+viewPlayer.getData().getWechatId());
                    }

                    @Override
                    public void OnLoginError(String s) {
                        AndroidUtils.shortToast(MainActivity.this,"failed");
                    }
                });
            }
        });

        //line登录
        /*mLineLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.initLineLogin();
                loginManager.setLineLoginParams(MainActivity.this, "1549136935", new LogInStateListener() {
                    @Override
                    public void OnLoginSuccess(ViewPlayer viewPlayer, String s) {
                        AndroidUtils.shortToast(MainActivity.this,viewPlayer.getData().getName()+"---"+viewPlayer.getData().getLineId());
                    }

                    @Override
                    public void OnLoginError(String s) {
                        AndroidUtils.shortToast(MainActivity.this,"failed");
                    }
                });
            }
        });*/


        //facebook登录
        mFaceBookLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginManager.initFaceBookLogin();
                loginManager.setFaceBookLoginParams(MainActivity.this, null, new LogInStateListener() {
                    @Override
                    public void OnLoginSuccess(ViewPlayer viewPlayer, String s) {
                        AndroidUtils.shortToast(MainActivity.this,viewPlayer.getData().getName()+"---"+viewPlayer.getData().getFacebookId());
                    }

                    @Override
                    public void OnLoginError(String s) {
                        AndroidUtils.shortToast(MainActivity.this,"failed");
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

        //facebook分享url
        mFbShareUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //应用在Google市场的详情
                String url="https://play.google.com/store/apps/details?id=com.ifreeplay.airtravelers";
                facebookShare.shareLinkUrl(url, FacebookShare.ShareType.MESSAGE, new FacebookShareListener() {
                    @Override
                    public void OnShareSuccess(Sharer.Result result) {
                    AndroidUtils.shortToast(MainActivity.this,"分享成功");
                    }

                    @Override
                    public void onShareCancel() {
                        AndroidUtils.shortToast(MainActivity.this,"取消分享");
                    }

                    @Override
                    public void OnShareError(FacebookException e) {
                        AndroidUtils.shortToast(MainActivity.this,"分享失败");
                    }
                });
            }
        });

        //Facebook分享图片
        mFbSharePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Bitmap> bitmaps = new ArrayList<>();
                Bitmap bitmapBj = BitmapFactory.decodeResource(getResources(), R.drawable.bj);
                Bitmap bitmapAsd = BitmapFactory.decodeResource(getResources(), R.drawable.asd);
                bitmaps.add(bitmapBj);
                bitmaps.add(bitmapAsd);
                facebookShare.shareImage(bitmaps, FacebookShare.ShareType.NEWS_FEED, new FacebookShareListener() {
                    @Override
                    public void OnShareSuccess(Sharer.Result result) {
                        AndroidUtils.shortToast(MainActivity.this,"分享成功");
                    }

                    @Override
                    public void onShareCancel() {
                        AndroidUtils.shortToast(MainActivity.this,"取消分享");
                    }

                    @Override
                    public void OnShareError(FacebookException e) {
                        AndroidUtils.shortToast(MainActivity.this,"分享失败");
                    }
                });
            }
        });

        tvWechatShareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WechatShare.ShareContentText shareContentText = (WechatShare.ShareContentText) wechatShare.getShareContentText("测试");
                wechatShare.shareByWebchat(shareContentText, WechatShare.WECHAT_SHARE_TYPE_TALK);
            }
        });
        tvWechatSharePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WechatShare.ShareContentPicture shareContentPicture= (WechatShare.ShareContentPicture) wechatShare.getShareContentPicture(R.mipmap.ic_launcher);
                wechatShare.shareByWebchat(shareContentPicture, WechatShare.WECHAT_SHARE_TYPE_TALK);
            }
        });
        tvWechatShareWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WechatShare.ShareContentWebpage shareContentWebpage= (WechatShare.ShareContentWebpage) wechatShare.getShareContentWebpag("分享测试","他大舅他二舅都是他舅，高桌子低板凳都是木头","www.baidu.com",R.mipmap.ic_launcher);
                wechatShare.shareByWebchat(shareContentWebpage, WechatShare.WECHAT_SHARE_TYPE_TALK);
            }
        });

        tvWhatsappShareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsAppShare.shareText("谁忘了，要给你温暖，我怀念的，我记得那年生日，也记得那一首歌");
            }
        });
        tvWhatsappSharePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                whatsAppShare.shareLocalImage();
            }
        });
        tvWhatsappShareNetPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, 101)){
                        try {
                            whatsAppShare.shareNetImage("http://leapkids-dev.oss-cn-beijing.aliyuncs.com/course/cover/4779401786cd45de94d032f105642ce5.jpg?x-oss-process=style/150_150");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            }
        });
    }

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{permission},100);
            return false;
        }else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){ //同意权限申请
            try {
                whatsAppShare.shareNetImage("http://leapkids-dev.oss-cn-beijing.aliyuncs.com/course/cover/4779401786cd45de94d032f105642ce5.jpg?x-oss-process=style/150_150");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else { //拒绝权限申请
            Toast.makeText(this,"权限被拒绝了",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //facebook与line登录的回调
        loginManager.onActivityResult(requestCode,resultCode,data);
        //Facebook分享的回调
        facebookShare.onActivityResult(requestCode,resultCode,data);
        //whatsApp分享手机图库图片回调
        whatsAppShare.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //使用微信登录添加
        loginManager.unRegister();
    }
}
