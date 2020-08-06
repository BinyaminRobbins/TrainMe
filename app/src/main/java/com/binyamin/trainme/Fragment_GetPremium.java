package com.binyamin.trainme;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.billingclient.api.BillingClient;
import com.android.billingclient.api.BillingClientStateListener;
import com.android.billingclient.api.BillingFlowParams;
import com.android.billingclient.api.BillingResult;
import com.android.billingclient.api.ConsumeParams;
import com.android.billingclient.api.ConsumeResponseListener;
import com.android.billingclient.api.Purchase;
import com.android.billingclient.api.PurchasesUpdatedListener;
import com.android.billingclient.api.SkuDetails;
import com.android.billingclient.api.SkuDetailsParams;
import com.android.billingclient.api.SkuDetailsResponseListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_GetPremium extends Fragment {
    static ProgressBar premiumBar;

    public Fragment_GetPremium() {
        // Required empty public constructor
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        requireActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        final SharedPreferences prefs = requireContext().getSharedPreferences("com.binyamin.trainme", Context.MODE_PRIVATE);
//        getActivity().getActionBar().hide();

        String product = getResources().getString(R.string.productId);
        final PurchaseProduct purchaseProduct = new PurchaseProduct(getContext(),getActivity(),product,prefs);
        purchaseProduct.setUp();

        Button button = view.findViewById(R.id.premiumButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(prefs.getBoolean("ProductIsOwned", false)){
                    premiumBar.setVisibility(View.VISIBLE);
                    Toast.makeText(getContext(),"Product Is Already Owned",Toast.LENGTH_SHORT).show();
                    return;
                }
                purchaseProduct.query();
            }
        });

        premiumBar = view.findViewById(R.id.premiumBar);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_get_premium, container, false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getActivity().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
}