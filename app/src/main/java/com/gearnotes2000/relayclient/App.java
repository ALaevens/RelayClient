package com.gearnotes2000.relayclient;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.gearnotes2000.relayclient.model.Relay;

import java.util.ArrayList;

public class App extends Application {
    public static String[] hosts;
    public static int port;

    @Override
    public void onCreate() {
        super.onCreate();

        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.preference_file_ips), Context.MODE_PRIVATE);
        String primary = sharedPreferences.getString("primary", "");
        String fallback = sharedPreferences.getString("fallback", "");
        port = sharedPreferences.getInt("port", 5050);

        if (fallback.length() == 0) {
            hosts = new String[]{primary};
        } else {
            hosts = new String[]{primary, fallback};
        }
    }
}
