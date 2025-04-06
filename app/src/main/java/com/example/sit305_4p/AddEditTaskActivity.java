package com.example.sit305_4p;

// import classes
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.sit305_4p.database.Task;
import com.example.sit305_4p.database.TaskDatabase;

// Declare the class for adding and editing
// extend to use AppCompatActivity properties
public class AddEditTaskActivity extends AppCompatActivity {
    // declare UI field variables
    private EditText editTextTitle, editTextDescription, editTextDueDate; // users can type in text
    private TaskDatabase database; // handles the saving and loading of the tasks

    // we call the onCreate method when the screen is created
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_task); // load the xml file where the UI is defined

        // find the UI elements in the activity_add_edit_task.XML file and connect it in the java file
        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDescription = findViewById(R.id.editTextDescription);
        editTextDueDate = findViewById(R.id.editTextDueDate);
        Button buttonSave = findViewById(R.id.buttonSave);

        // Initialise the single instance of the database
        database = TaskDatabase.getInstance(this); // we can now call out method eg. insert, delete etc.

        // Use a click listener on our save button
        buttonSave.setOnClickListener(v -> { // define what happens when we press the button
            // read user input and convert to string
            String title = editTextTitle.getText().toString();
            String description = editTextDescription.getText().toString();
            String dueDate = editTextDueDate.getText().toString();


            // Error handling and validation
            // Check to see if strings are empty using .isEmpty() and provide an error message if so
            // Validate title
            if (title.isEmpty()) {
                editTextTitle.setError("Title is required!");
                return;
            }

            // Validate description
            if (description.isEmpty()) {
                editTextDescription.setError("Description is required");
                return;
            }

            // Validate due date
            if (dueDate.isEmpty()) {
                editTextDueDate.setError("Due date is required!");
                return;
            }

            // Another example of error handling
            // validate user input ie. both title and due date are not empty
            if (!title.isEmpty() && !dueDate.isEmpty()) {
                database.crudOperation().insert(new Task(title, description, dueDate)); // create a new task and insert into the database
                finish(); // close activity and return to previous screen
            }
        });

    }
}

