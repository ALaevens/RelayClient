package com.gearnotes2000.relayclient.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.gearnotes2000.relayclient.App;
import com.gearnotes2000.relayclient.R;
import com.gearnotes2000.relayclient.model.Relay;
import com.gearnotes2000.relayclient.network.ConfigTask;
import com.gearnotes2000.relayclient.network.TemperatureTask;
import com.gearnotes2000.relayclient.network.SendTask;
import com.gearnotes2000.relayclient.network.SocketThread;
import com.gearnotes2000.relayclient.util.RelayList;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ListView relayList;
    private ArrayAdapter<Relay> relayArrayAdapter;

    private TextView tempDisplay;

    private ArrayList<Relay> relays;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        relayList = root.findViewById(R.id.relayList);

        tempDisplay = root.findViewById(R.id.tempDisplay);

        Button refresh = root.findViewById(R.id.refreshButton);
        refresh.setOnClickListener((v) -> {
            //homeViewModel.netThread.addTask(new TemperatureTask(this));
            getTemperature();
        });

        relays = new ArrayList<>();
        relayArrayAdapter = new RelayList(this, relays);
        relayList.setAdapter(relayArrayAdapter);

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();


        Log.d("SOCKET", "Call open");
        homeViewModel.openConn();
        getConfig();
        getTemperature();
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

    public void sendRelayDuration(int num, int delay, int duration) {
        SocketThread netThread = homeViewModel.netThread;
        netThread.addTask(new SendTask("TIMERELAY"));
        netThread.addTask(new SendTask(String.valueOf(num)));
        netThread.addTask(new SendTask(String.valueOf(duration)));
        netThread.addTask(new SendTask(String.valueOf(delay)));
    }

    private void getTemperature() {
        MutableLiveData<Double> liveTemp = new MutableLiveData<>();
        homeViewModel.netThread.addTask(new TemperatureTask(liveTemp));

        liveTemp.observe(getViewLifecycleOwner(), temp -> {
            if (temp != null) {
                tempDisplay.setText(String.format("Temperature: %.1f°C", temp));
            }
        });
    }

    private void getConfig() {
        MutableLiveData<ArrayList<Relay>> liveRelays = new MutableLiveData<>();
        homeViewModel.netThread.addTask(new ConfigTask(liveRelays));

        liveRelays.observe(getViewLifecycleOwner(), gotRelays -> {
            if (gotRelays != null) {
                relays.clear();
                relayArrayAdapter.clear();

                relays.addAll(gotRelays);
                relayArrayAdapter.notifyDataSetChanged();
            }
        });
    }

    int boolToInt(boolean bool) {
        if (bool) {
            return 1;
        } else {
            return 0;
        }
    }
}
