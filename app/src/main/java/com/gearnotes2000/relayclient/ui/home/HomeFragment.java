package com.gearnotes2000.relayclient.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.gearnotes2000.relayclient.App;
import com.gearnotes2000.relayclient.R;
import com.gearnotes2000.relayclient.model.Relay;
import com.gearnotes2000.relayclient.network.RefreshTask;
import com.gearnotes2000.relayclient.network.SendTask;
import com.gearnotes2000.relayclient.network.SocketThread;
import com.gearnotes2000.relayclient.util.RelayList;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ListView relayList;
    private ArrayAdapter<Relay> relayArrayAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        relayList = root.findViewById(R.id.relayList);

        App app = (App) getActivity().getApplication();
        relayArrayAdapter = new RelayList(this, app.relays);
        relayList.setAdapter(relayArrayAdapter);

        Button refresh = root.findViewById(R.id.refreshButton);
        refresh.setOnClickListener((v) -> {
            homeViewModel.netThread.addTask(new RefreshTask(this));
        });
        return root;
    }

    @Override
    public void onResume() {
        super.onResume();


        Log.d("SOCKET", "Call open");
        homeViewModel.openConn();
        homeViewModel.netThread.addTask(new RefreshTask(this));
    }

    @Override
    public void onPause() {
        super.onPause();

        homeViewModel.closeConn();
    }

    public void sendRelay(int num, boolean state) {
        SocketThread netThread = homeViewModel.netThread;
        netThread.addTask(new SendTask("SETRELAY"));
        netThread.addTask(new SendTask(String.valueOf(num)));
        netThread.addTask(new SendTask(String.valueOf(boolToInt(state))));
    }

    public void sendRelayDuration(int num, int duration, int delay) {
        SocketThread netThread = homeViewModel.netThread;
        netThread.addTask(new SendTask("TIMERELAY"));
        netThread.addTask(new SendTask(String.valueOf(num)));
        netThread.addTask(new SendTask(String.valueOf(duration)));
        netThread.addTask(new SendTask(String.valueOf(delay)));
    }

    int boolToInt(boolean bool) {
        if (bool) {
            return 1;
        } else {
            return 0;
        }
    }
}
