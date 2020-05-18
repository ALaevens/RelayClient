package com.gearnotes2000.relayclient.network;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SendTask implements NetworkTask {
    private String msg;
    public SendTask(String msg) {
        this.msg = msg;
    }

    @Override
    public boolean execute(Socket s) {
        DataOutputStream dout = null;

        try {
            dout = new DataOutputStream(s.getOutputStream());
            Log.d("SOCKETS", "SEND: "+msg);
            dout.writeUTF(msg);
            dout.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
