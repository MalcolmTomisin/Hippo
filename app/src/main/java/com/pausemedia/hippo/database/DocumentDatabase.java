package com.pausemedia.hippo.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Document.class}, version = 1, exportSchema = false)
public abstract class DocumentDatabase extends RoomDatabase {
    private static final int NUMBER_OF_THREADS = 4;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile DocumentDatabase INSTANCE;

    public static DocumentDatabase getInstance(final Context context){
        if (INSTANCE == null){
            synchronized (DocumentDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), DocumentDatabase.class,
                            "document_database").build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract DocumentDao documentDao();

}
