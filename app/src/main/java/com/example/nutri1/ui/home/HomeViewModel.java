package com.example.nutri1.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Follow these simple steps to get results:" +
                "\n 1. Click on camera icon in the menu" +
                "\n 2. Place the fruit in front of the camera" +
                "\n 3. Click the picture and get the details of the fruit");
    }

    public LiveData<String> getText() {
        return mText;
    }
}