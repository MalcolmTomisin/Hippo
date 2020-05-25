package com.pausemedia.hippo.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Script extends ViewModel {
    private MutableLiveData<String> Report;

    public LiveData<String> getReport(){
        return Report;
    }
}
