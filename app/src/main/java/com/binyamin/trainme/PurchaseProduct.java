package com.binyamin.trainme;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchaseHistoryRecord;
import com.android.billingclient.api.PurchaseHistoryResponseListener;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;

public class PurchaseProduct implements PurchasesUpdatedListener {
    Context context;
    BillingClient billingClient;
    String product;
    Activity activity;
    List<String> skuList = new ArrayList<>();
    SharedPreferences prefs;


    public PurchaseProduct(Context context, Activity activity, String product,SharedPreferences prefs){
        this.context = context;
        this.activity = activity;
        this.product = product;
        this.prefs = prefs;
    }
    public void setUp(){
    billingClient = BillingClient.newBuilder(context).enablePendingPurchases().setListener(new PurchasesUpdatedListener() {
        @Override
        //This method starts when user buys a product
        public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> list) {
            if (list != null && billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                for (Purchase purchase : list) {
                    handlePurchase(purchase);

                }
            } else if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.USER_CANCELED) {
                    Toast.makeText(context, "User Canceled - Try Purchasing Again", Toast.LENGTH_SHORT).show();
                    prefs.edit().putBoolean("ProductIsOwned", false).apply();

            } else if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
                    Toast.makeText(context, "Item Already Owned!", Toast.LENGTH_SHORT).show();
                    prefs.edit().putBoolean("ProductIsOwned", true).apply();

            }
        }
    }).build();

        billingClient.startConnection(new BillingClientStateListener() {
        @Override
        public void onBillingSetupFinished(BillingResult billingResult) {
            final String TAG = "OnBillingSetup";
            if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                //Toast.makeText(context, "Successfully connected to BillingClient", Toast.LENGTH_SHORT).show();

                billingClient.queryPurchaseHistoryAsync(BillingClient.SkuType.SUBS, new PurchaseHistoryResponseListener() {
                    @Override
                    public void onPurchaseHistoryResponse(BillingResult billingResult, List<PurchaseHistoryRecord> list) {
                        if (billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK && list != null) {
                            boolean productFound = false;
                            for (PurchaseHistoryRecord purchase : list) {
                                if (purchase.getSku().equals(product)) {
                                    productFound = true;
                                    prefs.edit().putBoolean("ProductIsOwned", true).apply();
                                    //Log.i(TAG,String.valueOf(prefs.getBoolean("ProductIsOwned",false)));
                                }
                            }
                            if (!productFound) {
                                    prefs.edit().putBoolean("ProductIsOwned", false).apply();
                                    //Log.i(TAG,String.valueOf(prefs.getBoolean("ProductIsOwned",false)));
                            }
                        }
                    }
                });

            } else {
                //Toast.makeText(context, "Failed to Connect", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onBillingServiceDisconnected() {
            //Toast.makeText(context, "Disconnected from BillingClient", Toast.LENGTH_SHORT).show();
        }
    });
        skuList.add(product);

    }

    public void query(){
        final SkuDetailsParams.Builder params = SkuDetailsParams.newBuilder();
        params.setSkusList(skuList).setType(BillingClient.SkuType.SUBS); // Subscription vs 1-Time Purchase
        billingClient.querySkuDetailsAsync(params.build(), new SkuDetailsResponseListener() {
            @Override
            public void onSkuDetailsResponse(BillingResult billingResult, List<SkuDetails> list) {
                if (list != null && billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK) {
                    for (final SkuDetails details : list) {

                        String sku = details.getSku(); //Your Product ID
                        String price = details.getPrice(); //Product Price
                        String description = details.getDescription(); //Product description

                        //Opens Pop-Up For Billing Purchase
                        BillingFlowParams billingFlowParams = BillingFlowParams.newBuilder()
                                .setSkuDetails(details)
                                .build();

                        BillingResult responseCode = billingClient.launchBillingFlow(activity, billingFlowParams);

                    }
                }
            }
        });
    }

    private void handlePurchase(Purchase purchase) {
        try {

            if (purchase.getPurchaseState() == Purchase.PurchaseState.PURCHASED) {
                if (purchase.getSku().equals(product)) {
                    ConsumeParams consumeParams = ConsumeParams.newBuilder()
                            .setPurchaseToken(purchase.getPurchaseToken())
                            .build();

                    ConsumeResponseListener consumeResponseListener = new ConsumeResponseListener() {
                        @Override
                        public void onConsumeResponse(BillingResult billingResult, String s) {
                            Toast.makeText(context, "Purchase Acknowledged", Toast.LENGTH_SHORT).show();
                        }
                    };
                    //billingClient.consumeAsync(consumeParams, consumeResponseListener);
                    //now purchase works again and again
                }
            }
        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onPurchasesUpdated(BillingResult billingResult, @Nullable List<Purchase> list) {
        Toast.makeText(context, "OnPurchases Updated", Toast.LENGTH_SHORT).show();
    }
}
