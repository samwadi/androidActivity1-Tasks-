package com.example.myapplication;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
public class FinishedTasksActivity extends AppCompatActivity {

    private TaskListAdapter taskListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finished_tasks);

        ListView finishedTasksListView = findViewById(R.id.finishedTasksListView);

        TaskManager taskManager = new TaskManager(this);
        ArrayList<TaskItem> finishedTasks = getFinishedTasks();

        taskListAdapter = new TaskListAdapter(this, finishedTasks);
        finishedTasksListView.setAdapter(taskListAdapter);
    }
    private ArrayList<TaskItem> getFinishedTasks() {
        ArrayList<TaskItem> finished = new ArrayList<>();
        for (TaskItem task : MainActivity.taskList) {
            if (task.isFinished()) {
                finished.add(task);
            }
        }
        return finished;
    }
}
