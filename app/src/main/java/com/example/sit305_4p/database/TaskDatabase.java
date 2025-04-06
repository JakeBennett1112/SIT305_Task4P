package com.example.sit305_4p.database;

// Import Room Components
import android.content.Context; // access application context for database creation
import androidx.room.Database; // defines room database
import androidx.room.Room; // provides the builder to create the database
import androidx.room.RoomDatabase; // base class for room databases

// define as room database
// store Task objects (from Task.java)
// extend RoomDatabase to make it a room database instance
@Database(entities = {Task.class}, version = 1)
public abstract class TaskDatabase extends RoomDatabase {
    // singleton instance
    // one database instance to prevent memory leaks and performance issues
    private static TaskDatabase instance;

    // Define DAO method
    // allows us to perform the CRUD operations
    // automatically implemented at runtime
    public abstract CRUDOperation crudOperation();

    // We only want one database instance to exist at any given time
    // Synchronised ensures safety when multiple parts of the app try to access the database (ensuring one database only)
    public static synchronized TaskDatabase getInstance(Context context) {
        // Create new room database if instance is null
        if (instance == null) {
            // create database name task_database
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            TaskDatabase.class, "task_database")
                    .fallbackToDestructiveMigration() // delete old data if database version changes
                    .allowMainThreadQueries() // This will allow the database queries to be used on the main UI thread
                    .build();
        }
        return instance; // ensure only one database instance is used across the app
    }
}
