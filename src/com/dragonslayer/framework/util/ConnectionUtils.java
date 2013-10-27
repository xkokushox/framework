package com.dragonslayer.framework.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class ConnectionUtils {

    private ConnectionUtils() {
    }
    
    public static boolean isNetworkAvailable(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(
                Context.CONNECTIVITY_SERVICE);
        final NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return ((networkInfo != null) && networkInfo.isConnected());
    }
    
}
