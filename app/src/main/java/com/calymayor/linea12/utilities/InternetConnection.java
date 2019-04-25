package com.calymayor.linea12.utilities;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by etorres on 02/01/17.
 */

public class InternetConnection {

    /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
    public static boolean checkConnection(Context context) {
        final ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetworkInfo = connMgr.getActiveNetworkInfo();

        if (activeNetworkInfo != null) { // connected to the internet
            Toast.makeText(context, activeNetworkInfo.getTypeName(), Toast.LENGTH_SHORT).show();

            if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            } else if (activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        return false;
    }

    public static boolean isNetworkAvailable(Context context) {
        Boolean isConnected = false;
        ConnectivityManager connectivity = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    System.out.println("Network State---" +info[i].getState());
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        isConnected = true;
                        Toast.makeText(context, "Internet is connected",
                                Toast.LENGTH_SHORT).show();
                    }else if (info[i].getState() == NetworkInfo.State.SUSPENDED){
                        isConnected = false;
                    }else if (info[i].getState() == NetworkInfo.State.DISCONNECTED){
                        isConnected = false;
                    }else if (info[i].getState() == NetworkInfo.State.UNKNOWN){
                        isConnected = false;
                    }
                    if(isConnected){
                        return true;
                    }


                }
            }
        }

        return false;
    }
    public static boolean isConnectedToInternet(Context context) {
        boolean isConnected = false;
        ConnectivityManager cm = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
            if (activeNetwork != null) {
                isConnected = activeNetwork.isConnectedOrConnecting();
                System.out.println("activeNetwork.isConnectedOrConnecting()" + activeNetwork.isConnectedOrConnecting());
            }
        }

        return isConnected;
    }

    public static boolean checkConnectivity(Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if((info==null)||!info.isConnected()||!info.isAvailable()){
            Toast.makeText(context, "No tienes internet!!!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
