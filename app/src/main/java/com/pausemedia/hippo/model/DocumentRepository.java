package com.pausemedia.hippo.model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.pausemedia.hippo.database.Document;
import com.pausemedia.hippo.database.DocumentDao;
import com.pausemedia.hippo.database.DocumentDatabase;

import java.util.List;

public class DocumentRepository {
    private DocumentDao documentDao;
    private LiveData<List<Document>> listDocs;

    DocumentRepository(Application application){
        DocumentDatabase db = DocumentDatabase.getInstance(application);
        documentDao = db.documentDao();
        listDocs = documentDao.getAll();
    }

    LiveData<List<Document>> getListDocs() {
        return listDocs;
    }

    void insert(Document document){
        DocumentDatabase.databaseWriteExecutor.execute(() -> {
            documentDao.insert(document);
        });
    }
}
