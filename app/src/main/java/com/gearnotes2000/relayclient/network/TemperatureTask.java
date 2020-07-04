package com.gearnotes2000.relayclient.network;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class TemperatureTask implements NetworkTask {
    private MutableLiveData<Double> liveValue;

    public TemperatureTask(MutableLiveData<Double> liveValue) {
        this.liveValue = liveValue;
    }

    @Override
    public boolean execute(Socket s) {
        DataInputStream din;
        DataOutputStream dout;

        try {
            dout = new DataOutputStream(s.getOutputStream());
            din = new DataInputStream(s.getInputStream());

            Log.d("SOCKETS", "SEND: GETTEMP");
            dout.writeUTF("GETTEMP");
            dout.flush();

            String reply = din.readUTF();
            Log.d("SOCKETS", "RECV: "+reply);

            liveValue.postValue(Double.valueOf(reply));

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
