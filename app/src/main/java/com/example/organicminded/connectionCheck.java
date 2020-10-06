package com.example.organicminded;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.provider.Settings;

public class connectionCheck {

    private Context context;
    private ConnectivityManager manager;

    public connectionCheck(Context c, ConnectivityManager m) {
        this.context = c;
        this.manager = m;
    }

    protected boolean connectionStatus() {
        try {
            boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
            boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();

            if (is3g)
                return true;
            else if (isWifi)
                return true;
            else {

                new AlertDialog.Builder(context)
                        .setPositiveButton(R.string.str_net_wifi, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
                                context.startActivity(intent);
                            }
                        })
                        .setNegativeButton(R.string.str_net_data, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_DATA_ROAMING_SETTINGS);
                                context.startActivity(intent);
                            }
                        })
                        .setTitle(R.string.str_Hoshdar)
                        .setMessage(R.string.str_noInternetConnection)
                        .create().show();
                return false;
            }
        } catch (NullPointerException e) {
            return false;
        }
    }


}
