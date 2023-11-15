package com.example.myapplication;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskListAdapter extends ArrayAdapter<TaskItem> {

    private Context context;
    private ArrayList<TaskItem> taskList;

    public TaskListAdapter(Context context, ArrayList<TaskItem> taskList) {
        super(context, 0, taskList);
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        TaskItem taskItem = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);
        }

        // Lookup view for data population
        TextView taskTitle = convertView.findViewById(R.id.taskTitle);
        TextView taskDescription = convertView.findViewById(R.id.taskDescription);

        // Populate the data into the template view using the data object
        taskTitle.setText(taskItem.getTitle());
        taskDescription.setText(taskItem.getDescription());

        // Return the completed view to render on screen
        return convertView;
    }
}
