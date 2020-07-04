package com.gearnotes2000.relayclient.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.gearnotes2000.relayclient.model.Relay;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class ConfigTask implements NetworkTask {
    private MutableLiveData<ArrayList<Relay>> liveLabels;

    public ConfigTask(MutableLiveData<ArrayList<Relay>> liveLabels) {
        this.liveLabels = liveLabels;
    }

    @Override
    public boolean execute(Socket s) {
        DataInputStream din;
        DataOutputStream dout;

        try {
            dout = new DataOutputStream(s.getOutputStream());
            din = new DataInputStream(s.getInputStream());

            Log.d("SOCKETS", "SEND: GETCONFIG");
            dout.writeUTF("GETCONFIG");
            dout.flush();

            int count = Integer.valueOf(din.readUTF());
            Log.d("SOCKETS", "RECV: "+count);

            ArrayList<Relay> labels = new ArrayList<>();
            for (int i = 0; i < count; i++) {
                String label = din.readUTF();
                labels.add(new Relay(i, label));
                Log.d("SOCKETS", "RECV: "+label);
            }

            liveLabels.postValue(labels);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
