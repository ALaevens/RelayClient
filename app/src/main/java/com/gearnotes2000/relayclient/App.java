package com.gearnotes2000.relayclient;

import android.app.Application;
import android.util.Log;

import com.gearnotes2000.relayclient.model.Relay;

import java.util.ArrayList;

public class App extends Application {
    public static final String[] hosts = {"192.168.2.60"};
    public static final int port = 5050;

    public ArrayList<Relay> relays;

    @Override
    public void onCreate() {
        super.onCreate();

        String[] relayColors = {"Red", "Green", "Blue", "Yellow"};
        relays = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            relays.add(new Relay(i, relayColors[i]));
        }
    }
}
