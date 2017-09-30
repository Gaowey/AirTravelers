package com.ifreeplay.airtravelers.payment;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import com.ifreeplay.airtravelers.utils.AndroidUtils;
import com.ifreeplay.airtravelers.utils.Constants;
import com.ifreeplay.airtravelers.utils.googleUtils.IabHelper;
import com.ifreeplay.airtravelers.utils.googleUtils.IabResult;
import com.ifreeplay.airtravelers.utils.googleUtils.Inventory;
import com.ifreeplay.airtravelers.utils.googleUtils.Purchase;

import java.util.ArrayList;

/**
 * Created by lenovo on 2017/9/29.
 */

public class GooglePay {

    private static Context mContext;
    private static String mGooglePublicKey;
    private static IabHelper helper;

    public static void init(Context context, String googlePublicKey){
        mContext =context;
        mGooglePublicKey =googlePublicKey;
        helper = new IabHelper(context, googlePublicKey);
    }

    public static void pay(final String productId, final long orderNumber){
        //绑定服务
        helper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                    AndroidUtils.shortToast(mContext,"Google payment service binding failed!");
                    return;
                }
                // 服务绑定成功,查询商品
                ArrayList<String> productList = new ArrayList<>();
                productList.add(productId);
                helper.queryInventoryAsync(true, productList, new IabHelper.QueryInventoryFinishedListener() {
                            public void onQueryInventoryFinished(IabResult result, Inventory inventory)
                            {
                                if (result.isFailure()) {
                                    AndroidUtils.shortToast(mContext,"Commodity query failed!");
                                    return;
                                }
                                String price =inventory.getSkuDetails(productId).getPrice();
                                //查询成功，发起购买商品
                                helper.launchPurchaseFlow((Activity) mContext, productId, Constants.GOOGLE_REQUEST_CODE,
                                        new IabHelper.OnIabPurchaseFinishedListener() {
                                            public void onIabPurchaseFinished(IabResult result, Purchase purchase)
                                            {
                                                if (result.isFailure()) {
                                                    AndroidUtils.shortToast(mContext,"Failure to buy goods!");
                                                    return;
                                                }
                                                //购买成功

                                            }
                                        }, String.valueOf(orderNumber));
                            }
                        });
            }
        });
    }

    /**
     * 解绑谷歌支付服务
     */
    public static void unbindService(){
        if (helper != null) helper.dispose();
        helper = null;
    }
}
