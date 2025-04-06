package com.example.sit305_4p.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

// Mark as room database table named tasks
@Entity(tableName = "tasks")
public class Task {
    // automatically generate the primary key of id
    @PrimaryKey(autoGenerate = true)
    private int id;

    // Variables to be used within the tasks table
    private String title;
    private String description;
    private String dueDate;

    // Create a constructor to create a task
    // id is automatically assigned
    public Task(String title, String description, String dueDate) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
    }

    // getter and setter methods
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getDueDate() { return dueDate; }
}
