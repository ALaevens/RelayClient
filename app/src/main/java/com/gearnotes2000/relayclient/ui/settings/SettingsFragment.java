package com.gearnotes2000.relayclient.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.gearnotes2000.relayclient.R;

public class SettingsFragment extends Fragment {

    private SettingsViewModel settingsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        //galleryViewModel = ViewModelProviders.of(this).get(GalleryViewModel.class);

        ViewModelProvider provider = new ViewModelProvider(this);
        settingsViewModel = provider.get(SettingsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_settings, container, false);
        //final TextView textView = root.findViewById(R.id.text_gallery);
        /*galleryViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        //textView.setText(settingsViewModel.getStatictext());
        return root;
    }
}
