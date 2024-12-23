package com.example.ecommerce;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtils {
    /**
         *   Internet bağlantısını kontrol eder.
         * @param context
         * @return
         * Bağlantı varsa true yoksa false döner.
         */
        public static boolean isInternetAvailable (Context context){
            ConnectivityManager connectivityManager =
                    (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (connectivityManager == null){
                return false;
            }
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo == null){
                return   false;
            }
            return  networkInfo.isConnectedOrConnecting();
    }
}

