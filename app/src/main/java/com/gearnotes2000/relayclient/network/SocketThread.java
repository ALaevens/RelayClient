package com.gearnotes2000.relayclient.network;

import android.util.Log;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayDeque;
import java.util.Deque;

public class SocketThread extends Thread{
    private String[] hosts;
    private int port;

    private boolean keepRunning = true;

    private Deque<NetworkTask> tasks = new ArrayDeque<>();

    public SocketThread(String[] hosts, int port) {
        super();

        this.hosts = hosts;
        this.port = port;
    }

    public void run() {
        System.out.println("Make Connection");

        Socket s = createSock();

        while(keepRunning && s != null) {
            if (tasks.peekFirst() != null) {
                NetworkTask task = tasks.poll();
                //System.out.println("Do Task");
                keepRunning = task.execute(s);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public Socket createSock() {
        Socket s = null;
        for (String host : hosts) {
            try {
                Log.d("SOCKETS", "Connect Socket to "+host+":"+port);
                s = new Socket();
                s.connect(new InetSocketAddress(host, port), 2000);
            } catch (IOException e) {
                e.printStackTrace();
                Log.d("SOCKETS", "Fail... try next fallback address");
                continue;
            }

            break;
        }

        return s;
    }

    public void addTask(NetworkTask task) {
        tasks.addLast(task);
    }

    public void setKeepRunning(boolean keepRunning) {
        this.keepRunning = false;
    }
}
