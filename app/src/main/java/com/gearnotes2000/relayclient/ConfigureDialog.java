package com.gearnotes2000.relayclient;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

import com.gearnotes2000.relayclient.ui.home.HomeFragment;

public class ConfigureDialog extends AlertDialog {
    private Fragment owner;
    private int delaySeconds = 0;
    private int durationSeconds = 0;
    private int relayID;

    public ConfigureDialog(Fragment owner, int relayID) {
        super(owner.getContext());

        this.owner = owner;
        this.relayID = relayID;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_configure_time);

        Button cancelButton = findViewById(R.id.cancelButton);
        Button startButton = findViewById(R.id.startButton);
        Button delayButton = findViewById(R.id.delayButton);
        Button durationButton = findViewById(R.id.durationButton);

        delayButton.setOnClickListener(v -> {
            MutableLiveData<Integer> liveInt = new MutableLiveData<>();
            TimeSelectorDialog dd = new TimeSelectorDialog(owner, liveInt, "Set Delay");
            dd.show();

            liveInt.observe(owner, val -> {
                if (val != null){
                    Log.d("DATACHANGE", "Delay to: "+val);
                    TextView display = findViewById(R.id.delayDisplay);
                    setTimeDisplay(display, val);
                    delaySeconds = val;
                }
            });
        });

        durationButton.setOnClickListener(v -> {
            MutableLiveData<Integer> liveInt = new MutableLiveData<>();
            TimeSelectorDialog dd = new TimeSelectorDialog(owner, liveInt, "Set Duration");
            dd.show();

            liveInt.observe(owner, val -> {
                if (val != null){
                    Log.d("DATACHANGE", "Duration to: "+val);
                    TextView display = findViewById(R.id.durationDisplay);
                    setTimeDisplay(display, val);
                    durationSeconds = val;
                }
            });
        });

        cancelButton.setOnClickListener(v -> dismiss());

        startButton.setOnClickListener(v -> {
            HomeFragment home = (HomeFragment) owner;
            home.sendRelayDuration(relayID, durationSeconds, delaySeconds);
            dismiss();
        });
    }

    private void setTimeDisplay(TextView timeDisplay, int seconds) {
        int hours = seconds/3600;
        seconds = seconds%3600;
        int minutes = seconds/60;
        seconds = seconds%60;

        timeDisplay.setText(String.format("%02d:%02d:%02d", hours, minutes, seconds));
    }
}
