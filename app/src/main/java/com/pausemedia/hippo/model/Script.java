package com.pausemedia.hippo.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.pausemedia.hippo.database.Document;

import java.util.List;

public class Script extends AndroidViewModel {
    String promptUser = "Hello User";
    private MutableLiveData<String> Report;
    private DocumentRepository documentRepository;
    private LiveData<List<Document>> sAllDocs;

    public Script(@NonNull Application application) {
        super(application);
        documentRepository = new DocumentRepository(application);
        sAllDocs = documentRepository.getListDocs();
    }

    LiveData<List<Document>> getsAllDocs() { return sAllDocs;}

    public void insert (Document document) { documentRepository.insert(document);}

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
