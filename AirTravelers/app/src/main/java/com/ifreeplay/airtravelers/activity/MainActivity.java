package com.ifreeplay.airtravelers.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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
import com.ifreeplay.ifreeplaysdk.utils.payUtils.AndroidUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.HttpUtils;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.UrlConstants;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity{

    private TextView mConfirmOrder;
    private TextView mWechatLogin;
    private TextView mLineLogin;
    private TextView mFaceBookLogin;
    private LoginManager loginManager;
    private TextView mFbSharePic;
    private CallbackManager callbackManager;
    private TextView mLineShareText;
    private TextView mLineSharePic;
    private TextView mFbShareUrl;
    private FacebookShare facebookShare;
    private LineShare lineShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化登录
        loginManager = LoginManager.initialize(this, 2);
        //初始化Facebook分享
        facebookShare = FacebookShare.initialize(this);
        //初始化line分享
        lineShare = LineShare.initialize(this);
        initView();
        initData();
    }

    /**
     * 初始化布局
     */
    private void initView() {
        mConfirmOrder = (TextView) findViewById(R.id.tv_confirmOrder);
        mWechatLogin = (TextView) findViewById(R.id.tv_wechatlogin);
        mLineLogin = (TextView) findViewById(R.id.tv_linelogin);
        mFaceBookLogin = (TextView) findViewById(R.id.tv_facebooklogin);

        mFbShareUrl = (TextView) findViewById(R.id.tv_fb_share_url);
        mFbSharePic = (TextView) findViewById(R.id.tv_fb_share_pic);
        mLineShareText = (TextView) findViewById(R.id.tv_line_share_text);
        mLineSharePic = (TextView) findViewById(R.id.tv_line_share_pic);


        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse("https://play.google.com/store/apps/details?id=com.ifreeplay.airtravelers"))
                .build();
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
        mLineLogin.setOnClickListener(new View.OnClickListener() {
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
        });


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

        //line分享文本
        mLineShareText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineShare.shareText("测试文本...");
            }
        });

        //line分享图片
        mLineSharePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmapBj = BitmapFactory.decodeResource(getResources(), R.drawable.bj);
                ComponentName cn = new ComponentName("jp.naver.line.android"
                        , "jp.naver.line.android.activity.selectchat.SelectChatActivity");
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                String insertImage = MediaStore.Images.Media.insertImage(getContentResolver(), bitmapBj, null, null);
                Uri uri = Uri.parse(insertImage);
                shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                shareIntent.setType("image/png"); //图片分享
                shareIntent.setComponent(cn);
                startActivity(Intent.createChooser(shareIntent, "分享"));
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //facebook与line登录的回调
        loginManager.onActivityResult(requestCode,resultCode,data);
        //Facebook分享的回调
        facebookShare.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //使用微信分享添加
        loginManager.unRegister();
    }

    /*private String getResourcesUri(@DrawableRes int id) {
        Resources resources = getResources();
        String uriPath = ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                resources.getResourcePackageName(id) + "/" +
                resources.getResourceTypeName(id) + "/" +
                resources.getResourceEntryName(id);
        return uriPath;
    }


    public Bitmap getBitmap(String url) {
        Bitmap bm = null;
        try {
            URL iconUrl = new URL(url);
            URLConnection conn = iconUrl.openConnection();
            HttpURLConnection http = (HttpURLConnection) conn;

            int length = http.getContentLength();

            conn.connect();
            // 获得图像的字符流
            InputStream is = conn.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is, length);
            bm = BitmapFactory.decodeStream(bis);
            bis.close();
            is.close();// 关闭流
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return bm;
    }*/
}
