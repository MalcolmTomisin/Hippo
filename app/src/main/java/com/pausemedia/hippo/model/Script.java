package com.pausemedia.hippo.model;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Script extends ViewModel {
    String promptUser = "Hello User";
    private MutableLiveData<String> Report;

    public LiveData<String> getReport(){
       if (Report == null){
           Report = new MutableLiveData<>(promptUser);
       }
       return Report;
    }

    public void postReport (String speech){
        Report.postValue(speech);
    }
}
