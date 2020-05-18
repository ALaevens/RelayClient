package com.gearnotes2000.relayclient.network;

import java.net.Socket;

public class StopTask extends SendTask {
    public StopTask() {
        super("DISCONNECT");
    }

    @Override
    public boolean execute(Socket s) {
        super.execute(s);
        return false;
    }
}
