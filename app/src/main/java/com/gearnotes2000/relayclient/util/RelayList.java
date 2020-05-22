package com.gearnotes2000.relayclient.util;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.gearnotes2000.relayclient.ConfigureDialog;
import com.gearnotes2000.relayclient.R;
import com.gearnotes2000.relayclient.model.Relay;
import com.gearnotes2000.relayclient.ui.home.HomeFragment;

import java.util.ArrayList;

public class RelayList extends ArrayAdapter<Relay> {
    private ArrayList<Relay> relays;
    private Fragment owner;

    public RelayList(Fragment owner, ArrayList<Relay> relays) {
        super(owner.getContext(), 0, relays);

        this.relays = relays;
        this.owner = owner;
    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        if(view == null) {
            view = LayoutInflater.from(owner.getContext()).inflate(R.layout.relay_list_content, parent, false);
        }

        final Relay relay = relays.get(position);

        TextView label = view.findViewById(R.id.label);
        Switch sw = view.findViewById(R.id.manualSwitch);

        label.setText(relay.getLabel());

        sw.setOnCheckedChangeListener((b, state) -> {
            HomeFragment home = (HomeFragment) owner;
            home.sendRelay(relay.getId(), state);
        });

        Button timeButton = view.findViewById(R.id.configureButton);
        timeButton.setOnClickListener((v) -> {
            ConfigureDialog cd = new ConfigureDialog(owner, relay.getId());
            cd.show();
        });

        return view;
    }

}
