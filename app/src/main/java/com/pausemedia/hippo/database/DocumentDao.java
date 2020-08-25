package com.pausemedia.hippo.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface DocumentDao {

    @Insert
    void insert(Document doc);

    @Query("SELECT * FROM document_table ORDER BY date ASC")
    LiveData<List<Document>> getAll();

    @Delete
    void delete(Document doc);

    @Update
    void update(Document doc);

}
