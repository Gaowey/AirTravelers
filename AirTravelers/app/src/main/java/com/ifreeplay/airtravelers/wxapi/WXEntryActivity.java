package com.ifreeplay.airtravelers.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ifreeplay.airtravelers.activity.MainActivity;
import com.ifreeplay.ifreeplaysdk.utils.loginUtils.SPUtil;
import com.ifreeplay.ifreeplaysdk.utils.payUtils.AndroidUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

public class WXEntryActivity extends Activity implements IWXAPIEventHandler {
    private IWXAPI api;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, "wx5c8698af4ea9d013");
        api.handleIntent(getIntent(),this);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        String s="cdscds";
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                //用户拒绝授权
                AndroidUtils.shortToast(WXEntryActivity.this, "拒绝授权微信登录");
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                //用户取消
                AndroidUtils.shortToast(WXEntryActivity.this, "取消授权微信登录");
                finish();
                break;
            case BaseResp.ErrCode.ERR_OK:
                //用户换取access_token的code，仅在ErrCode为0时有效
                String code = ((SendAuth.Resp) baseResp).code;
                SPUtil.setString(WXEntryActivity.this,"WxCode",code);
                Intent intent = new  Intent();
                intent.setAction("authlogin");
                sendBroadcast(intent);
                finish();
                //发送广播传递code
                break;
        }

    }
}
