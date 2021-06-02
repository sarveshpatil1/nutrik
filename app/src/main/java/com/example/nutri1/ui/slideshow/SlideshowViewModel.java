package com.example.nutri1.ui.slideshow;

import android.widget.Button;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Here are the list of fruits this app can scan\n\n" +
                "click on any button to know more about the fruit");
    }


    public LiveData<String> getText() {
        return mText;
    }
}