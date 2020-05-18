package com.gearnotes2000.relayclient.network;

import java.net.Socket;

public interface NetworkTask {
    boolean execute(Socket s);
}
