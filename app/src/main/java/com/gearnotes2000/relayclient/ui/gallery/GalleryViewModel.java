package com.gearnotes2000.relayclient.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private String galText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is gallery fragment");

        galText = "This isn't live";
    }

    public LiveData<String> getText() {
        return mText;
    }

    public String getStatictext() {return galText;}
}