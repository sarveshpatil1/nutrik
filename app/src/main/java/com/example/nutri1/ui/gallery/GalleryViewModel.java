package com.example.nutri1.ui.gallery;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class GalleryViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public GalleryViewModel() {
        mText = new MutableLiveData<>();
        /*mText.setValue("OPEN's camera");*/
    }

    public LiveData<String> getText() {
        return mText;
    }
}