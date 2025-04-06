package com.example.sit305_4p;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sit305_4p.database.Task;
import com.example.sit305_4p.database.TaskDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

// extend to AppCompatActivity to inherit its properties
public class MainActivity extends AppCompatActivity {
    // declare our variables
    private RecyclerView recyclerView; // Display the list of tasks
    private RecyclerViewAdapter adapter; // Populate recycler view
    private TaskDatabase database; // provide access to the stored tasks
    private List<Task> taskList; // hold the list of tasks that we retrieve from the database

    // Initilase the UI
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.recyclerView); // find recycler view from XML
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); // display vertically

        // get the database instance and load the tasks
        database = TaskDatabase.getInstance(this); // get an instance of the room database
        taskList = database.crudOperation().getAllTasks(); // retrieve all of the tasks stored within the database

        // create a task adapter and uses Intent to pass a list of tasks and open taskDetailsActivity when we click a task
        adapter = new RecyclerViewAdapter(taskList, task -> {
            Intent intent =  new Intent(MainActivity.this, TaskDetailsActivity.class);
            intent.putExtra("taskId", task.getId());
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);

        // Set up a floating action button wo we can add a new task to AddEditTaskActivity
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(v -> startActivity(new Intent(MainActivity.this, AddEditTaskActivity.class)));

        // implement the bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                return true;
            } else if (id == R.id.nav_completed) {
                Intent intent = new Intent(MainActivity.this, CompletedTasksActivity.class);
                startActivity(intent);
                return true;
            } else if (id == R.id.nav_settings) {
                Intent intent = new Intent(MainActivity.this, SettingsActvitiy.class);
                startActivity(intent);
                return true;
            }

            return false;
        });

    }

    // refresh the task list when the user returns to the screen after leaving
    // It retrieves the updated task list from the database and notifies this to the adapter and refreshes the UI
    @Override
    protected void onResume() {
        super.onResume();
        taskList = database.crudOperation().getAllTasks();
        adapter.notifyDataSetChanged();
    }

}