package com.binyamin.trainme;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NetworkConnectionCheck extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String status = NetworkUtil.getConnectivityStatusString(context);

        if(status != null){
            Toast.makeText(context, status, Toast.LENGTH_SHORT).show();
        }
    }
}
