package com.example.foodmate.ui.recruited;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecruitedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecruitedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mypage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}