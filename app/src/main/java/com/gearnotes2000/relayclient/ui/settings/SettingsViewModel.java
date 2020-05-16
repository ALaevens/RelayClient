package com.gearnotes2000.relayclient.ui.settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SettingsViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String galText;

    public SettingsViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");

        galText = "This isn't live";
    }

    public LiveData<String> getText() {
        return mText;
    }

    public String getStatictext() {return galText;}
}