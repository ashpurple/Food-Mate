package com.example.foodmate.ui.recruiting;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecruitingViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public RecruitingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is mypage fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}