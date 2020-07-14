package com.binyamin.trainme;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_GetPremium extends Fragment implements PurchasesUpdatedListener {
    private BillingClient billingClient;
    private List skuList = new ArrayList();
    private String sku = "singlepurchase_premium_features";
    Button premiumButton;


    public Fragment_GetPremium() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        premiumButton = view.findViewById(R.id.premiumButton);
        premiumButton.setEnabled(false);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Boolean b = getBoolFromPref(getContext(),"itemPurchased",sku);
        if(b == true){
            Toast.makeText(getContext(),"You Are A Premium User",Toast.LENGTH_SHORT).show();
        }else{
            skuList.add(sku);
            setUpBillingClient();
        }

        return inflater.inflate(R.layout.fragment_get_premium, container, false);
    }

    private void setUpBillingClient() {
        billingClient = BillingClient.newBuilder(getContext()).enablePendingPurchases().setListener(this).build();
        billingClient.startConnection(new BillingClientStateListener() {
            @Override
            public void onBillingSetupFinished(@NonNull BillingResult billingResult) {
                if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                    //BillingClient is setup successfully
                    loadAllSkus();
                }
            }

            @Override
            public void onBillingServiceDisconnected() {
                //try to restart connection to BillingClient
                //by calling .startConnection() again.
            }
        });

    }


    private void loadAllSkus() {
        if(billingClient.isReady()){
            final SkuDetailsParams params = SkuDetailsParams.newBuilder()
                    .setSkusList(skuList)
                    .setType(BillingClient.SkuType.INAPP)
                    .build();
            billingClient.querySkuDetailsAsync(params, new SkuDetailsResponseListener() {
                @Override
                public void onSkuDetailsResponse(@NonNull BillingResult billingResult, @Nullable List<SkuDetails> list) {
                    if(billingResult.getResponseCode() == BillingClient.BillingResponseCode.OK){
                        for(Object skuDetailsObject : list){
                            final SkuDetails skuDetails = (SkuDetails) skuDetailsObject;
                            if(skuDetails.getSku().equals(sku)){
                                premiumButton.setEnabled(true);
                                premiumButton.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        BillingFlowParams params = BillingFlowParams
                                                .newBuilder()
                                                .setSkuDetails(skuDetails)
                                                .build();
                                        billingClient.launchBillingFlow(getActivity(),params);
                                    }
                                });
                            }
                        }
                    }
                }
            });
        }
    }

    @Override
    public void onPurchasesUpdated(@NonNull BillingResult billingResult, @Nullable List<Purchase> list) {
        int responseCode = billingResult.getResponseCode();
        if(responseCode == BillingClient.BillingResponseCode.OK && list != null ){
            //OK
            for (Purchase purchase : list) {
                handlePurchase(purchase);
                Toast.makeText(getContext(), "I/Purchasing", Toast.LENGTH_SHORT).show();

            }
        }else if(responseCode == BillingClient.BillingResponseCode.ITEM_ALREADY_OWNED) {
            //item laready owned
            Toast.makeText(getContext(), "I/Item ALready Owned", Toast.LENGTH_SHORT).show();
            setBoolInPref(getContext(),"itemPurchased",sku,true);
        }else if(responseCode == BillingClient.BillingResponseCode.USER_CANCELED){
            Toast.makeText(getContext(), "I/Canceled", Toast.LENGTH_SHORT).show();

        }
    }

    private void handlePurchase(Purchase purchase) {
        if(purchase.getSku().equals(sku)){
            setBoolInPref(getContext(),"itemPurchased",sku,true);
            Toast.makeText(getContext(),"You Are Now A Premium User",Toast.LENGTH_SHORT).show();
        }
    }

    private void setBoolInPref(Context context, String prefName, String constantName, boolean val){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(constantName,val).commit();
    }

    private Boolean getBoolFromPref(Context context, String prefName, String constantName){
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("com.binyamin.trainme",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(prefName,false);
    }
}
