package com.example.sit305_4p;

// import packages
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sit305_4p.database.Task;
import com.example.sit305_4p.database.TaskDatabase;

// Create class and extend to inherit AppCompatActivity
public class TaskDetailsActivity extends AppCompatActivity {
    // Declare variables
    private TextView textTitle, textDescription, textDueDate;
    private Button buttonEdit, buttonDelete;

    private TaskDatabase database;
    private Task task;

    // oncreate for when the screen is created
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_details); // load the activity_task_details.xml file

        // retrieve the UI elements from activity_task_details.xml
        textTitle = findViewById(R.id.textViewTaskTitle);
        textDescription = findViewById(R.id.textViewTaskDescription);
        textDueDate = findViewById(R.id.textViewTaskDueDate);
        buttonEdit = findViewById(R.id.buttonEdit);
        buttonDelete = findViewById(R.id.buttonDelete);

        // retrieve the task ID from MainActivity
        int taskId = getIntent().getIntExtra("taskId", -1); // show -1 if en error has occured
        if (taskId == -1) {
            Toast.makeText(this, "Invalid Task ID", Toast.LENGTH_SHORT).show();
            finish(); // close the screen
            return;
        }

        // load the database and task from Room
        database = TaskDatabase.getInstance(this);
        task = database.crudOperation().getTaskById(taskId);

        // if the task doesn't equal null we want to show its details
        if (task != null) {
            textTitle.setText(task.getTitle());
            textDescription.setText(task.getDescription());
            textDueDate.setText(task.getDueDate());
        }

        // edit button when it is clicked
        buttonEdit.setOnClickListener((v -> {
            Intent intent = new Intent(TaskDetailsActivity.this, AddEditTaskActivity.class);
            intent.putExtra("taskId", task.getId());
            startActivity(intent);
            finish();
        }));

        // delete button when it is clicked
        buttonDelete.setOnClickListener(v -> {
            database.crudOperation().delete(task);
            Toast.makeText(this, "Task deleted", Toast.LENGTH_SHORT).show();
            finish(); // Go back to MainActivity
        });
    }

    // if the task is edited, return and fetch the up to date information
    @Override
    protected void onResume() {
        super.onResume();
        if (task != null) {
            task = database.crudOperation().getTaskById(task.getId());
            if (task != null) {
                textTitle.setText(task.getTitle());
                textDescription.setText(task.getDescription());
                textDueDate.setText(task.getDueDate());
            }
        }
    }
}
