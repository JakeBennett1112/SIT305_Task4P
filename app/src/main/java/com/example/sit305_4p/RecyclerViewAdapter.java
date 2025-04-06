package com.example.sit305_4p;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sit305_4p.database.Task;

import java.util.List;

// Declare our recycler view adapter but extend to use RecyclerView.Adapter
// This allow us to manage a list inside a recycler view
// .ViewHolder defines a customer view holder so we can manage our tasks
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>
{
    // Variables for our taskList to hold our list of tasks
    //                  listener so we can handle on click events on our tasks
    private List<Task> taskList;
    private OnTaskClickListener listener;

    // interface for our click events so external classes are able to handle our task clicks
    public interface OnTaskClickListener {
        void onTaskClick(Task task);
    }

    // constructor so we can store a list of tasks and the click listener
    // We do this so we can pass the task and listener when creating an instance of this adapter
    public RecyclerViewAdapter(List<Task> taskList, OnTaskClickListener listener) {
        this.taskList = taskList;
        this.listener = listener;
    }

    // we want to create a view holder for each of the objects in the list
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskView = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_item, parent, false); // convert task_item.xml into a View object
        return new ViewHolder(taskView); // crate a return a new view holder instance
    }

    // bind data to view holder so we can populate our task data within the UI
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Task task = taskList.get(position); // Get the task from the position given
        holder.title.setText(task.getTitle()); // set the title of the task in the text view
        holder.dueDate.setText(task.getDueDate()); // set the due date
        holder.itemView.setOnClickListener(v -> listener.onTaskClick(task)); // handle task clicks. call the task from the listener when the item is clicked

    }

    // return the number of tasks within the task list
    @Override
    public int getItemCount() {
        return taskList.size();
    }

    // create a viewholder class to hold the references to the views inside of each item
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, dueDate; // create a title and duedate variable with an instance of TextView

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // find both the title and due date using their id and store it in their respective variables
            title = itemView.findViewById(R.id.textViewTitle); // find title
            dueDate = itemView.findViewById(R.id.textViewDueDate); // find due date
        }
    }
}
