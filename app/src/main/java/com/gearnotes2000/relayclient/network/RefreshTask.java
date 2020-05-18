package com.gearnotes2000.relayclient.network;

import android.app.Activity;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.gearnotes2000.relayclient.R;
import com.gearnotes2000.relayclient.network.NetworkTask;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class RefreshTask implements NetworkTask {
    private Fragment owner;

    public RefreshTask(Fragment owner) {
        this.owner = owner;
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


            owner.getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    TextView textView = owner.getView().findViewById(R.id.tempDisplay);
                    textView.setText(String.format("Temperature: %.1f°C", Double.valueOf(reply)));
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }
}
