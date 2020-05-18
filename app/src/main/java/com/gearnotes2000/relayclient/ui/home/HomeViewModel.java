package com.gearnotes2000.relayclient.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.gearnotes2000.relayclient.App;
import com.gearnotes2000.relayclient.network.SocketThread;
import com.gearnotes2000.relayclient.network.StopTask;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    protected SocketThread netThread;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }

    protected void openConn() {
        netThread = new SocketThread(App.hosts, App.port);
        netThread.start();
    }

    protected void closeConn() {
        netThread.addTask(new StopTask());
    }
}