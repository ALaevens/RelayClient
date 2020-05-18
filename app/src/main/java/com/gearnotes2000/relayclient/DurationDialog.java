package com.gearnotes2000.relayclient;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import androidx.fragment.app.Fragment;

import com.gearnotes2000.relayclient.ui.home.HomeFragment;

public class DurationDialog extends AlertDialog {
    private Fragment owner;
    private int relayID;

    public DurationDialog(Fragment owner, int relayID) {
        super(owner.getContext());

        this.relayID = relayID;
        this.owner = owner;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_duration_picker);

        NumberPicker hourPicker = findViewById(R.id.numpicker_hours);
        NumberPicker minutePicker = findViewById(R.id.numpicker_minutes);
        NumberPicker secondPicker = findViewById(R.id.numpicker_seconds);

        hourPicker.setMaxValue(23);
        minutePicker.setMaxValue(59);
        secondPicker.setMaxValue(59);

        Button cancelButton = findViewById(R.id.cancelButton);
        Button startButton = findViewById(R.id.startButton);

        cancelButton.setOnClickListener((v) -> {
            dismiss();
        });

        startButton.setOnClickListener((v) -> {
            int delay = 3600 * hourPicker.getValue() + 60 * minutePicker.getValue() + secondPicker.getValue();


            HomeFragment home = (HomeFragment) owner;
            home.sendRelayDuration(relayID, delay);

            dismiss();
        });
    }
}
