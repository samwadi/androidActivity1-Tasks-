package com.example.myapplication;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class DueTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_due_tasks);

        ListView listViewDueTasks = findViewById(R.id.listViewDueTasks);
        Button btnBack = findViewById(R.id.btnBack);

        ArrayList<TaskItem> dueTaskList = getDueTasks();

        ArrayAdapter<TaskItem> adapter = new TaskAdapter(this, dueTaskList);
        listViewDueTasks.setAdapter(adapter);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private ArrayList<TaskItem> getDueTasks() {
        ArrayList<TaskItem> dueTasks = new ArrayList<>();
        for (TaskItem task : MainActivity.taskList) {
            if (!task.isFinished()) {
                dueTasks.add(task);
            }
        }
        return dueTasks;
    }
}
