package com.binyamin.trainme;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

class NetworkUtil {

        private static int TYPE_WIFI = 1;
        private static int TYPE_MOBILE = 2;
        private static int TYPE_NOT_CONNECTED = 0;

        private static int getConnectivityStatus(Context context) {
            ConnectivityManager cm = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);

            assert cm != null;
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (null != activeNetwork) {
                if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                    return TYPE_WIFI;

                if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                    return TYPE_MOBILE;
            }
            return TYPE_NOT_CONNECTED;
        }

        static String getConnectivityStatusString(Context context) {
            int conn = NetworkUtil.getConnectivityStatus(context);
            String status = null;
            if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
                status = "No Internet Connection";
            }
            return status;
        }
}
