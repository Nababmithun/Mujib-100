package com.ezzetech.mujib100;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.ezzetech.mujib100.interfaces.OnNetworkStateChangeListener;


public class NetworkChangeReceiver extends BroadcastReceiver {

    private OnNetworkStateChangeListener listener;

    public NetworkChangeReceiver() {
    }

    public NetworkChangeReceiver(OnNetworkStateChangeListener listener) {
        this.listener = listener;
    }

    @Override
    public void onReceive(Context context, Intent intent ) {
        try {

            if( isInternetOnCheck( context ) ) {
                listener.onChange(true);
            } else {
                listener.onChange(false);
            }
        } catch( NullPointerException e ) {
            e.printStackTrace();
        }
    }

    public boolean isInternetOnCheck( Context aContext ) {

        boolean aResult = false;
        ConnectivityManager aConnecMan = ( ConnectivityManager ) aContext.getSystemService( Context.CONNECTIVITY_SERVICE );
        NetworkInfo activeNetwork = aConnecMan.getActiveNetworkInfo();

        if( activeNetwork != null ) {
            boolean isConnected = activeNetwork.isConnected();
            boolean isWiFi = activeNetwork.getType() == ConnectivityManager.TYPE_WIFI;

            if( isConnected && isWiFi ) {

            }
        }

        if( ( aConnecMan.getNetworkInfo( 0 ).getState() == NetworkInfo.State.CONNECTED )
                || ( aConnecMan.getNetworkInfo( 0 ).getState() == NetworkInfo.State.CONNECTING )
                || ( aConnecMan.getNetworkInfo( 1 ).getState() == NetworkInfo.State.CONNECTING )
                || ( aConnecMan.getNetworkInfo( 1 ).getState() == NetworkInfo.State.CONNECTED ) ) {

            aResult = true;

        } else if( ( aConnecMan.getNetworkInfo( 0 ).getState() == NetworkInfo.State.DISCONNECTED )
                || ( aConnecMan.getNetworkInfo( 1 ).getState() == NetworkInfo.State.DISCONNECTED ) ) {

            aResult = false;
        }

        return aResult;
    }
}