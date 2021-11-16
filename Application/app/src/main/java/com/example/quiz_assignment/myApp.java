package com.example.quiz_assignment;

import android.app.Application;

public class myApp extends Application {
    private StorageManager storageManager = new StorageManager();

    public StorageManager getStorageManager(){
        return storageManager;
    }
}
