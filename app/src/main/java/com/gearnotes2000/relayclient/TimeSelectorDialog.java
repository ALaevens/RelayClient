package com.gearnotes2000.relayclient;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;

public class TimeSelectorDialog extends AlertDialog {
    private MutableLiveData<Integer> liveSeconds;
    private String title;

    public TimeSelectorDialog(Fragment owner, MutableLiveData<Integer> liveSeconds) {
        this(owner, liveSeconds, "");
    }

    public TimeSelectorDialog(Fragment owner, MutableLiveData<Integer> liveSeconds, String title) {
        super(owner.getContext());
        this.liveSeconds = liveSeconds;
        this.title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.fragment_time_picker);

        NumberPicker hourPicker = findViewById(R.id.numpicker_hours);
        NumberPicker minutePicker = findViewById(R.id.numpicker_minutes);
        NumberPicker secondPicker = findViewById(R.id.numpicker_seconds);

        hourPicker.setMaxValue(23);
        minutePicker.setMaxValue(59);
        secondPicker.setMaxValue(59);

        Button cancelButton = findViewById(R.id.timeCancelButton);
        Button startButton = findViewById(R.id.timeSetButton);

        TextView titleView = findViewById(R.id.pickerTitle);
        if (title.length() == 0) {
            titleView.setVisibility(View.GONE);
        } else {
            titleView.setText(title);
        }

        cancelButton.setOnClickListener((v) -> {
            liveSeconds.postValue(null);
            dismiss();
        });

        startButton.setOnClickListener((v) -> {
            int seconds = 3600 * hourPicker.getValue() + 60 * minutePicker.getValue() + secondPicker.getValue();

            liveSeconds.postValue(seconds);
            dismiss();
        });

    }
}
