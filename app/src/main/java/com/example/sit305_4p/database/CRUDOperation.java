package com.example.sit305_4p.database;

// Import room annotations
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

// Data Access Object
// Interface for automatic implementation
@Dao
public interface CRUDOperation {
    // Insert new task into database
    @Insert
    void insert(Task task);

    @Update
    void update(Task task);

    @Delete
    void delete(Task task);

    @Query("SELECT * FROM tasks ORDER BY dueDate ASC")
    List<Task> getAllTasks();

    // fetch a task by ID
    Task getTaskById(int taskId);
}
