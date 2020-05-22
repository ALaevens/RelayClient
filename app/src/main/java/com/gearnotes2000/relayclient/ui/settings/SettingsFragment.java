package com.gearnotes2000.relayclient.ui.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gearnotes2000.relayclient.App;
import com.gearnotes2000.relayclient.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;
    private SharedPreferences sharedPreferences;

    private EditText editPrimary;
    private EditText editFallback;
    private EditText editPort;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        ViewModelProvider provider = new ViewModelProvider(this);
        settingsViewModel = provider.get(SettingsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        Context context = getActivity();
        sharedPreferences = context.getSharedPreferences(getString(R.string.preference_file_ips), Context.MODE_PRIVATE);

        editPrimary = root.findViewById(R.id.primaryAddress);
        editFallback = root.findViewById(R.id.fallbackAddress);
        editPort = root.findViewById(R.id.serverPort);

        editPrimary.setText(sharedPreferences.getString("primary", ""));
        editFallback.setText(sharedPreferences.getString("fallback", ""));
        editPort.setText(String.valueOf(sharedPreferences.getInt("port", 5050)));

        Button saveButton = root.findViewById(R.id.saveButton);
        saveButton.setOnClickListener((v) -> {
            save();
        });

        return root;
    }

    @Override
    public void onPause() {
        super.onPause();

        View view = getView();
        ConstraintLayout layout = view.findViewById(R.id.settingsLayout);

        InputMethodManager imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(layout.getWindowToken(), 0);
    }

    private void save() {
        String primary = editPrimary.getText().toString();
        String fallback = editFallback.getText().toString();
        String portString = editPort.getText().toString();

        Log.d("SETTINGS", "Primary: '"+primary+"'");
        Log.d("SETTINGS", "Fallback: '"+fallback+"'");
        Log.d("SETTINGS", "Port: "+portString);

        if (primary.length() == 0) {
            Toast toast = Toast.makeText(getActivity(),"Primary address cannot be empty", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        if (portString.length() == 0) {
            Toast toast = Toast.makeText(getActivity(),"Port cannot be empty", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }

        SharedPreferences.Editor editor = sharedPreferences.edit();
        int port = Integer.parseInt(portString);
        editor.putString("primary", primary);
        editor.putString("fallback", fallback);
        editor.putInt("port", port);
        editor.commit();

        if (fallback.length() == 0) {
            App.hosts = new String[]{primary};
        } else {
            App.hosts = new String[]{primary, fallback};
        }

        App.port = port;

        Toast toast = Toast.makeText(getActivity(),"Updated Settings", Toast.LENGTH_SHORT);
        toast.show();
    }
}
